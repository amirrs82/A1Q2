package Controller;

import Model.ClashRoyale;
import Model.User;
import View.enums.Messages.MainMenuMessages;

import java.util.ArrayList;
import java.util.Comparator;

public class MainMenuController {
    public static MainMenuMessages checkStartGame(String turnsCount, String username) {
        if (checkTurnsCount(turnsCount))
            if (RegisterMenuController.checkUsernameFormat(username))
                if (ClashRoyale.usernameExist(username))
                    return MainMenuMessages.SUCCESS;
                else return MainMenuMessages.USERNAME_NOT_EXISTS;
            else return MainMenuMessages.INCORRECT_USERNAME_FORMAT;
        else return MainMenuMessages.INVALID_TURNS_COUNT;
    }

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

    public static boolean checkTurnsCount(String turnsCount) {
        //matches all numbers from 5 to 30
        return turnsCount.matches("[5-9]|[1-2]\\d|30");
    }
}
