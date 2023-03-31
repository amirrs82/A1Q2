package Model;

import Model.Cards.Card;
import Model.Cards.Spell;
import Model.Cards.Troop;


import java.util.ArrayList;

public class ClashRoyale {

    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Card> cards = new ArrayList<>();
    private static final ArrayList<Place> map = new ArrayList<>();

    static {
        cards.add(new Troop(900, 2000, 100, "Barbarian"));
        cards.add(new Troop(1500, 3500, 180, "Ice Wizard"));
        cards.add(new Troop(1200, 3300, 200, "Baby Dragon"));
        cards.add(new Spell(1600, 100, "Fireball"));
        cards.add(new Spell(1000, 150, "Heal"));//TODO:2000 or 1000?
    }

    public static void setMap() {
        for (int i = 0; i < 15; i++) {
            map.add(new Place("left", i + 1));
            map.add(new Place("middle", i + 1));
            map.add(new Place("right", i + 1));
        }
//        for (int i = 0; i < 15; i++) map.add(new Place("left", i + 1));
//        for (int i = 0; i < 15; i++) map.add(new Place("middle", i + 1));
//        for (int i = 0; i < 15; i++) map.add(new Place("right", i + 1));
    }

    public static ArrayList<Place> getMap() {
        return map;
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

    public static Place getPlace(String lineDirection, int rowNumber) {
        for (Place place : map)
            if (place.getLineDirection().equals(lineDirection) && place.getRowNumber() == rowNumber)
                return place;
        return null;
    }
}
