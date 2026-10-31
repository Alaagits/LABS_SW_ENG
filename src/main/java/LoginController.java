import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for login.fxml.
 * Checks the entered username/password against the users loaded from the file.
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel; // error is shown here instead of a popup

    private List<User> users = new ArrayList<>();

    /** Called from MainApp after the file is read. */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void showError(String message) {
        errorLabel.setText(message);
    }

    // runs when the login button is clicked (onAction in login.fxml)
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        User user = findUser(username, password);

        if (user != null) {
            showWelcomeScreen(user);
        } else {
            showError("user or password do not match");
            passwordField.clear();
        }
    }

    // returns null if no user matches both username and password
    private User findUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // switch the window to the welcome screen
    private void showWelcomeScreen(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/welcome.fxml"));
            Scene welcomeScene = new Scene(loader.load());

            WelcomeController controller = loader.getController();
            controller.setUsername(user.getUsername());

            // same window, just a different scene
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(welcomeScene);
            stage.sizeToScene();
        } catch (IOException e) {
            showError("Could not open the welcome screen");
            e.printStackTrace();
        }
    }
}