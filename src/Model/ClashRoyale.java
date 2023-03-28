package Model;

import java.util.ArrayList;

public class ClashRoyale {
    static ArrayList<User> users = new ArrayList<>();
    public static void addUser(User user){
        users.add(user);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
    public static User getUserByUsername(String username){
        for (User user : users)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }
}
