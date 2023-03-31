package View.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    SHOW_LINE_INFO("show line info (?<lineDirection>.+)"),
    MOVE_TROOP("move troop in line (?<lineDirection>.+) and row (?<rowNumber>.+) (?<direction>.+)"),
    DEPLOY_TROOP("deploy troop (?<troopName>.+) in line (?<lineDirection>.+) and row (?<rowNumber>.+)"),
    DEPLOY_HEAL("deploy spell Heal in line (?<lineDirection>.+) and row (?<rowNumber>.+)"),
    DEPLOY_FIREBALL("deploy spell Fireball in line (?<lineDirection>.+)"),

    ;
    private final String regex;

    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, GameMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
