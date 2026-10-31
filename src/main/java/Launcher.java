
import javafx.application.Application;


/**
 * Main class. Starts MainApp through Application.launch() -
 * running MainApp directly fails when JavaFX is on the classpath.
 */

public class Launcher {
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);

    }
}
