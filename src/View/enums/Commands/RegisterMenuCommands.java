package View.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegisterMenuCommands {
    REGISTER("register username (?<username>.+) password (?<password>.+)"),
    LOGIN("login username (?<username>.+) password (?<password>.+)"),

    ;


    private final String regex;

    RegisterMenuCommands(String regex) {
        this.regex = regex;
    }
    public static Matcher getMatcher(String command, RegisterMenuCommands mainRegex){
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
