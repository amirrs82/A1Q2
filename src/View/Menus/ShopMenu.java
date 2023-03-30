package View.Menus;

import Controller.ShopMenuController;
import View.enums.Commands.ShopMenuCommands;
import View.enums.Messages.ShopMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {

    public void run(Scanner scanner) {
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if (command.equals("back")) {
                System.out.println("Entered main menu!");
                break;
            } else if (command.equals("show current menu")) System.out.println("Profile Menu");
            else if ((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.BUY_CARD)) != null)
                checkBuyCard(matcher);
            else if ((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.SELL_CARD)) != null)
                checkSellCard(matcher);
            else System.out.println("Invalid command!");
        }
    }

    private void checkBuyCard(Matcher matcher) {
        String name = matcher.group("name");
        ShopMenuMessages message = ShopMenuController.checkBuyCard(name);
        switch (message) {
            case INVALID_CARD_NAME:
                System.out.println("Invalid card name!");
                break;
            case NOT_ENOUGH_GOLD:
                System.out.println("Not enough gold to buy " + name + "!");
                break;
            case CARD_EXIST:
                System.out.println("You have this card!");
                break;
            case SUCCESS:
                System.out.println("Card " + name + " bought successfully!");
                break;
        }
    }

    private void checkSellCard(Matcher matcher) {
        String name = matcher.group("name");
        ShopMenuMessages message = ShopMenuController.checkSellCard(name);
        switch (message) {
            case INVALID_CARD_NAME:
                System.out.println("Invalid card name!");
                break;
            case CARD_NOT_EXIST:
                System.out.println("You don't have this card!");
                break;
            case IN_BATTLE_DECK:
                System.out.println("You cannot sell a card from your battle deck!");
                break;
            case SUCCESS:
                System.out.println("Card " + name + " sold successfully!");
                break;
        }
    }
}
