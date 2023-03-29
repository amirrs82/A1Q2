package View.Menus;

import Controller.RegisterMenuController;
import Model.ClashRoyale;
import Model.User;
import View.enums.Commands.RegisterMenuCommands;
import View.enums.Messages.RegisterMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterMenu {

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if (command.equals("Exit")) break;
            else if (command.equals("show current menu")) System.out.println("Register/Login Menu");
            else if ((matcher = RegisterMenuCommands.getMatcher(command, RegisterMenuCommands.REGISTER)) != null)
                checkRegister(matcher);
            else if ((matcher = RegisterMenuCommands.getMatcher(command, RegisterMenuCommands.LOGIN)) != null)
                checkLogin(matcher, scanner);
            else System.out.println("Invalid command!");
        }
    }

    public void checkRegister(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        RegisterMenuMessages message = RegisterMenuController.checkRegister(username, password);
        switch (message) {
            case INCORRECT_USERNAME_FORMAT:
                System.out.println("Incorrect format for username!");
                break;
            case INCORRECT_PASSWORD_FORMAT:
                System.out.println("Incorrect format for password!");
                break;
            case USERNAME_EXISTS:
                System.out.println("Username already exists!");
                break;
            case SUCCESS:
                System.out.println("User " + username + " created successfully!");
                break;
        }
    }

    public void checkLogin(Matcher matcher, Scanner scanner) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        RegisterMenuMessages message = RegisterMenuController.checkLogin(username, password);
        switch (message) {
            case INCORRECT_USERNAME_FORMAT:
                System.out.println("Incorrect format for username!");
                break;
            case INCORRECT_PASSWORD_FORMAT:
                System.out.println("Incorrect format for password!");
                break;
            case USERNAME_NOT_EXISTS:
                System.out.println("Username doesn't exist!");
                break;
            case INCORRECT_PASSWORD:
                System.out.println("Password is incorrect!");
                break;
            case SUCCESS:
                System.out.println("User " + username + " logged in!");
                User loggedInUser = ClashRoyale.getUserByUsername(username);
                ClashRoyale.setCurrentUser(loggedInUser);
                new MainMenu().run(scanner);
                break;
        }
    }

}