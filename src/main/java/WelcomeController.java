import javafx.fxml.FXML;
import javafx.scene.control.Label;

/** Controller for welcome.fxml, shown after a successful login. */
public class WelcomeController {

    @FXML
    private Label welcomeLabel;

    public void setUsername(String username) {
        welcomeLabel.setText("Welcome, " + username + "!");
    }
}