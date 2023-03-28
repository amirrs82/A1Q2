import View.RegisterMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new RegisterMenu().run(scanner);
    }
}
