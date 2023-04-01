package Model;

import Model.Cards.*;


import java.util.ArrayList;

public class ClashRoyale {

    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Place> map = new ArrayList<>();

    public static void setMap() {
        map.clear();
        for (int i = 0; i < 15; i++) {
            map.add(new Place("left", i + 1));
            map.add(new Place("middle", i + 1));
            map.add(new Place("right", i + 1));
        }
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

    public static boolean cardExist(ArrayList<Card> cards, String name) {
        for (Card card : cards)
            if (card.getCardName().equals(name)) return true;
        return false;
    }

    public static Card getCardTypeByName(String name) {
        switch (name) {
            case "Barbarian":
                return new Barbarian(900, 2000, 100, name);
            case "Ice Wizard":
                return new IceWizard(1500, 3500, 180, name);
            case "Baby Dragon":
                return new BabyDragon(1200, 3300, 200, name);
            case "Fireball":
                return new Fireball(1600, 100, name);
            case "Heal":
                return new Heal(1000, 150, name);
            default:
                return null;
        }
    }

    public static Place getPlace(String lineDirection, int rowNumber) {
        for (Place place : map)
            if (place.getLineDirection().equals(lineDirection) && place.getRowNumber() == rowNumber)
                return place;
        return null;
    }
}
