package Controller;

import Model.Cards.Card;
import Model.Cards.Spell;
import Model.Cards.Troop;
import Model.Castle;
import Model.ClashRoyale;
import Model.Place;
import Model.User;
import View.enums.Messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.Iterator;


public class GameMenuController {
    private static User host;
    private static User guest;
    private static User currentPlayer;
    private static User otherPlayer;

    private static Card movedCard;

    public static void startGame(String guestUsername) {
        ClashRoyale.setMap();
        setHost(ClashRoyale.getCurrentUser());
        setGuest(ClashRoyale.getUserByUsername(guestUsername));
        host.setCastles();
        guest.setCastles();
        currentPlayer = host;
        otherPlayer = guest;
    }

    public static GameMenuMessages checkMoveTroop(String lineDirection, String direction, int rowNumber) {
        Place place = ClashRoyale.getPlace(lineDirection, rowNumber);
        int move = 1; //case upward
        if (direction.equals("downward")) move = -1;//case downward
        if (checkLineDirection(lineDirection).equals(GameMenuMessages.SUCCESS))
            if (checkRowNumber(rowNumber))
                if (checkDirection(direction).equals(GameMenuMessages.SUCCESS)) {
                    Card userCardInPlace = getCardInPlace(place, currentPlayer);
                    if (currentPlayer.getMovesLeft() > 0)
                        if (userCardInPlace != null)
                            if ((checkRowNumber(userCardInPlace.getCurrentRow() + move))) {
                                moveTroop(place, move);
                                return GameMenuMessages.SUCCESS;
                            } else return GameMenuMessages.INVALID_MOVE;
                        else return GameMenuMessages.NO_TROOPS_IN_PLACE;
                    else return GameMenuMessages.NO_MOVES_LEFT;
                } else return GameMenuMessages.INVALID_DIRECTION;
            else return GameMenuMessages.INVALID_ROW;
        else return GameMenuMessages.INVALID_LINE_DIRECTION;
    }

    public static GameMenuMessages checkDeployTroop(String lineDirection, int rowNumber, Card card) {
        Place place = ClashRoyale.getPlace(lineDirection, rowNumber);
        if (card != null && checkDeployedCardName(card.getCardName()))
            if (currentPlayer.getBattleDeck().contains(card))
                if (checkLineDirection(lineDirection).equals(GameMenuMessages.SUCCESS))
                    if (checkRowNumber(rowNumber))
                        if (checkDeployedTroopRowNumber(rowNumber, currentPlayer))
                            if (currentPlayer.getCardsToPlay() != 0) {
                                deployCard(place, rowNumber, card);
                                return GameMenuMessages.SUCCESS;
                            } else return GameMenuMessages.NO_CARDS_TO_PLAY_LEFT;
                        else return GameMenuMessages.NOT_NEAR_CASTLE;
                    else return GameMenuMessages.INVALID_ROW;
                else return GameMenuMessages.INVALID_LINE_DIRECTION;
            else return GameMenuMessages.NOT_IN_BATTLE_DECK;
        else return GameMenuMessages.INVALID_CARD_NAME;
    }

    public static GameMenuMessages checkDeployHeal(String lineDirection, int rowNumber, User currentPlayer) {
        Place place = ClashRoyale.getPlace(lineDirection, rowNumber);
        Spell heal = (Spell) ClashRoyale.getCardByName("Heal");
        if (checkLineDirection(lineDirection).equals(GameMenuMessages.SUCCESS))
            if (currentPlayer.getBattleDeck().contains(heal))
                if (checkRowNumber(rowNumber)) if (currentPlayer.getCardsToPlay() != 0) {
                    deployCard(place, rowNumber, heal);
                    return GameMenuMessages.SUCCESS;
                } else return GameMenuMessages.NO_CARDS_TO_PLAY_LEFT;
                else return GameMenuMessages.INVALID_ROW;
            else return GameMenuMessages.NOT_IN_BATTLE_DECK;
        else return GameMenuMessages.INVALID_LINE_DIRECTION;
    }

    public static GameMenuMessages checkDeployFireball(String lineDirection) {
        Spell fireball = (Spell) ClashRoyale.getCardByName("Fireball");
        if (checkLineDirection(lineDirection).equals(GameMenuMessages.SUCCESS))
            if (currentPlayer.getBattleDeck().contains(fireball)) if (currentPlayer.getCardsToPlay() != 0)
                if (otherPlayer.getCastleBySide(lineDirection).getHitPoint() > 0) {
                    deployCard(lineDirection);
                    return GameMenuMessages.SUCCESS;
                } else return GameMenuMessages.DESTROYED_CASTLE;
            else return GameMenuMessages.NO_CARDS_TO_PLAY_LEFT;
            else return GameMenuMessages.NOT_IN_BATTLE_DECK;
        else return GameMenuMessages.INVALID_LINE_DIRECTION;
    }

    public static GameMenuMessages nextTurn(int turnsCount, int currentTurn) {
        if (currentTurn < turnsCount * 2 && (host.getCastlesDestroyed() < 3 || guest.getCastlesDestroyed() < 3)) {
            if (currentPlayer.equals(guest)) {
                fight();
                resetPlayerAbilities();
            }
            changeTurns();
            removeDead();
            return GameMenuMessages.NEXT_TURN;
        } else {
            setPlayersGold();
            setPlayersExperience();
            setPlayersLevel();
            return GameMenuMessages.END_GAME;
        }
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

    public static boolean checkDeployedCardName(String cardName) {
        return cardName.matches("Barbarian|Ice Wizard|Baby Dragon");
    }

    public static boolean checkDeployedTroopRowNumber(int rowNumber, User currentPlayer) {
        if (currentPlayer.equals(ClashRoyale.getCurrentUser())) return rowNumber >= 1 && rowNumber <= 4; //host
        else return rowNumber <= 15 && rowNumber >= 12; //guest

    }

    public static Card getCardInPlace(Place place, User user) {
        ArrayList<Card> cardsInPlace = place.getCards();
        for (Card card : cardsInPlace)
            if (card.getOwner().equals(user) && card instanceof Troop) return card;
        return null;
    }

    public static void moveTroop(Place place, int move) {
        Iterator<Card> iterator = place.getCards().iterator();
        Card card = null;
        while (iterator.hasNext()) {
            card = iterator.next();
            if (card.getOwner().equals(currentPlayer) && card instanceof Troop) {
                card.setCurrentRow(card.getCurrentRow() + move);
                iterator.remove();
                break;
            }
        }
        Place newPlace = ClashRoyale.getPlace(place.getLineDirection(), place.getRowNumber() + move);
        newPlace.getCards().add(card);
        movedCard = card;
        currentPlayer.setMovesLeft(currentPlayer.getMovesLeft() - 1);
    }

    private static void deployCard(Place place, int rowNumber, Card card) {
        place.getCards().add(card);
        card.setOwner(currentPlayer);
        card.setCurrentRow(rowNumber);
        currentPlayer.setCardsToPlay(currentPlayer.getCardsToPlay() - 1);
    }

    private static void deployCard(String lineDirection) {
        Castle attackedCastle = otherPlayer.getCastleBySide(lineDirection);
        attackedCastle.setHitPoint(attackedCastle.getHitPoint() - 1600); //Fireball damage is 1600
        if (attackedCastle.getHitPoint() <= 0) {
            attackedCastle.setHitPoint(0);
            currentPlayer.setCastlesDestroyed(currentPlayer.getCastlesDestroyed() + 1);
        }
        currentPlayer.setCardsToPlay(currentPlayer.getCardsToPlay() - 1);
    }

    public static void fight() {
        ArrayList<Place> map = ClashRoyale.getMap();
        Card firstCardInPlace;
        Card secondCardInPlace;
        for (Place place : map) {
            for (int i = 0; i < place.getCards().size(); i++) {
                firstCardInPlace = place.getCards().get(i);
                if ((firstCardInPlace.getCurrentRow() == 15 || firstCardInPlace.getCurrentRow() == 1) && firstCardInPlace instanceof Troop)
                    fightCastle((Troop) firstCardInPlace, place.getLineDirection());
                for (int j = i + 1; j < place.getCards().size(); j++) {
                    secondCardInPlace = place.getCards().get(j);

                    if (firstCardInPlace instanceof Troop && secondCardInPlace instanceof Troop)
                        if (!firstCardInPlace.getOwner().equals(secondCardInPlace.getOwner())) //if cards are not for one user
                            fight((Troop) firstCardInPlace, (Troop) secondCardInPlace);

                    if (firstCardInPlace instanceof Spell && secondCardInPlace instanceof Troop)
                        if (firstCardInPlace.getOwner().equals(secondCardInPlace.getOwner()))//if cards are for one user
                            heal((Troop) secondCardInPlace);

                    if (firstCardInPlace instanceof Troop && secondCardInPlace instanceof Spell)
                        if (firstCardInPlace.getOwner().equals(secondCardInPlace.getOwner()))//if cards are for one user
                            heal((Troop) firstCardInPlace);
                }
            }
        }
    }

    private static void removeDead() {
        for (Place place : ClashRoyale.getMap()) {
            Iterator<Card> iterator = place.getCards().iterator();
            while (iterator.hasNext()) {
                Card cardInPlace = iterator.next();
                if (cardInPlace instanceof Troop)
                    if (((Troop) cardInPlace).getHitPoint() <= 0)
                        iterator.remove();

                if (cardInPlace instanceof Spell)
                    if (((Spell) cardInPlace).getHealAffect() == 0)
                        iterator.remove();
                    else ((Spell) cardInPlace).setHealAffect(((Spell) cardInPlace).getHealAffect() - 1);
            }

        }
    }

    private static void heal(Troop card) {
        card.setHitPoint(Math.min(card.getHitPoint() + 1000, card.getMaxHitPoint()));
    }

    public static void fight(Troop firstCard, Troop secondCard) {
        int firstCardDamage = firstCard.getDamage();
        int firstCardHitPoint = firstCard.getHitPoint();
        int secondCardDamage = secondCard.getDamage();
        int secondCardHitPoint = secondCard.getHitPoint();
        int netDamage;

        if ((netDamage = firstCardDamage - secondCardDamage) > 0)
            secondCard.setHitPoint(secondCardHitPoint - netDamage);
        else firstCard.setHitPoint(firstCardHitPoint + netDamage); //because netDamage is negative
    }

    public static void fightCastle(Troop troop, String lineDirection) {
        int cardDamage = troop.getDamage();
        int cardHitPoint = troop.getHitPoint();
        int currentRow = troop.getCurrentRow();
        User cardOwner = troop.getOwner();
        if (currentRow == 15 && cardOwner.equals(host))
            fight(troop, lineDirection, cardDamage, cardHitPoint, host);
        else if (currentRow == 1 && cardOwner.equals(guest))
            fight(troop, lineDirection, cardDamage, cardHitPoint, guest);
    }

    private static void fight(Troop troop, String lineDirection, int cardDamage, int cardHitPoint, User user) {
        Castle castle;
        castle = user.getCastleBySide(lineDirection);
        int castleDamage = castle.getDamage();
        int castleHitPoint = castle.getHitPoint();
        troop.setHitPoint(cardHitPoint - castleDamage);
        castle.setHitPoint(castleHitPoint - cardDamage);
        if (castle.getHitPoint() <= 0) {
            castle.setHitPoint(0);
            user.setCastlesDestroyed(user.getCastlesDestroyed() + 1);
        }
    }

    public static void resetPlayerAbilities() {
        currentPlayer.setMovesLeft(3);
        otherPlayer.setMovesLeft(3);
        currentPlayer.setCardsToPlay(1);
        otherPlayer.setCardsToPlay(1);
    }

    public static void changeTurns() {
        User tempUser = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = tempUser;
    }

    public static User getWinner() {
        if (host.getCastlesDestroyed() > guest.getCastlesDestroyed()) return host;
        else if (host.getCastlesDestroyed() < guest.getCastlesDestroyed()) return guest;
        else {
            int hostCastlesHitPoint = getHitPointsLeft(host);
            int guestCastlesHitPoint = getHitPointsLeft(guest);
            if (hostCastlesHitPoint > guestCastlesHitPoint) return host;
            else if (hostCastlesHitPoint < guestCastlesHitPoint) return guest;
            else return null;
        }
    }

    public static void setPlayersGold() {
        host.setGold(host.getGold() + host.getCastlesDestroyed() * 25);
        guest.setGold(guest.getGold() + guest.getCastlesDestroyed() * 25);
    }

    public static void setPlayersExperience() {
        int hostCastlesHitPoint = getHitPointsLeft(host);
        int guestCastlesHitPoint = getHitPointsLeft(guest);
        host.setExperience(host.getExperience() + hostCastlesHitPoint);
        guest.setExperience(guest.getExperience() + guestCastlesHitPoint);
    }

    public static void setPlayersLevel() {
        setPlayerLevel(host);
        setPlayerLevel(guest);
    }

    public static void setPlayerLevel(User user) {
        int userExperience = user.getExperience();
        int userLevel = user.getLevel();
        while (userExperience > 160 * userLevel * userLevel) {
            userExperience -= 160 * userLevel * userLevel;
            userLevel += 1;
            user.setExperience(userExperience);
            user.setLevel(userLevel);
        }
    }

    public static int getHitPointsLeft(User user) {
        int userCastlesHitPoint = 0;
        for (Castle userCastle : user.getCastles()) userCastlesHitPoint += userCastle.getHitPoint();
        return userCastlesHitPoint;
    }

    public static Card getMovedCard() {
        return movedCard;
    }

    public static void setHost(User host) {
        GameMenuController.host = host;
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
