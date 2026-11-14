public class BlockCheckThread extends Thread {
    private final AttemptTracker tracker;
    private final String email;
    private long secondsRemaining;

    public BlockCheckThread(AttemptTracker tracker, String email) {
        super("BlockCheckThread");
        this.tracker = tracker;
        this.email = email;
    }

    @Override
    public void run() {
        secondsRemaining = tracker.remainingSeconds(email);
    }

    // Read after join(), which makes the thread's result visible to its caller.
    public long getSecondsRemaining() { return secondsRemaining; }
}
