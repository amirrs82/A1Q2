package Controller;

import Model.Cards.Card;
import Model.ClashRoyale;
import Model.User;
import View.enums.Messages.ProfileMenuMessages;

import java.util.ArrayList;
import java.util.Comparator;

public class ProfileMenuController {

    public static ProfileMenuMessages changePassword(String oldPassword, String newPassword) {
        User user = ClashRoyale.getCurrentUser();
        if (user.isPasswordCorrect(oldPassword))
            if (Controller.checkPasswordFormat(newPassword)) {
                user.setPassword(newPassword);
                return ProfileMenuMessages.SUCCESS;
            } else return ProfileMenuMessages.INCORRECT_PASSWORD_FORMAT;
        else return ProfileMenuMessages.INCORRECT_PASSWORD;
    }

    public static ProfileMenuMessages checkRemoveFromDeck(String name) {
        User user = ClashRoyale.getCurrentUser();
        ArrayList<Card> battleDeck = user.getBattleDeck();
        Card card = ClashRoyale.getCardTypeByName(name);
        if (card != null) {
            if (ClashRoyale.cardExist(battleDeck, name))
                if (battleDeck.size() > 1) {
                    battleDeck.removeIf(card1 -> card1.getCardName().equals(card.getCardName()));
                    return ProfileMenuMessages.SUCCESS;
                } else return ProfileMenuMessages.INVALID_ACTION;
            else return ProfileMenuMessages.CARD_NOT_EXIST;
        } else return ProfileMenuMessages.INVALID_CARD_NAME;
    }

    public static ProfileMenuMessages checkAddToDeck(String name) {
        User user =ClashRoyale.getCurrentUser();
        ArrayList<Card> battleDeck = user.getBattleDeck();
        ArrayList<Card> boughtCards = user.getBoughtCards();
        Card card = ClashRoyale.getCardTypeByName(name);
        if (card != null)
            if (ClashRoyale.cardExist(boughtCards, name))
                if (!ClashRoyale.cardExist(battleDeck, name)) {
                    if (battleDeck.size() != 4) {
                        battleDeck.add(card);
                        return ProfileMenuMessages.SUCCESS;
                    } else return ProfileMenuMessages.INVALID_ACTION;
                } else return ProfileMenuMessages.CARD_EXIST;
            else return ProfileMenuMessages.CARD_NOT_EXIST;
        else return ProfileMenuMessages.INVALID_CARD_NAME;
    }

    public static String showBattleDeck() {
        ArrayList<Card> battleDeck = ClashRoyale.getCurrentUser().getBattleDeck();
        battleDeck.sort(Comparator.comparing(Card::getCardName));
        StringBuilder battleDeckOutput = new StringBuilder();
        for (Card card : battleDeck)
            battleDeckOutput.append(card.getCardName()).append("\n");
        return battleDeckOutput.toString();
    }

    public static String showInfo() {
        User user = ClashRoyale.getCurrentUser();
        String infoOutput = "";
        infoOutput += "username: " + user.getUsername();
        infoOutput += "\npassword: " + user.getPassword();
        infoOutput += "\nlevel: " + user.getLevel();
        infoOutput += "\nexperience: " + user.getExperience();
        infoOutput += "\ngold: " + user.getGold();
        infoOutput += "\nrank: " + ProfileMenuController.getRanking();
        return infoOutput;
    }

    public static int getRanking() {
        User currentUser = ClashRoyale.getCurrentUser();
        ArrayList<User> sortedUsers = Controller.sortUsers(ClashRoyale.getUsers());
        int i = 0;
        for (User sortedUser : sortedUsers) {
            if (sortedUser == currentUser) return i + 1;
            i++;
        }
        return 0;
    }
}
