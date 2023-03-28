package Model;


import Model.Cards.Barbarian;
import Model.Cards.Card;
import Model.Cards.Fireball;

import java.util.ArrayList;

public class User {

    private ArrayList<Card> battleDeck = new ArrayList<>();
    private String username;
    private String password;

    private int gold;
    private int level;
    private int experience;


    {
        gold = 100;
        level = 1;
        experience = 0;
        battleDeck.add(new Barbarian());
        battleDeck.add(new Fireball());
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
