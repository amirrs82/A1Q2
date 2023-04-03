package Controller;

import Model.ClashRoyale;
import Model.User;
import View.enums.Messages.RegisterMenuMessages;

public class RegisterMenuController {
    public static RegisterMenuMessages checkRegister(String username, String password) {
        if (Controller.checkUsernameFormat(username))
            if (Controller.checkPasswordFormat(password))
                if (!ClashRoyale.usernameExist(username)) {
                    ClashRoyale.addUser(new User(username, password));
                    return RegisterMenuMessages.SUCCESS;
                } else return RegisterMenuMessages.USERNAME_EXISTS;
            else return RegisterMenuMessages.INCORRECT_PASSWORD_FORMAT;
        else return RegisterMenuMessages.INCORRECT_USERNAME_FORMAT;
    }

    public static RegisterMenuMessages checkLogin(String username, String password) {
        User user = ClashRoyale.getUserByUsername(username);
        if (Controller.checkUsernameFormat(username))
            if (Controller.checkPasswordFormat(password))
                if (user != null)
                    if (user.isPasswordCorrect(password)) {
                        ClashRoyale.setCurrentUser(user);
                        return RegisterMenuMessages.SUCCESS;
                    } else return RegisterMenuMessages.INCORRECT_PASSWORD;
                else return RegisterMenuMessages.USERNAME_NOT_EXISTS;
            else return RegisterMenuMessages.INCORRECT_PASSWORD_FORMAT;
        else return RegisterMenuMessages.INCORRECT_USERNAME_FORMAT;
    }
}
