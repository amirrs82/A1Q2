package Controller;

import Model.User;
import View.enums.Messages.GameMenuMessages;


public class GameMenuController {
    public static void startGame(User host, User guest) {
        User.setCastles(host);
        User.setCastles(guest);
    }

    public static GameMenuMessages checkDirection(String direction) {
        if (direction.matches("left|right|middle"))
            return GameMenuMessages.SUCCESS;
        return GameMenuMessages.INVALID_LINE;
    }

}
