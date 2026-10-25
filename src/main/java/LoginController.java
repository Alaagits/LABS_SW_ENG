import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        ArrayList<User> users = UsersApp.loadUsers();

        boolean found = false;

        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                found = true;
                break;
            }
        }

        if (found) {
            try {
                FXMLLoader loader =
                        new FXMLLoader(getClass().getResource("/welcome.fxml"));

                Scene welcomeScene = new Scene(loader.load());

                Stage stage =
                        (Stage) loginButton.getScene().getWindow();

                stage.setScene(welcomeScene);

            } catch (IOException e) {
                errorLabel.setText("Could not open welcome screen");
            }

        } else {
            errorLabel.setText("Username or password is incorrect");
        }
    }
}