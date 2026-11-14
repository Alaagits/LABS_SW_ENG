public class LoginResult {
    public enum Status { SUCCESS, ERROR, BLOCKED }

    private final Status status;
    private final User user;
    private final int attemptsRemaining;
    private final long secondsRemaining;

    public LoginResult(Status status, User user, int attemptsRemaining, long secondsRemaining) {
        this.status = status;
        this.user = user;
        this.attemptsRemaining = attemptsRemaining;
        this.secondsRemaining = secondsRemaining;
    }

    public Status getStatus() { return status; }
    public User getUser() { return user; }
    public int getAttemptsRemaining() { return attemptsRemaining; }
    public long getSecondsRemaining() { return secondsRemaining; }
}
