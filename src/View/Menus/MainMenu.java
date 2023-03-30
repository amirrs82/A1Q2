package View.Menus;

import Controller.MainMenuController;
import Model.ClashRoyale;
import Model.User;
import View.enums.Commands.MainMenuCommands;
import View.enums.Messages.MainMenuMessages;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {
    public void run(Scanner scanner) {
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if (command.equals("logout")) {
                System.out.println("User " + ClashRoyale.getCurrentUser().getUsername() + " logged out successfully!");
                break;
            } else if (command.equals("list of users")) showUsers();
            else if (command.equals("scoreboard")) showScoreboard();
            else if (command.equals("show current menu")) System.out.println("Main Menu");
            else if (command.equals("profile menu")) {
                System.out.println("Entered profile menu!");
                new ProfileMenu().run(scanner);
            } else if (command.equals("shop menu")) {
                System.out.println("Entered shop menu!");
                new ShopMenu().run(scanner);
            } else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.START_GAME)) != null)
                checkStartGame(matcher, scanner);
            else System.out.println("Invalid command!");
        }
    }

    private void showScoreboard() {
        ArrayList<User> sortedUsers = new ArrayList<>(MainMenuController.sortUsers(ClashRoyale.getUsers()));
        for (int i = 0; i < 5 && i < sortedUsers.size(); i++) {
            User user = sortedUsers.get(i);
            System.out.println((i + 1) + "- username: " + user.getUsername() + " level: " + user.getLevel() + " experience: " + user.getExperience());
        }
    }

    private void showUsers() {
        int i = 0;
        for (User user : ClashRoyale.getUsers())
            System.out.println("user " + ++i + ": " + user.getUsername());
    }

    private void checkStartGame(Matcher matcher, Scanner scanner) {
        String username = matcher.group("username");
        String turnsCount = matcher.group("turnsCount");
        MainMenuMessages message = MainMenuController.checkStartGame(turnsCount, username);
        switch (message) {
            case INVALID_TURNS_COUNT:
                System.out.println("Invalid turns count!");
                break;
            case INCORRECT_USERNAME_FORMAT:
                System.out.println("Incorrect format for username!");
                break;
            case USERNAME_NOT_EXISTS:
                System.out.println("Username doesn't exist!");
                break;
            case SUCCESS:
                System.out.println("Battle started with user " + username);
                new GameMenu().run(scanner, username, Integer.parseInt(turnsCount));
                break;
        }
    }
}
