import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JavaFX entry class.
 * Reads the users file first, then opens the login screen.
 */
public class MainApp extends Application {

    private ArrayList<User> users;

    // runs before start(), so the file is read once before the window opens
    @Override
    public void init() {
        users = UsersApp.loadUsers();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/login.fxml"));
        Scene scene = new Scene(loader.load());

        // pass the users to the login controller
        LoginController controller = loader.getController();
        controller.setUsers(users);

        if (users.isEmpty()) {
            controller.showError("No valid users loaded - check Users.txt");
        }

        // closing the window (X) exits the program
        stage.setOnCloseRequest(event -> Platform.exit());

        stage.setTitle("GCM Login");
        stage.setScene(scene);
        stage.show();
    }
}