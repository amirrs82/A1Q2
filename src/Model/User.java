package Model;


import Controller.MainMenuController;
import Model.Cards.Card;

import java.util.ArrayList;

public class User {

    private final ArrayList<Card> battleDeck = new ArrayList<>();

    private final ArrayList<Card> boughtCards;

    private ArrayList<Castle> castles = new ArrayList<>();

    private final String username;
    private String password;
    private int gold;
    private int level;
    private int experience;

    {
        gold = 100;
        level = 1;
        experience = 0;
        battleDeck.add(ClashRoyale.getCardByName("Barbarian"));
        battleDeck.add(ClashRoyale.getCardByName("Fireball"));
        boughtCards = new ArrayList<>(battleDeck);
    }

    public ArrayList<Card> getBoughtCards() {
        return boughtCards;
    }

    public ArrayList<Castle> getCastles() {
        return castles;
    }

    public static void setCastles(User user) {
        int level = user.getLevel();
        user.getCastles().clear();
        user.getCastles().add(new Castle(level * 3600, level * 600, "middle castle"));
        user.getCastles().add(new Castle(level * 2500, level * 600, "right castle"));
        user.getCastles().add(new Castle(level * 2500, level * 600, "left castle"));
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
