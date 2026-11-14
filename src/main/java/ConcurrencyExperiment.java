import java.util.concurrent.CountDownLatch;

public class ConcurrencyExperiment {
    public static String run() throws InterruptedException {
        int threadCount = 20;
        int failuresPerThread = 499;
        int maxAttempts = 10_000;
        String email = "experiment@example.com";
        AttemptTracker tracker = new AttemptTracker(maxAttempts, 30);
        CountDownLatch start = new CountDownLatch(1);
        Thread[] threads = new Thread[threadCount];

        try {
            for (int i = 0; i < threadCount; i++) {
                threads[i] = new Thread(() -> {
                    try {
                        start.await();
                        for (int j = 0; j < failuresPerThread; j++) {
                            if (Thread.currentThread().isInterrupted()) {
                                return;
                            }
                            tracker.recordFailure(email);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "Experiment-" + (i + 1));
                threads[i].start();
            }

            start.countDown();
            for (Thread thread : threads) {
                thread.join();
            }

            int expected = threadCount * failuresPerThread;
            int actual = tracker.getFailures(email);
            boolean firstNineteenUnlocked = true;
            for (int i = 0; i < 19; i++) {
                if (tracker.recordFailure(email).getStatus() == LoginResult.Status.BLOCKED) {
                    firstNineteenUnlocked = false;
                }
            }
            boolean twentiethLocked =
                    tracker.recordFailure(email).getStatus() == LoginResult.Status.BLOCKED;
            boolean passed = actual == expected && firstNineteenUnlocked && twentiethLocked;

            return "Failures after 20 threads: " + actual + " (expected " + expected + ")"
                    + "\nLost updates: " + (expected - actual)
                    + "\nNext 19 attempts stayed unlocked: " + firstNineteenUnlocked
                    + "\n20th extra attempt locked the account: " + twentiethLocked
                    + "\nResult: " + (passed ? "PASS" : "FAIL");
        } finally {
            for (Thread thread : threads) {
                if (thread != null) {
                    thread.interrupt();
                }
            }
            tracker.shutdown();
        }
    }
}
