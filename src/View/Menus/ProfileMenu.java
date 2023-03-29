package View.Menus;

import Controller.ProfileMenuController;
import Model.Cards.Card;
import Model.ClashRoyale;
import Model.User;
import View.enums.Commands.ProfileMenuCommands;
import View.enums.Messages.ProfileMenuMessages;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;


public class ProfileMenu {
    private final User currentUser = ClashRoyale.getCurrentUser();

    public void run(Scanner scanner) {
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if (command.equals("back")) {
                System.out.println("Entered main menu!");
                break;
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_PASSWORD)) != null)
                changePassword(matcher);
            else if (command.equals("Info")) showInfo();
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.REMOVE_FROM_BATTLE_DECK)) != null)
                checkRemoveFromDeck(matcher);
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.ADD_TO_BATTLE_DECK)) != null)
                checkAddToDeck(matcher);
            else if (command.equals("show battle deck")) showBattleDeck();
            else System.out.println("Invalid command!");
        }
    }

    private void changePassword(Matcher matcher) {
        String oldPassword = matcher.group("oldPassword");
        String newPassword = matcher.group("newPassword");
        ProfileMenuMessages message = ProfileMenuController.changePassword(oldPassword, newPassword, currentUser);
        switch (message) {
            case INCORRECT_PASSWORD:
                System.out.println("Incorrect password!");
                break;
            case INCORRECT_PASSWORD_FORMAT:
                System.out.println("Incorrect format for new password!");
                break;
            case SUCCESS:
                System.out.println("Password changed successfully!");
                break;
        }
    }

    private void showInfo() {
        System.out.println("username: " + currentUser.getUsername());
        System.out.println("password: " + currentUser.getPassword());
        System.out.println("level: " + currentUser.getLevel());
        System.out.println("experience: " + currentUser.getExperience());
        System.out.println("gold: " + currentUser.getGold());
        System.out.println("rank: " + currentUser.getRanking());
    }

    private void checkRemoveFromDeck(Matcher matcher) {
        String name = matcher.group("name");
        ProfileMenuMessages message = ProfileMenuController.checkRemoveFromDeck(name);
        switch (message) {
            case INVALID_CARD_NAME:
                System.out.println("Invalid card name!");
                break;
            case CARD_NOT_EXIST:
                System.out.println("This card isn't in your battle deck!");
                break;
            case INVALID_ACTION:
                System.out.println("Invalid action: your battle deck will be empty!");
                break;
            case SUCCESS:
                System.out.println("Card " + name + " removed successfully!");
                break;
        }
    }

    private void checkAddToDeck(Matcher matcher) {
        String name = matcher.group("name");
        ProfileMenuMessages message = ProfileMenuController.checkAddToDeck(name);
        switch (message) {
            case INVALID_CARD_NAME:
                System.out.println("Invalid card name!");
                break;
            case CARD_NOT_EXIST:
                System.out.println("You don't have this card!");
                break;
            case CARD_EXIST:
                System.out.println("This card is already in your battle deck!");
                break;
            case INVALID_ACTION:
                System.out.println("Invalid action: your battle deck is full!");
                break;
            case SUCCESS:
                System.out.println("Card " + name + " added successfully!");
                break;
        }
    }

    public void showBattleDeck() {
        ArrayList<Card> battleDeck = currentUser.getBattleDeck();
        battleDeck.sort(Comparator.comparing(Card::getCardName));
        for (Card card : battleDeck)
            System.out.println(card.getCardName());
    }
}
