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
        Card card = ClashRoyale.getCardByName(name);
        ArrayList<Card> battleDeck = currentUser.getBattleDeck();
        if (card != null)
            if (battleDeck.contains(card))
                if (battleDeck.size() > 1) {
                    battleDeck.remove(card);
                    return ProfileMenuMessages.SUCCESS;
                } else return ProfileMenuMessages.INVALID_ACTION;
            else return ProfileMenuMessages.CARD_NOT_EXIST;
        else return ProfileMenuMessages.INVALID_CARD_NAME;
    }

    public static ProfileMenuMessages checkAddToDeck(String name, User currentUser) {
        Card card = ClashRoyale.getCardByName(name);
        ArrayList<Card> battleDeck = currentUser.getBattleDeck();
        ArrayList<Card> boughtCards = currentUser.getBoughtCards();
        if (card != null)
            if (boughtCards.contains(card))
                if (!battleDeck.contains(card))
                    if (boughtCards.size() != 4) {
                        battleDeck.add(card);
                        return ProfileMenuMessages.SUCCESS;
                    } else return ProfileMenuMessages.INVALID_ACTION;
                else return ProfileMenuMessages.CARD_EXIST;
            else return ProfileMenuMessages.CARD_NOT_EXIST;
        else return ProfileMenuMessages.INVALID_CARD_NAME;
    }
}
