package View;

import Controller.MainMenuController;
import Model.ClashRoyale;
import Model.User;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    public void run(Scanner scanner) {

        outer:
        while (true) {
            String command = scanner.nextLine().trim();
            switch (command) {
                case "logout":
                    break outer;
                case "show current menu":
                    System.out.println("Main Menu");
                    break;
                case "profile menu":
                    System.out.println("Entered profile menu!");
                    new ProfileMenu().run(scanner);
                    break;
                case "list of users":
                    showUsers();
                    break;
                case "scoreboard":
                    showScoreboard();
                    break;
            }
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
}
