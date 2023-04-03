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
        resetPlayerAbilities();
        currentPlayer.setCastlesDestroyed(0);
        otherPlayer.setCastlesDestroyed(0);
    }

    public static String showHitPoints() {
        String hitPointOutput = "";
        Castle middleCastle = otherPlayer.getCastleBySide("middle");
        Castle leftCastle = otherPlayer.getCastleBySide("left");
        Castle rightCastle = otherPlayer.getCastleBySide("right");
        hitPointOutput += "middle castle: ";
        if (middleCastle != null && middleCastle.getHitPoint() > 0)
            hitPointOutput += (middleCastle.getHitPoint() + "\n");
        else hitPointOutput += ("-1\n");

        hitPointOutput += "left castle: ";
        if (leftCastle != null && leftCastle.getHitPoint() > 0) hitPointOutput += (leftCastle.getHitPoint() + "\n");
        else hitPointOutput += ("-1\n");

        hitPointOutput += "right castle: ";
        if (rightCastle != null && rightCastle.getHitPoint() > 0) hitPointOutput += (rightCastle.getHitPoint() + "\n");
        else hitPointOutput += ("-1\n");
        return hitPointOutput;
    }

    public static String printMaps(String lineDirection) {
        String mapOutput = "";
        mapOutput += lineDirection + " line:\n";
        for (Place place : ClashRoyale.getMap()) {
            ArrayList<Card> cardsInPlace = place.getCards();
            if (place.getLineDirection().equals(lineDirection))
                for (Card card : cardsInPlace) {
                    User owner = card.getOwner();
                    mapOutput += "row " + place.getRowNumber() + ": " + card.getCardName() + ": " + owner.getUsername() + "\n";
                }
        }
        return mapOutput;
    }

    public static String getCardsToPlay() {
        return "You can play " + currentPlayer.getCardsToPlay() + " cards more!";
    }

    public static String getMovesLeft() {
        return "You have " + currentPlayer.getMovesLeft() + " moves left!";
    }

    public static GameMenuMessages checkMoveTroop(String lineDirection, String direction, int rowNumber) {
        Place place = ClashRoyale.getPlace(lineDirection, rowNumber);
        int move = 1; //case upward
        if (direction.equals("downward")) move = -1;//case downward
        if (checkLineDirection(lineDirection).equals(GameMenuMessages.SUCCESS))
            if (Controller.checkRowNumber(rowNumber))
                if (checkDirection(direction).equals(GameMenuMessages.SUCCESS)) {
                    Troop userTroopInPlace = getTroopInPlace(place, currentPlayer);
                    if (currentPlayer.getMovesLeft() > 0)
                        if (userTroopInPlace != null)
                            if ((Controller.checkRowNumber(userTroopInPlace.getCurrentRow() + move))) {
                                moveTroop(place, userTroopInPlace, move);
                                return GameMenuMessages.SUCCESS;
                            } else return GameMenuMessages.INVALID_MOVE;
                        else return GameMenuMessages.NO_TROOPS_IN_PLACE;
                    else return GameMenuMessages.NO_MOVES_LEFT;
                } else return GameMenuMessages.INVALID_DIRECTION;
            else return GameMenuMessages.INVALID_ROW;
        else return GameMenuMessages.INVALID_LINE_DIRECTION;
    }

    public static String moveTroop(String lineDirection) {
        Troop troop = (Troop) GameMenuController.getMovedCard();
        String cardName = troop.getCardName();
        int currentRow = troop.getCurrentRow();
        return cardName + " moved successfully to row " + currentRow + " in line " + lineDirection;
    }

    public static GameMenuMessages checkDeployTroop(String lineDirection, int rowNumber, String troopName) {
        Card card = ClashRoyale.getCardTypeByName(troopName);
        ArrayList<Card> battleDeck = currentPlayer.getBattleDeck();
        Place place = ClashRoyale.getPlace(lineDirection, rowNumber);

        if (card != null && Controller.checkDeployedCardName(card.getCardName()))
            if (ClashRoyale.cardExist(battleDeck, card.getCardName()))
                if (checkLineDirection(lineDirection).equals(GameMenuMessages.SUCCESS))
                    if (Controller.checkRowNumber(rowNumber))
                        if (Controller.checkDeployedTroopRowNumber(rowNumber, currentPlayer))
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

    public static GameMenuMessages checkDeployHeal(String lineDirection, int rowNumber) {
        Place place = ClashRoyale.getPlace(lineDirection, rowNumber);
        ArrayList<Card> battleDeck = currentPlayer.getBattleDeck();
        Spell heal = (Spell) ClashRoyale.getCardTypeByName("Heal");
        if (checkLineDirection(lineDirection).equals(GameMenuMessages.SUCCESS))
            if (ClashRoyale.cardExist(battleDeck, "Heal"))
                if (Controller.checkRowNumber(rowNumber))
                    if (currentPlayer.getCardsToPlay() != 0) {
                        deployCard(place, rowNumber, heal);
                        return GameMenuMessages.SUCCESS;
                    } else return GameMenuMessages.NO_CARDS_TO_PLAY_LEFT;
                else return GameMenuMessages.INVALID_ROW;
            else return GameMenuMessages.NOT_IN_BATTLE_DECK;
        else return GameMenuMessages.INVALID_LINE_DIRECTION;
    }

    public static GameMenuMessages checkDeployFireball(String lineDirection) {
        ArrayList<Card> battleDeck = currentPlayer.getBattleDeck();
        if (checkLineDirection(lineDirection).equals(GameMenuMessages.SUCCESS))
            if (ClashRoyale.cardExist(battleDeck, "Fireball"))
                if (currentPlayer.getCardsToPlay() != 0)
                    if (otherPlayer.getCastleBySide(lineDirection) != null) {
                        deployCard(lineDirection);
                        return GameMenuMessages.SUCCESS;
                    } else return GameMenuMessages.DESTROYED_CASTLE;
                else return GameMenuMessages.NO_CARDS_TO_PLAY_LEFT;
            else return GameMenuMessages.NOT_IN_BATTLE_DECK;
        else return GameMenuMessages.INVALID_LINE_DIRECTION;
    }

    public static GameMenuMessages nextTurn(int turnsCount, int currentTurn) {
        if (currentTurn < turnsCount * 2 && host.getCastlesDestroyed() < 3 && guest.getCastlesDestroyed() < 3) {
            healAffect();
            if (currentPlayer.equals(guest)) {
                fight();
                resetPlayerAbilities();
                removeDead();
            }
            changeTurns();
            return GameMenuMessages.NEXT_TURN;
        } else {
            if (currentPlayer.equals(guest)) {
                fight();
                setPlayersGold();
                setPlayersExperience();
                setPlayersLevel();
                return GameMenuMessages.END_GAME;
            } else {
                changeTurns();
                return GameMenuMessages.NEXT_TURN;
            }
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

    public static Troop getTroopInPlace(Place place, User user) {
        ArrayList<Card> cardsInPlace = place.getCards();
        for (Card card : cardsInPlace)
            if (card.getOwner().equals(user) && card instanceof Troop) return (Troop) card;
        return null;
    }

    public static void moveTroop(Place place, Troop userTroopInPlace, int move) {
        Place newPlace = ClashRoyale.getPlace(place.getLineDirection(), place.getRowNumber() + move);
        newPlace.getCards().add(userTroopInPlace);
        userTroopInPlace.setCurrentRow(userTroopInPlace.getCurrentRow() + move);
        place.getCards().removeIf(userTroopInPlace::equals);
        movedCard = userTroopInPlace;
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
        ArrayList<Place> map = ClashRoyale.getMap();
        for (Place place : map) {
            ArrayList<Card> cardsInPlace = place.getCards();
            cardsInPlace.removeIf(cardInPlace -> cardInPlace instanceof Troop && ((Troop) cardInPlace).getHitPoint() <= 0);
            removeDestroyedCastles(host);
            removeDestroyedCastles(guest);
        }
    }

    private static void healAffect() {
        ArrayList<Place> map = ClashRoyale.getMap();
        for (Place place : map) {
            Iterator<Card> iterator = place.getCards().iterator();
            while (iterator.hasNext()) {
                Card cardInPlace = iterator.next();
                if (cardInPlace instanceof Spell)
                    if (((Spell) cardInPlace).getHealAffect() == 0)
                        iterator.remove();
                    else ((Spell) cardInPlace).setHealAffect(((Spell) cardInPlace).getHealAffect() - 1);
            }
        }
    }

    private static void removeDestroyedCastles(User user) {
        user.getCastles().removeIf(castle -> castle.getHitPoint() <= 0);
    }

    private static void heal(Troop card) {
        card.setHitPoint(Math.min(card.getHitPoint() + 1000, card.getMaxHitPoint()));//heal givenHitPoint is 1000
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
            fight(troop, lineDirection, cardDamage, cardHitPoint, host, guest);
        else if (currentRow == 1 && cardOwner.equals(guest))
            fight(troop, lineDirection, cardDamage, cardHitPoint, guest, host);
    }

    private static void fight(Troop troop, String lineDirection, int cardDamage, int cardHitPoint, User attacker, User defender) {
        Castle castle;
        castle = defender.getCastleBySide(lineDirection);
        if (castle != null) {
            int castleDamage = castle.getDamage();
            int castleHitPoint = castle.getHitPoint();
            troop.setHitPoint(cardHitPoint - castleDamage);
            castle.setHitPoint(castleHitPoint - cardDamage);
            if (castle.getHitPoint() <= 0) {
                castle.setHitPoint(0);
                attacker.setCastlesDestroyed(attacker.getCastlesDestroyed() + 1);
            }
        }
    }

    public static void resetPlayerAbilities() {
        currentPlayer.setMovesLeft(3);
        currentPlayer.setCardsToPlay(1);
        otherPlayer.setMovesLeft(3);
        otherPlayer.setCardsToPlay(1);
    }

    public static void changeTurns() {
        User tempUser = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = tempUser;
    }

    public static String getWinner() {
        String wins = "Game has ended. Winner: ";
        String hostWins = wins + host.getUsername();
        String guestWins = wins + guest.getUsername();
        if (host.getCastlesDestroyed() > guest.getCastlesDestroyed()) return hostWins;
        else if (host.getCastlesDestroyed() < guest.getCastlesDestroyed()) return guestWins;
        else {
            int hostCastlesHitPoint = getHitPointsLeft(host);
            int guestCastlesHitPoint = getHitPointsLeft(guest);
            if (hostCastlesHitPoint > guestCastlesHitPoint) return hostWins;
            else if (hostCastlesHitPoint < guestCastlesHitPoint) return guestWins;
            else return "Game has ended. Result: Tie";
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
        for (Castle userCastle : user.getCastles())
            userCastlesHitPoint += userCastle.getHitPoint();
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
}