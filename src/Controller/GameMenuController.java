package Controller;

import Model.Cards.Card;
import Model.Cards.Spell;
import Model.Cards.Troop;
import Model.Castle;
import Model.ClashRoyale;
import Model.User;
import View.enums.Messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.HashMap;


public class GameMenuController {
    private static User host;
    private static User guest;
    private static User currentPlayer;
    private static User otherPlayer;

    public static void startGame(String guestUsername) {
        ClashRoyale.setMap();
        setHost(ClashRoyale.getCurrentUser());
        setGuest(ClashRoyale.getUserByUsername(guestUsername));
        host.setCastles();
        guest.setCastles();
        currentPlayer = host;
        otherPlayer = guest;
    }

    public static GameMenuMessages checkMoveTroop(String lineDirection, String direction, int rowNumber, User currentPlayer) {
        Card userCardInPlace = getCardInPlace(lineDirection, rowNumber, currentPlayer);
        int cardCurrentRow;
        if (checkLineDirection(lineDirection).equals(GameMenuMessages.SUCCESS))
            if (checkRowNumber(rowNumber))
                if (checkDirection(direction).equals(GameMenuMessages.SUCCESS)) {
                    if (direction.equals("downward")) rowNumber = -rowNumber;
                    if (currentPlayer.getMovesLeft() > 0)
                        if (userCardInPlace != null)
                            if ((checkRowNumber(cardCurrentRow = userCardInPlace.getCurrentRow() + rowNumber))) {
                                userCardInPlace.setCurrentRow(cardCurrentRow);
                                return GameMenuMessages.SUCCESS;
                            } else return GameMenuMessages.INVALID_MOVE;
                        else return GameMenuMessages.NO_TROOPS_IN_PLACE;
                    else return GameMenuMessages.NO_MOVES_LEFT;
                } else return GameMenuMessages.INVALID_DIRECTION;
            else return GameMenuMessages.INVALID_ROW;
        else return GameMenuMessages.INVALID_LINE_DIRECTION;
    }

    public static GameMenuMessages checkDeployTroop(String lineDirection, int rowNumber, Troop troop, User currentPlayer) {
        if (troop != null)
            if (currentPlayer.getBattleDeck().contains(troop))
                if (checkLineDirection(lineDirection).equals(GameMenuMessages.SUCCESS))
                    if (checkRowNumber(rowNumber))
                        if (checkDeployedTroopRowNumber(rowNumber, currentPlayer))
                            if (currentPlayer.getCardsToPlay() != 0) {
                                deployCard(lineDirection, rowNumber, troop);
                                return GameMenuMessages.SUCCESS;
                            } else return GameMenuMessages.NO_CARDS_TO_PLAY_LEFT;
                        else return GameMenuMessages.NOT_NEAR_CASTLE;
                    else return GameMenuMessages.INVALID_ROW;
                else return GameMenuMessages.INVALID_LINE_DIRECTION;
            else return GameMenuMessages.NOT_IN_BATTLE_DECK;
        else return GameMenuMessages.INVALID_CARD_NAME;
    }

    public static GameMenuMessages checkDeployHeal(String lineDirection, int rowNumber, User currentPlayer) {
        Spell heal = (Spell) ClashRoyale.getCardByName("Heal");
        if (checkLineDirection(lineDirection) == GameMenuMessages.SUCCESS)
            if (currentPlayer.getBattleDeck().contains(heal))
                if (checkRowNumber(rowNumber))
                    if (currentPlayer.getCardsToPlay() != 0) {
                        deployCard(lineDirection, rowNumber, heal);
                        return GameMenuMessages.SUCCESS;
                    } else return GameMenuMessages.NO_CARDS_TO_PLAY_LEFT;
                else return GameMenuMessages.INVALID_ROW;
            else return GameMenuMessages.NOT_IN_BATTLE_DECK;
        else return GameMenuMessages.INVALID_LINE_DIRECTION;
    }

    public static GameMenuMessages checkDeployFireball(String lineDirection, User currentPlayer) {
        Spell fireball = (Spell) ClashRoyale.getCardByName("Fireball");
        if (checkLineDirection(lineDirection).equals(GameMenuMessages.SUCCESS))
            if (currentPlayer.getBattleDeck().contains(fireball))
                if (currentPlayer.getCardsToPlay() != 0)
                    if (otherPlayer.getCastleBySide(lineDirection).getHitPoint() > 0) {
                        deployCard(lineDirection);
                        return GameMenuMessages.SUCCESS;
                    } else return GameMenuMessages.DESTROYED_CASTLE;
                else return GameMenuMessages.NO_CARDS_TO_PLAY_LEFT;
            else return GameMenuMessages.NOT_IN_BATTLE_DECK;
        else return GameMenuMessages.INVALID_LINE_DIRECTION;
    }

    public static GameMenuMessages nextTurn(int turnsCount, int currentTurn) {
        if (currentTurn <= turnsCount) {
            fight();
            changeTurns();
            return GameMenuMessages.NEXT_TURN;
        } else return GameMenuMessages.END_GAME;
    }

    public static GameMenuMessages checkLineDirection(String direction) {
        if (direction.matches("left|right|middle")) return GameMenuMessages.SUCCESS;
        return GameMenuMessages.INVALID_LINE_DIRECTION;
    }

    public static GameMenuMessages checkDirection(String direction) {
        if (direction.matches("upward|downward")) return GameMenuMessages.SUCCESS;
        return GameMenuMessages.INVALID_DIRECTION;
    }

    public static boolean checkRowNumber(int rowNumber) {
        return rowNumber >= 1 && rowNumber <= 15;
    }

    public static boolean checkDeployedTroopRowNumber(int rowNumber, User currentPlayer) {
        if (currentPlayer.equals(ClashRoyale.getCurrentUser())) return rowNumber >= 1 && rowNumber <= 4; //host
        else return rowNumber <= 15 && rowNumber >= 12; //guest

    }

    public static Card getCardInPlace(String lineDirection, int rowNumber, User user) {
        ArrayList<Card> cardsInPlace = ClashRoyale.getMap().get(lineDirection).get(rowNumber);
        for (Card card : cardsInPlace)
            if (card.getOwner().equals(user)) return card;
        return null;
    }

    private static void deployCard(String lineDirection, int rowNumber, Card card) {
        card.setOwner(currentPlayer);
        ClashRoyale.getMap().get(lineDirection).get(rowNumber).add(card);
    }

    private static void deployCard(String lineDirection) {
        Castle attackedCastle = otherPlayer.getCastleBySide(lineDirection);
        attackedCastle.setHitPoint(attackedCastle.getHitPoint() - 1500);
    }

    public static void fight() {
        HashMap<String,HashMap<Integer, ArrayList<Card>>> map = ClashRoyale.getMap();
        for (HashMap<Integer, ArrayList<Card>> lineDirection : map.values()) {
            for (Integer rowNumber : lineDirection.keySet()) {
            }
        }
    }

    public static void changeTurns() {
        User tempUser = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = tempUser;
    }

    public static User getWinner() {
        return host;
    }

    public static User getHost() {
        return host;
    }

    public static void setHost(User host) {
        GameMenuController.host = host;
    }

    public static User getGuest() {
        return guest;
    }

    public static void setGuest(User guest) {
        GameMenuController.guest = guest;
    }

    public static User getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(User currentPlayer) {
        GameMenuController.currentPlayer = currentPlayer;
    }

    public static User getOtherPlayer() {
        return otherPlayer;
    }

    public static void setOtherPlayer(User otherPlayer) {
        GameMenuController.otherPlayer = otherPlayer;
    }
}
