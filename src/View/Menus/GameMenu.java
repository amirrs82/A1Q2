package View.Menus;

import Controller.GameMenuController;
import View.enums.Commands.GameMenuCommands;
import View.enums.Messages.GameMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private boolean end = false;

    public void run(Scanner scanner, String guestUsername, int turnsCount) {
        GameMenuController.startGame(guestUsername);
        String command;
        Matcher matcher;
        int currentTurn = 1;
        while (!end) {
            command = scanner.nextLine();
            if (command.equals("show current menu")) System.out.println("Game Menu");
            else if (command.equals("show the hitpoints left of my opponent")) showHitPoints();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_LINE_INFO)) != null)
                showLineInfo(matcher);
            else if (command.equals("number of cards to play"))
                System.out.println(GameMenuController.getCardsToPlay());
            else if (command.equals("number of moves left"))
                System.out.println(GameMenuController.getMovesLeft());
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
        System.out.print(GameMenuController.showHitPoints());
    }

    private void showLineInfo(Matcher matcher) {
        String lineDirection = matcher.group("lineDirection");
        GameMenuMessages message = GameMenuController.checkLineDirection(lineDirection);
        switch (message) {
            case INVALID_LINE_DIRECTION:
                System.out.println("Incorrect line direction!");
                break;
            case SUCCESS:
                System.out.print(GameMenuController.printMaps(lineDirection));
                break;
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
                System.out.println(GameMenuController.moveTroop(lineDirection));
                break;
        }
    }

    private void checkDeployTroop(Matcher matcher) {
        String troopName = matcher.group("troopName");
        String lineDirection = matcher.group("lineDirection");
        int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
        GameMenuMessages message = GameMenuController.checkDeployTroop(lineDirection, rowNumber, troopName);
        switch (message) {
            case INVALID_CARD_NAME:
                System.out.println("Invalid troop name!");
                break;
            case NOT_IN_BATTLE_DECK:
                System.out.println("You don't have " + troopName + " card in your battle deck!");
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
                System.out.println("You have deployed " + troopName + " successfully!");
                break;
        }
    }

    public void checkDeployHeal(Matcher matcher) {
        String lineDirection = matcher.group("lineDirection");
        int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
        GameMenuMessages message = GameMenuController.checkDeployHeal(lineDirection, rowNumber);
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
                String currentPlayerName = GameMenuController.getCurrentPlayer().getUsername();
                System.out.println("Player " + currentPlayerName + " is now playing!");
                break;
            case END_GAME:
                end = true;
                System.out.println(GameMenuController.getWinner());
                break;
        }
    }
}