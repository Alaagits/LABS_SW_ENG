import javafx.application.Platform;
import java.util.function.LongConsumer;

public class CountdownTask implements Runnable {
    private final AttemptTracker tracker;
    private final String email;
    private final LongConsumer onTick;
    private final Runnable onFinished;

    public CountdownTask(AttemptTracker tracker, String email,
                         LongConsumer onTick, Runnable onFinished) {
        this.tracker = tracker;
        this.email = email;
        this.onTick = onTick;
        this.onFinished = onFinished;
    }

    @Override
    public void run() {
        try {
            long seconds;
            while ((seconds = tracker.remainingSeconds(email)) > 0) {
                long remaining = seconds;
                Platform.runLater(() -> onTick.accept(remaining));
                Thread.sleep(1000);
            }
            Platform.runLater(onFinished);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
