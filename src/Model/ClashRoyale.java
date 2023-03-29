package Model;

import java.util.ArrayList;

public class ClashRoyale {
    private static final ArrayList<User> users = new ArrayList<>();
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
            if (user.getUsername().equals(username))
                return user;
        return null;
    }
}
