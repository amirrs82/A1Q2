package Controller;

import Model.Cards.Card;
import Model.ClashRoyale;
import Model.User;
import View.enums.Messages.ShopMenuMessages;

import java.util.ArrayList;


public class ShopMenuController {
    public static ShopMenuMessages checkBuyCard(String name) {
        User currentUser = ClashRoyale.getCurrentUser();
        ArrayList<Card> boughtCards = currentUser.getBoughtCards();
        Card card = ClashRoyale.getCardTypeByName(name);
        int currentUserGold = currentUser.getGold();
        if (card != null) if (currentUserGold >= card.getPrice()) {
            if (!ClashRoyale.cardExist(boughtCards, name)) {
                card = ClashRoyale.getCardTypeByName(name);
                boughtCards.add(card);
                currentUser.setGold(currentUserGold - card.getPrice());
                return ShopMenuMessages.SUCCESS;
            } else return ShopMenuMessages.CARD_EXIST;
        } else return ShopMenuMessages.NOT_ENOUGH_GOLD;
        else return ShopMenuMessages.INVALID_CARD_NAME;
    }

    public static ShopMenuMessages checkSellCard(String name) {
        User currentUser = ClashRoyale.getCurrentUser();
        ArrayList<Card> boughtCards = currentUser.getBoughtCards();
        ArrayList<Card> battleDeck = currentUser.getBattleDeck();
        Card card = ClashRoyale.getCardTypeByName(name);
        int currentUserGold = currentUser.getGold();
        if (card != null)
            if (ClashRoyale.cardExist(boughtCards, name))
                if (!ClashRoyale.cardExist(battleDeck, name)) {
                    int addedGold = (int) (card.getPrice() * 0.8);
                    boughtCards.removeIf(boughtCard -> boughtCard.getCardName().equals(card.getCardName()));
                    currentUser.setGold(currentUserGold + addedGold);
                    return ShopMenuMessages.SUCCESS;
                } else return ShopMenuMessages.IN_BATTLE_DECK;
            else return ShopMenuMessages.CARD_NOT_EXIST;
        else return ShopMenuMessages.INVALID_CARD_NAME;
    }
}
