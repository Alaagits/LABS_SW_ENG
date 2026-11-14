import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
 * Starts background login work and displays its result.
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button experimentButton;

    @FXML
    private Label errorLabel; // error is shown here instead of a popup

    private List<User> users = new ArrayList<>();
    private AttemptTracker tracker;
    private Thread loginThread;
    private Thread countdownThread;
    private Thread experimentThread;
    private boolean closed;

    /** Called from MainApp after the file is read. */
    public void configure(List<User> users, AttemptTracker tracker) {
        this.users = users;
        this.tracker = tracker;
    }

    public void showError(String message) {
        errorLabel.setText(message);
    }

    // runs when the login button is clicked (onAction in login.fxml)
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        setBusy(true);
        showError("");
        loginThread = new Thread(new LoginTask(username, password, users, tracker,
                result -> displayResult(username, result)), "LoginTask");
        loginThread.start();
    }

    @FXML
    private void handleExperiment() {
        setBusy(true);
        experimentThread = new Thread(() -> {
            try {
                String result = ConcurrencyExperiment.run();
                System.out.println(result);
                Platform.runLater(() -> {
                    if (!closed) {
                        setBusy(false);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initOwner(experimentButton.getScene().getWindow());
                        alert.setTitle("Concurrency Test");
                        alert.setHeaderText("Concurrency test results");
                        alert.setContentText(result);
                        alert.show();
                    }
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "ConcurrencyExperiment");
        experimentThread.start();
    }

    private void displayResult(String email, LoginResult result) {
        if (closed) {
            return;
        }
        passwordField.clear();
        if (result.getStatus() == LoginResult.Status.BLOCKED) {
            showBlocked(result.getSecondsRemaining());
            countdownThread = new Thread(new CountdownTask(tracker, email,
                    this::showBlocked, () -> {
                        if (!closed) {
                            showError("");
                            setBusy(false);
                        }
                    }), "CountdownTask");
            countdownThread.start();
        } else {
            setBusy(false);
            if (result.getStatus() == LoginResult.Status.SUCCESS) {
                showWelcomeScreen(result.getUser());
            } else {
                String message = "user or password do not match";
                if (result.getAttemptsRemaining() >= 0) {
                    message += ". Attempts remaining: " + result.getAttemptsRemaining();
                }
                showError(message);
            }
        }
    }

    private void showBlocked(long seconds) {
        if (!closed) {
            showError("Account locked. " + seconds + " seconds remaining.");
        }
    }

    private void setBusy(boolean busy) {
        usernameField.setDisable(busy);
        passwordField.setDisable(busy);
        loginButton.setDisable(busy);
        experimentButton.setDisable(busy);
    }

    public void shutdown() {
        closed = true;
        if (loginThread != null) {
            loginThread.interrupt();
        }
        if (countdownThread != null) {
            countdownThread.interrupt();
        }
        if (experimentThread != null) {
            experimentThread.interrupt();
        }
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
