package Controller;

import Model.ClashRoyale;
import Model.User;

import java.util.ArrayList;
import java.util.Comparator;

public class Controller {
    //RegisterMenu and MainMenu Controller
    public static boolean checkUsernameFormat(String username) {
        return username.matches("[a-zA-Z]+");
    }

    //RegisterMenu and ProfileMenu Controller
    public static boolean checkPasswordFormat(String password) {
        if (password.matches("^(?!\\d)(?=\\S*[A-Z])(?=\\S*[a-z])(?=\\S*[!@#$%^&*])(?=\\S*[0-9]).{8,20}$"))
            return !password.contains(" ");
        return false;
    }

    //User and MainMenu
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

    //MainMenu Controller
    public static boolean checkTurnsCount(String turnsCount) {
        //matches all numbers from 5 to 30
        return turnsCount.matches("[5-9]|[1-2]\\d|30");
    }

    //GameMenu Controller
    public static boolean checkRowNumber(int rowNumber) {
        return rowNumber >= 1 && rowNumber <= 15;
    }

    public static boolean checkDeployedCardName(String cardName) {
        return cardName.matches("Barbarian|Ice Wizard|Baby Dragon");
    }

    public static boolean checkDeployedTroopRowNumber(int rowNumber, User currentPlayer) {
        if (currentPlayer.equals(ClashRoyale.getCurrentUser())) return rowNumber >= 1 && rowNumber <= 4; //host
        else return rowNumber <= 15 && rowNumber >= 12; //guest

    }
}
