package Controller;

import Model.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainMenuController {
    public static ArrayList<User> sortUsers(ArrayList<User> users) {
        ArrayList<User> sortedUsers = new ArrayList<>(users);
        Comparator<User> comparator;
        //sort from small numbers to big numbers
        comparator = Comparator.comparing(User::getLevel);
        //sort from big numbers to small numbers
        comparator = comparator.thenComparing(User::getExperience).reversed();
        //sort lexicographically
        comparator = comparator.thenComparing(User::getUsername);
        sortedUsers.sort(comparator);
        return sortedUsers;
    }
}
