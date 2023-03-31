package View.Menus;

import Controller.GameMenuController;
import Model.Cards.Card;
import Model.Cards.Troop;
import Model.Castle;
import Model.ClashRoyale;
import Model.Place;
import Model.User;
import View.enums.Commands.GameMenuCommands;
import View.enums.Messages.GameMenuMessages;

import java.util.*;
import java.util.regex.Matcher;

public class GameMenu {

    private User currentPlayer;

    private User otherPlayer;

    public void run(Scanner scanner, String guestUsername, int turnsCount) {
        GameMenuController.startGame(guestUsername);
        String command;
        Matcher matcher;
        int currentTurn = 1;
        while (currentTurn <= turnsCount * 2) {
            currentPlayer = GameMenuController.getCurrentPlayer();
            otherPlayer = GameMenuController.getOtherPlayer();
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
            } else System.out.println("Invalid command!");
        }
    }

    private void showHitPoints() {
        for (Castle castle : otherPlayer.getCastles())
            if (castle.getHitPoint() > 0)
                System.out.println(castle.getSide() + ": " + castle.getHitPoint());
            else System.out.println(castle.getSide() + ": " + -1);

    }

    private void showLineInfo(Matcher matcher) {
        String lineDirection = matcher.group("lineDirection");
        GameMenuMessages message = GameMenuController.checkLineDirection(lineDirection);
        switch (message) {
            case INVALID_LINE_DIRECTION:
                System.out.println("Incorrect line direction!");
                break;
            case SUCCESS:
                printMaps(lineDirection);
                break;
        }
    }

    public void printMaps(String lineDirection) {
        System.out.println(lineDirection + " line:");
        for (Place place : ClashRoyale.getMap()) {
            ArrayList<Card> cardsInPlace = place.getCards();
            if (place.getLineDirection().equals(lineDirection)) {
                for (Card card : cardsInPlace) {
                    User owner = card.getOwner();
                    System.out.println("row " + place.getRowNumber() + ": " + card.getCardName() + ": " +
                            owner.getUsername());
                }
            }
        }
    }

    private void checkMoveTroop(Matcher matcher) {
        String lineDirection = matcher.group("lineDirection");
        String direction = matcher.group("direction");
        int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
        GameMenuMessages message = GameMenuController.checkMoveTroop(lineDirection, direction, rowNumber);
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
                System.out.println("You are out of moves!");
                break;
            case NO_TROOPS_IN_PLACE:
                System.out.println("You don't have any troops in this place!");
                break;
            case INVALID_MOVE:
                System.out.println("Invalid move!");
                break;
            case SUCCESS:
                Troop troop = (Troop) GameMenuController.getMovedCard();
                String cardName = troop.getCardName();
                int currentRow = troop.getCurrentRow();
                System.out.println(cardName + " moved successfully to row " + currentRow + " in line " + lineDirection);
                break;
        }
    }

    private void checkDeployTroop(Matcher matcher) {
        Card card = ClashRoyale.getCardByName(matcher.group("troopName"));
        String lineDirection = matcher.group("lineDirection");
        int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
        GameMenuMessages message = GameMenuController.checkDeployTroop(lineDirection, rowNumber, card);
        switch (message) {
            case INVALID_CARD_NAME:
                System.out.println("Invalid troop name!");
                break;
            case NOT_IN_BATTLE_DECK:
                System.out.println("You don't have " + card.getCardName() + " card in your battle deck!");
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
            case SUCCESS:
                System.out.println("You have deployed " + card.getCardName() + " successfully!");
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
            case NO_CARDS_TO_PLAY_LEFT:
                System.out.println("You have deployed a troop or spell this turn!");
                break;
            case SUCCESS:
                System.out.println("You have deployed Heal successfully!");
                break;
        }
    }

    public void checkDeployFireball(Matcher matcher) {
        String lineDirection = matcher.group("lineDirection");
        GameMenuMessages message = GameMenuController.checkDeployFireball(lineDirection);
        switch (message) {
            case INVALID_LINE_DIRECTION:
                System.out.println("Incorrect line direction!");
                break;
            case NOT_IN_BATTLE_DECK:
                System.out.println("You don't have Fireball card in your battle deck!");
                break;
            case NO_CARDS_TO_PLAY_LEFT:
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
                currentPlayer = GameMenuController.getCurrentPlayer();
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