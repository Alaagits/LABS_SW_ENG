import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class AttemptTracker {
    private final ConcurrentHashMap<String, UserState> states = new ConcurrentHashMap<>();
    private final BlockingQueue<FailedAttempt> failureQueue = new LinkedBlockingQueue<>();
    private final FailureWorker[] workers = new FailureWorker[2];
    private final int maxAttempts;
    private final int blockSeconds;

    public AttemptTracker(int maxAttempts, int blockSeconds) {
        if (maxAttempts < 1 || blockSeconds < 1) {
            throw new IllegalArgumentException("n and t must be positive integers");
        }
        this.maxAttempts = maxAttempts;
        this.blockSeconds = blockSeconds;
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new FailureWorker(this, "FailureWorker-" + (i + 1));
            workers[i].start();
        }
    }

    public long remainingSeconds(String email) {
        UserState state = states.get(email);
        return state == null ? 0 : state.remainingSeconds();
    }

    public LoginResult recordFailure(String email) {
        return states.computeIfAbsent(email, key -> new UserState())
                .recordFailure(maxAttempts, blockSeconds);
    }

    public LoginResult recordSuccess(User user) {
        return states.computeIfAbsent(user.getUsername(), key -> new UserState())
                .recordSuccess(user);
    }

    public BlockingQueue<FailedAttempt> getFailureQueue() { return failureQueue; }

    public void shutdown() {
        for (FailureWorker worker : workers) {
            worker.interrupt();
        }
    }
}
