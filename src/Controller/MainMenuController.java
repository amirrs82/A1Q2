package Controller;

import Model.ClashRoyale;
import Model.User;
import View.enums.Messages.MainMenuMessages;

import java.util.ArrayList;

public class MainMenuController {

    public static MainMenuMessages checkStartGame(String turnsCount, String username) {
        if (Controller.checkTurnsCount(turnsCount))
            if (Controller.checkUsernameFormat(username))
                if (ClashRoyale.usernameExist(username))
                    return MainMenuMessages.SUCCESS;
                else return MainMenuMessages.USERNAME_NOT_EXISTS;
            else return MainMenuMessages.INCORRECT_USERNAME_FORMAT;
        else return MainMenuMessages.INVALID_TURNS_COUNT;
    }

    public static String logout(){
        return "User " + ClashRoyale.getCurrentUser().getUsername() + " logged out successfully!";
    }
    public static String showScoreboard() {
        String scoreboardOutput = "";
        ArrayList<User> sortedUsers = new ArrayList<>(Controller.sortUsers(ClashRoyale.getUsers()));
        for (int i = 0; i < 5 && i < sortedUsers.size(); i++) {
            User user = sortedUsers.get(i);
            scoreboardOutput += (i + 1) + "- username: " + user.getUsername() +
                    " level: " + user.getLevel() + " experience: " + user.getExperience() + "\n";
        }
        return scoreboardOutput;
    }

    public static String showUsers() {
        int i = 1;
        String usersOutput = "";
        for (User user : ClashRoyale.getUsers())
            usersOutput += "user " + (i++) + ": " + user.getUsername() + "\n";
        return usersOutput;
    }
}