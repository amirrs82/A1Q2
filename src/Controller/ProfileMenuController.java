package Controller;

import Model.Cards.Card;
import Model.ClashRoyale;
import Model.User;
import View.enums.Messages.ProfileMenuMessages;

import java.util.ArrayList;

public class ProfileMenuController {

    public static ProfileMenuMessages changePassword(String oldPassword, String newPassword, User currentUser) {
        if (User.isPasswordCorrect(currentUser.getUsername(), oldPassword))
            if (RegisterMenuController.checkPasswordFormat(newPassword)) {
                currentUser.setPassword(newPassword);
                return ProfileMenuMessages.SUCCESS;
            } else return ProfileMenuMessages.INCORRECT_PASSWORD_FORMAT;
        else return ProfileMenuMessages.INCORRECT_PASSWORD;
    }

    public static ProfileMenuMessages checkRemoveFromDeck(String name, User currentUser) {
        ArrayList<Card> battleDeck = currentUser.getBattleDeck();
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

    public static ProfileMenuMessages checkAddToDeck(String name, User currentUser) {
        ArrayList<Card> battleDeck = currentUser.getBattleDeck();
        ArrayList<Card> boughtCards = currentUser.getBoughtCards();
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
}
