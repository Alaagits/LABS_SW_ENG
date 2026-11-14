public class FailureWorker extends Thread {
    private final AttemptTracker tracker;

    public FailureWorker(AttemptTracker tracker, String name) {
        super(name);
        this.tracker = tracker;
        setDaemon(true);
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                FailedAttempt attempt = tracker.getFailureQueue().take();
                LoginResult result = tracker.recordFailure(attempt.getEmail());
                attempt.getReply().put(result);
            }
        } catch (InterruptedException e) {
            interrupt();
        }
    }
}
