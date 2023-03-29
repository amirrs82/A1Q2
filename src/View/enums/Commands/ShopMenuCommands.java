package View.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShopMenuCommands {
    BUY_CARD("buy card (?<name>.+)"),
    SELL_CARD("sell card (?<name>.+)")
    ;
    private final String regex;
    ShopMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, ShopMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
