package View.Menus;

import Controller.GameMenuController;
import Model.Cards.Card;
import Model.Cards.Troop;
import Model.Castle;
import Model.ClashRoyale;
import Model.User;
import View.enums.Commands.GameMenuCommands;
import View.enums.Messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {

    private User host;
    private User guest;
    private User currentPlayer;

    private User otherPlayer;

    public void run(Scanner scanner, String guestUsername, int turnsCount) {
        GameMenuController.startGame(guestUsername);
        host = GameMenuController.getHost();
        guest = GameMenuController.getGuest();
        String command;
        Matcher matcher;
        int currentTurn = 1;
        while (currentTurn < turnsCount) {
            command = scanner.nextLine();
            if (command.equals("show current menu")) System.out.println("Game Menu");
            else if (command.equals("show the hitpoints left of my opponent")) showHitPoints();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_LINE_INFO)) != null)
                showLineInfo(matcher);
            else if (command.equals("number of cards to play"))
                System.out.println("You can play " + currentPlayer.getCardsToPlay() + " cards more!");
            else if (command.equals("number of moves left"))
                System.out.println("You have " + currentPlayer.getMovesLeft() + " moves left!");
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.MOVE_TROOP)) != null)
                checkMoveTroop(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DEPLOY_TROOP)) != null)
                checkDeployTroop(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DEPLOY_HEAL)) != null)
                checkDeployHeal(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DEPLOY_FIREBALL)) != null)
                checkDeployFireball(matcher);
            else if (command.equals("next turn")) {
                nextTurn(turnsCount, currentTurn);
                currentTurn++;
            }
        }
    }

    private void showHitPoints() {
        for (Castle castle : currentPlayer.getCastles())
            System.out.println(castle.getSide() + ": " + castle.getHitPoint());
    }

    private void showLineInfo(Matcher matcher) {
        String direction = matcher.group("lineDirection");
        GameMenuMessages message = GameMenuController.checkLineDirection(direction);
        switch (message) {
            case INVALID_LINE_DIRECTION:
                System.out.println("Incorrect line direction!");
                break;
            case SUCCESS:
                HashMap<Integer, ArrayList<Card>> rows = ClashRoyale.getMap().get(direction);
                rows.forEach((rowNumber, rowCards) -> {
                    if (rowCards.size() > 0) for (Card rowCard : rowCards)
                        System.out.println("row " + rowNumber + ": " + rowCard.getCardName() + ": " + rowCard.getOwner().getUsername());
                });
        }
    }

    private void checkMoveTroop(Matcher matcher) {
        String lineDirection = matcher.group("lineDirection");
        String direction = matcher.group("direction");
        int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
        GameMenuMessages message = GameMenuController.checkMoveTroop(lineDirection, direction, rowNumber, currentPlayer);
        switch (message) {
            case INVALID_LINE_DIRECTION:
                System.out.println("Incorrect line direction!");
                break;
            case INVALID_ROW:
                System.out.println("Invalid row number!");
                break;
            case INVALID_DIRECTION:
                System.out.println("you can only move troops upward or downward!");
                break;
            case NO_MOVES_LEFT:
                System.out.println("You are out of moves");
                break;
            case NO_TROOPS_IN_PLACE:
                System.out.println("You don't have any troops in this place!");
                break;
            case INVALID_MOVE:
                System.out.println("Invalid move!");
                break;
            case SUCCESS:
                Card userCardInPlace = GameMenuController.getCardInPlace(lineDirection, rowNumber, currentPlayer);
                String cardName = userCardInPlace.getCardName();
                int currentRow = userCardInPlace.getCurrentRow();
                System.out.println(cardName + " moved successfully to row " + currentRow + " in line " + lineDirection);
                break;
        }
    }

    private void checkDeployTroop(Matcher matcher) {
        Troop troop = (Troop) ClashRoyale.getCardByName(matcher.group("troopName"));
        String lineDirection = matcher.group("lineDirection");
        int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
        GameMenuMessages message = GameMenuController.checkDeployTroop(lineDirection, rowNumber, troop, currentPlayer);
        switch (message) {
            case INVALID_CARD_NAME:
                System.out.println("Invalid troop name!");
                break;
            case NOT_IN_BATTLE_DECK:
                System.out.println("You don't have " + troop.getCardName() + " card in your battle deck!");
                break;
            case INVALID_LINE_DIRECTION:
                System.out.println("Incorrect line direction!");
                break;
            case INVALID_ROW:
                System.out.println("Invalid row number!");
                break;
            case NOT_NEAR_CASTLE:
                System.out.println("Deploy your troops near your castles!");
                break;
            case NO_CARDS_TO_PLAY_LEFT:
                System.out.println("You have deployed a troop or spell this turn!");
                break;
        }
    }

    public void checkDeployHeal(Matcher matcher) {
        String lineDirection = matcher.group("lineDirection");
        int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
        GameMenuMessages message = GameMenuController.checkDeployHeal(lineDirection, rowNumber, currentPlayer);
        switch (message) {
            case INVALID_LINE_DIRECTION:
                System.out.println("Incorrect line direction!");
                break;
            case NOT_IN_BATTLE_DECK:
                System.out.println("You don't have Heal card in your battle deck!");
                break;
            case INVALID_ROW:
                System.out.println("Invalid row number!");
                break;
            case NO_MOVES_LEFT:
                System.out.println("You have deployed a troop or spell this turn!");
                break;
            case SUCCESS:
                System.out.println("You have deployed Heal successfully!");
                break;
        }
    }

    public void checkDeployFireball(Matcher matcher) {
        String lineDirection = matcher.group("lineDirection");
        GameMenuMessages message = GameMenuController.checkDeployFireball(lineDirection, currentPlayer);
        switch (message) {
            case INVALID_LINE_DIRECTION:
                System.out.println("Incorrect line direction!");
                break;
            case NOT_IN_BATTLE_DECK:
                System.out.println("You don't have Fireball card in your battle deck!");
                break;
            case NO_MOVES_LEFT:
                System.out.println("You have deployed a troop or spell this turn!");
                break;
            case DESTROYED_CASTLE:
                System.out.println("This castle is already destroyed!");
                break;
            case SUCCESS:
                System.out.println("You have deployed Fireball successfully!");
                break;
        }
    }

    public void nextTurn(int turnsCount, int currentTurn) {
        GameMenuMessages message = GameMenuController.nextTurn(turnsCount, currentTurn);
        switch (message) {
            case NEXT_TURN:
                System.out.println("Player " + currentPlayer.getUsername() + " is now playing!");
                break;
            case END_GAME:
                User winner = GameMenuController.getWinner();
                if (winner == null) System.out.println("Game has ended. Result: Tie");
                else System.out.println("Game has ended. Winner: " + winner.getUsername());
                break;
        }
    }
}