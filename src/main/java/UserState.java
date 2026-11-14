public class UserState {
    private int failures;
    private long blockedUntil;

    public synchronized long remainingSeconds() {
        long remaining = blockedUntil - System.currentTimeMillis();
        if (blockedUntil != 0 && remaining <= 0) {
            failures = 0;
            blockedUntil = 0;
        }
        return remaining > 0 ? (remaining + 999) / 1000 : 0;
    }

    public synchronized LoginResult recordFailure(int maxAttempts, int blockSeconds) {
        long seconds = remainingSeconds();
        if (seconds > 0) {
            return new LoginResult(LoginResult.Status.BLOCKED, null, 0, seconds);
        }
        failures++;
        if (failures >= maxAttempts) {
            blockedUntil = System.currentTimeMillis() + blockSeconds * 1000L;
            return new LoginResult(LoginResult.Status.BLOCKED, null, 0, blockSeconds);
        }
        return new LoginResult(LoginResult.Status.ERROR, null, maxAttempts - failures, 0);
    }

    public synchronized LoginResult recordSuccess(User user) {
        long seconds = remainingSeconds();
        if (seconds > 0) {
            return new LoginResult(LoginResult.Status.BLOCKED, null, 0, seconds);
        }
        failures = 0;
        return new LoginResult(LoginResult.Status.SUCCESS, user, 0, 0);
    }

    public synchronized int getFailures() {
        remainingSeconds();
        return failures;
    }
}
