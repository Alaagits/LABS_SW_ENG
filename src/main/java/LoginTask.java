import javafx.application.Platform;
import java.util.List;
import java.util.function.Consumer;

public class LoginTask implements Runnable {
    private final String email;
    private final String password;
    private final List<User> users;
    private final AttemptTracker tracker;
    private final Consumer<LoginResult> onResult;

    public LoginTask(String email, String password, List<User> users,
                     AttemptTracker tracker, Consumer<LoginResult> onResult) {
        this.email = email;
        this.password = password;
        this.users = users;
        this.tracker = tracker;
        this.onResult = onResult;
    }

    @Override
    public void run() {
        BlockCheckThread check = new BlockCheckThread(tracker, email);
        try {
            check.start();
            check.join();
            LoginResult result;
            if (check.getSecondsRemaining() > 0) {
                result = new LoginResult(LoginResult.Status.BLOCKED, null, 0,
                        check.getSecondsRemaining());
            } else {
                result = authenticate();
            }
            Platform.runLater(() -> onResult.accept(result));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            check.interrupt();
        }
    }

    private LoginResult authenticate() throws InterruptedException {
        User existing = null;
        for (User user : users) {
            if (user.getUsername().equals(email)) {
                existing = user;
                break;
            }
        }
        if (existing == null) {
            return new LoginResult(LoginResult.Status.ERROR, null, -1, 0);
        }

        boolean valid = false;
        try {
            User entered = new User(email, password);
            valid = existing.getPassword().equals(entered.getPassword());
        } catch (IllegalArgumentException e) {
            // Invalid password format is also a failed attempt for a known email.
        }
        if (valid) {
            return tracker.recordSuccess(existing);
        }
        FailedAttempt attempt = new FailedAttempt(email);
        tracker.getFailureQueue().put(attempt);
        return attempt.getReply().take();
    }
}
