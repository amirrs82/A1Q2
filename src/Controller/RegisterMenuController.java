package Controller;

import Model.ClashRoyale;
import Model.User;
import View.enums.Messages.RegisterMenuMessages;

public class RegisterMenuController {
    public static RegisterMenuMessages checkRegister(String username, String password) {
        if (checkUsernameFormat(username))
            if (checkPasswordFormat(password))
                if (!ClashRoyale.usernameExist(username)) {
                    ClashRoyale.addUser(new User(username, password));
                    return RegisterMenuMessages.SUCCESS;
                } else return RegisterMenuMessages.USERNAME_EXISTS;
            else return RegisterMenuMessages.INCORRECT_PASSWORD_FORMAT;
        else return RegisterMenuMessages.INCORRECT_USERNAME_FORMAT;
    }

    public static RegisterMenuMessages checkLogin(String username, String password) {
        if (checkUsernameFormat(username))
            if (checkPasswordFormat(password))
                if (ClashRoyale.usernameExist(username))
                    if (User.isPasswordCorrect(username, password))
                        return RegisterMenuMessages.SUCCESS;
                    else return RegisterMenuMessages.INCORRECT_PASSWORD;
                else return RegisterMenuMessages.USERNAME_NOT_EXISTS;
            else return RegisterMenuMessages.INCORRECT_PASSWORD_FORMAT;
        else return RegisterMenuMessages.INCORRECT_USERNAME_FORMAT;
    }


    public static boolean checkUsernameFormat(String username) {
        return username.matches("[a-zA-Z]+");
    }

    public static boolean checkPasswordFormat(String password) {
        if (password.matches("^(?!\\d)(?=\\S*[A-Z])(?=\\S*[a-z])(?=\\S*[!@#$%^&*])(?=\\S*[0-9]).{8,20}$"))
            return !password.contains(" ");
        return false;
    }

}
