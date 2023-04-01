package Model;


import Controller.MainMenuController;
import Model.Cards.Barbarian;
import Model.Cards.Card;
import Model.Cards.Fireball;

import java.util.ArrayList;

public class User {

    private final ArrayList<Card> battleDeck = new ArrayList<>();

    private final ArrayList<Card> boughtCards = new ArrayList<>();

    private final ArrayList<Castle> castles = new ArrayList<>();

    private final String username;
    private String password;
    private int gold;
    private int level;
    private int experience;
    private int cardsToPlay;
    private int movesLeft;
    private int castlesDestroyed;

    {
        gold = 100;
        level = 1;
        experience = 0;
        Barbarian barbarian = new Barbarian(2000, 900, 100, "Barbarian");
        Fireball fireball = new Fireball(1600, 100, "Fireball");
        battleDeck.add(barbarian);
        battleDeck.add(fireball);
        boughtCards.add(barbarian);
        boughtCards.add(fireball);
    }

    public ArrayList<Card> getBoughtCards() {
        return boughtCards;
    }

    public Castle getCastleBySide(String side) {
        for (Castle castle : castles)
            if (castle.getSide().equals(side + " castle")) return castle;
        return null;
    }

    public void setCastles() {
        int level = getLevel();
        getCastles().clear();
        getCastles().add(new Castle(level * 3600, level * 600, "middle castle"));
        getCastles().add(new Castle(level * 2500, level * 600, "left castle"));
        getCastles().add(new Castle(level * 2500, level * 600, "right castle"));
    }

    public ArrayList<Castle> getCastles() {
        return castles;
    }

    public int getCastlesDestroyed() {
        return castlesDestroyed;
    }

    public void setCastlesDestroyed(int castlesDestroyed) {
        this.castlesDestroyed = castlesDestroyed;
    }

    public int getCardsToPlay() {
        return cardsToPlay;
    }

    public void setCardsToPlay(int cardsToPlay) {
        this.cardsToPlay = cardsToPlay;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }


    public String getUsername() {
        return username;
    }

    public int getRanking() {
        User currentUser = ClashRoyale.getCurrentUser();
        ArrayList<User> sortedUsers = MainMenuController.sortUsers(ClashRoyale.getUsers());
        int i = 0;
        for (User sortedUser : sortedUsers) {
            if (sortedUser == currentUser) return i + 1;
            i++;
        }
        return 0;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Card> getBattleDeck() {
        return battleDeck;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean isPasswordCorrect(String username, String password) {
        User user = ClashRoyale.getUserByUsername(username);
        return user.getPassword().equals(password);
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
