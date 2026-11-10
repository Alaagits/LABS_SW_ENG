
import javafx.application.Application;
import java.util.Scanner;


/**
 * Main class. Starts MainApp through Application.launch() -
 * running MainApp directly fails when JavaFX is on the classpath.
 */

public class Launcher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (!validArguments(args)) {
            System.out.println("Enter two positive integers: n (maximum failed attempts) t (block duration in seconds)");
            if (!scanner.hasNextLine()) {
                System.out.println("No input available. Exiting.");
                return;
            }
            args = scanner.nextLine().trim().split("\\s+");
        }
        Application.launch(MainApp.class, args);
    }

    private static boolean validArguments(String[] args) {
        if (args.length != 2) {
            return false;
        }
        try {
            return Integer.parseInt(args[0]) >= 1 && Integer.parseInt(args[1]) >= 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
