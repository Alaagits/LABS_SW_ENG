import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FailedAttempt {
    private final String email;
    private final BlockingQueue<LoginResult> reply = new ArrayBlockingQueue<>(1);

    public FailedAttempt(String email) { this.email = email; }
    public String getEmail() { return email; }
    public BlockingQueue<LoginResult> getReply() { return reply; }
}
