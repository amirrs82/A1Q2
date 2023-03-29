package Controller;

import Model.Cards.Card;
import Model.ClashRoyale;
import Model.User;
import View.enums.Messages.ShopMenuMessages;

import java.util.ArrayList;


public class ShopMenuController {
    public static ShopMenuMessages checkBuyCard(String name) {
        Card card = ClashRoyale.getCardByName(name);
        User currentUser = ClashRoyale.getCurrentUser();
        ArrayList<Card> boughtCards = currentUser.getBoughtCards();
        int currentUserGold = currentUser.getGold();
        if (card != null)
            if (currentUserGold >= card.getPrice())
                if (!boughtCards.contains(card)) {
                    boughtCards.add(card);
                    currentUser.setGold(currentUserGold - card.getPrice());
                    return ShopMenuMessages.SUCCESS;
                } else return ShopMenuMessages.CARD_EXIST;
            else return ShopMenuMessages.NOT_ENOUGH_GOLD;
        else return ShopMenuMessages.INVALID_CARD_NAME;
    }

    public static ShopMenuMessages checkSellCard(String name) {
        Card card = ClashRoyale.getCardByName(name);
        User currentUser = ClashRoyale.getCurrentUser();
        ArrayList<Card> boughtCards = currentUser.getBoughtCards();
        ArrayList<Card> battleDeck = currentUser.getBattleDeck();
        int currentUserGold = currentUser.getGold();

        if (card != null)
            if (boughtCards.contains(card))
                if (!battleDeck.contains(card)) {
                    int addedGold = (int) (card.getPrice() * 0.8);
                    boughtCards.remove(card);
                    currentUser.setGold(currentUserGold + addedGold);
                    return ShopMenuMessages.SUCCESS;
                } else return ShopMenuMessages.IN_BATTLE_DECK;
            else return ShopMenuMessages.CARD_NOT_EXIST;
        else return ShopMenuMessages.INVALID_CARD_NAME;
    }
}
