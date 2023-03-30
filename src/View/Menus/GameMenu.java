package View.Menus;

import Controller.GameMenuController;
import Model.Cards.Card;
import Model.Castle;
import Model.ClashRoyale;
import Model.User;
import View.enums.Commands.GameMenuCommands;
import View.enums.Messages.GameMenuMessages;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {

    private final User host = ClashRoyale.getCurrentUser();
    private User guest;
    private User currentPlayer;

    public void run(Scanner scanner, String guestUsername, int turnsCount) {
        String command;
        Matcher matcher;
        guest = ClashRoyale.getUserByUsername(guestUsername);
        for (int i = 0; i < turnsCount; i++) {
            command = scanner.nextLine();
            if (command.equals("show current menu")) System.out.println("Game Menu");
            else if (command.equals("show the hitpoints left of my opponent")) showHitPoints();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_LINE_INFO)) != null)
                showLineInfo(matcher);

        }
    }

    private void showHitPoints() {
        for (Castle castle : currentPlayer.getCastles())
            System.out.println(castle.getSide() + ": " + castle.getHitPoint());
    }

    private void showLineInfo(Matcher matcher) {
        String direction = matcher.group("lineDirection");
        GameMenuMessages message = GameMenuController.checkDirection(direction);
        switch (message) {
            case INVALID_LINE:
                System.out.println("Incorrect line direction!");
                break;
            case SUCCESS:
                HashMap<Integer, Card> rows = ClashRoyale.getMap().get(direction);
                rows.forEach((rowNumber, rowCard) -> {
                    if (rowCard != null)
                        System.out.println("row " + rowNumber + ": " + rowCard.getCardName() + ": " + rowCard.getOwner().getUsername());
                });

        }
    }
}
