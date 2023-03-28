package Controller;

import Model.ClashRoyale;
import Model.User;
import View.enums.Commands.RegisterMenuCommands;
import View.enums.Messages.RegisterMenuMessages;
import com.sun.tools.javac.Main;

public class RegisterMenuController {
    public static RegisterMenuMessages checkRegister(String username, String password) {
        if (username.matches("[a-z][A-Z]"))
            if (password.matches("^(?!\\d)[^-\\s](?=\\S*[A-Z])(?=\\S*[a-z])(?=\\S*[!@#$%^&*])(?=\\S*[0-9]).{8,20}$"))
                if (!usernameExist(username)) {
                    ClashRoyale.addUser(new User(username, password));
                    return RegisterMenuMessages.SUCCESS;
                } else return RegisterMenuMessages.USERNAME_EXISTS;
            else return RegisterMenuMessages.INCORRECT_PASSWORD_FORMAT;
        else return RegisterMenuMessages.INCORRECT_USERNAME_FORMAT;
    }

    public static RegisterMenuMessages checkLogin(String username, String password) {
        if (checkUsernameFormat(username))
            if (checkPasswordFormat(password))
                if (usernameExist(username))
                    if (isPasswordCorrect(username, password))
                        return RegisterMenuMessages.SUCCESS;
                    else return RegisterMenuMessages.INCORRECT_PASSWORD;
                else return RegisterMenuMessages.USERNAME_NOT_EXISTS;
            else return RegisterMenuMessages.INCORRECT_PASSWORD_FORMAT;
        else return RegisterMenuMessages.INCORRECT_USERNAME_FORMAT;
    }

    private static boolean usernameExist(String userName) {
        //TODO: iterate all users and check if username exist
        return false;
    }

    private static boolean checkUsernameFormat(String username) {
        return username.matches("[a-z][A-Z]");
    }

    private static boolean checkPasswordFormat(String password) {
        return (password.matches("^(?!\\d)[^-\\s](?=\\S*[A-Z])(?=\\S*[a-z])(?=\\S*[!@#$%^&*])(?=\\S*[0-9]).{8,20}$"));
    }

    private static boolean isPasswordCorrect(String username, String password) {
        User user = ClashRoyale.getUserByUsername(username);
        return user.getPassword().equals(password);
    }
}
