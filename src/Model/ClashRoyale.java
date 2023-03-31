package Model;

import Model.Cards.Card;
import Model.Cards.Spell;
import Model.Cards.Troop;


import java.util.ArrayList;
import java.util.HashMap;

public class ClashRoyale {
    static class Map {
        private ArrayList<Integer> rows;
        private ArrayList<String> columns;

    }

    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Card> cards = new ArrayList<>();
    private static final HashMap<String, HashMap<Integer, ArrayList<Card>>> map = new HashMap<>() {{
        put("left", new HashMap<>());
        put("right", new HashMap<>());
        put("middle", new HashMap<>());
    }};
    private static final HashMap<String, HashMap<Card, User>> myMap = new HashMap<>();

    public static void setMyMap() {
        for (int i = 0; i < 15; i++) {
            myMap.put("left", new HashMap<>());
            myMap.put("right", new HashMap<>());
            myMap.put("middle", new HashMap<>());
        }
    }

    static {
        cards.add(new Troop(900, 2000, 100, "Barbarian"));
        cards.add(new Troop(1500, 3500, 180, "Ice Wizard"));
        cards.add(new Troop(1200, 3300, 200, "Baby Dragon"));
        cards.add(new Spell(1600, 100, "Fireball"));
        cards.add(new Spell(1000, 150, "Heal"));//TODO:2000 or 1000?
    }

    public static HashMap<String, HashMap<Integer, ArrayList<Card>>> getMap() {
        return map;
    }

    public static void setMap() {
        for (int i = 0; i < 15; i++) {
            map.get("left").put(i + 1, new ArrayList<>());
            map.get("right").put(i + 1, new ArrayList<>());
            map.get("middle").put(i + 1, new ArrayList<>());
        }
    }

    private static User currentUser;

    public static void addUser(User user) {
        users.add(user);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        ClashRoyale.currentUser = currentUser;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getUserByUsername(String username) {
        for (User user : users)
            if (user.getUsername().equals(username)) return user;
        return null;
    }

    public static boolean usernameExist(String username) {
        return getUserByUsername(username) != null;
    }

    public static Card getCardByName(String name) {
        for (Card card : cards)
            if (card.getCardName().equals(name)) return card;
        return null;
    }

}
