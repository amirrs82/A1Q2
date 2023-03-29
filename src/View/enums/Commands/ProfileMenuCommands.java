package View.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    CHANGE_PASSWORD("change password old password (?<oldPassword>.+) new password (?<newPassword>.+)"),
    REMOVE_FROM_BATTLE_DECK("remove from battle deck (?<name>.+)"),
    ADD_TO_BATTLE_DECK("add to battle deck (?<name>.+)"),
    ;
    private final String regex;

    ProfileMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, ProfileMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
