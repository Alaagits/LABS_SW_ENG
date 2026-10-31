import javafx.fxml.FXML;
import javafx.scene.control.Label;

//Controller of the welcome screen ({@code welcome.fxml}),
//shown after a successful login.

public class WelcomeController {

    //Displays the welcome message.
    @FXML
    private Label welcomeLabel;


     // Personalizes the welcome message with the logged-in user's name.
     //@param username the username (email) of the logged-in user

    public void setUsername(String username) {
        welcomeLabel.setText("Welcome, " + username + "!");
    }
}