


/**
 * A user (email + password). The constructor validates both and throws
 * IllegalArgumentException if something is wrong, so every User is valid.
 */

public class User {
    // limits as named constants instead of magic numbers
    private static final int MAX_EMAIL_LENGTH = 50;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 12;
    // allowed symbols, ^ included (per expected output)
    private static final String PASSWORD_SYMBOLS = "!@#$%^&*()+";

    private final String username;
    private final String password;

    public User(String username, String password) {
        if (username == null || !isEmailFormatValid(username)) {
            if (username != null && username.length() > MAX_EMAIL_LENGTH) {
                throw new IllegalArgumentException(
                        "Username is too long, try something shorter");
            }
            throw new IllegalArgumentException(
                    "Please enter a valid Email as username");
        }

        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(
                    "Your password is too short, add more characters");
        }
        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(
                    "Your password is too long, try a shorter one");
        }
        if (!isPasswordFormatValid(password)) {
            throw new IllegalArgumentException("Please enter a valid password");
        }

        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private static boolean isEmailFormatValid(String email) {
        if (email.length() > MAX_EMAIL_LENGTH) {
            return false;
        }

        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        if (at <= 0 || at != email.lastIndexOf('@') ||
                dot <= at + 1 || dot >= email.length() - 2) {
            return false;
        }

        String local = email.substring(0, at);
        String domain = email.substring(at + 1, dot);
        String suffix = email.substring(dot + 1);

        for (int i = 0; i < local.length(); i++) {
            char c = local.charAt(i);
            if (!isAsciiLetterOrDigit(c) && "._%+-".indexOf(c) == -1) {
                return false;
            }
        }

        // domain non-empty is already guaranteed by the dot check
        if (!isAsciiLetterOrDigit(domain.charAt(0))) {
            return false;
        }
        for (int i = 0; i < domain.length(); i++) {
            char c = domain.charAt(i);
            if (!isAsciiLetterOrDigit(c) && c != '.' && c != '-') {
                return false;
            }
        }

        for (int i = 0; i < suffix.length(); i++) {
            if (!isAsciiLetter(suffix.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPasswordFormatValid(String password) {
        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (isAsciiLetter(c)) {
                hasLetter = true;
            } else if (c >= '0' && c <= '9') {
                hasDigit = true;
            } else if (PASSWORD_SYMBOLS.indexOf(c) != -1) { // was ascii range '!'..'+'
                hasSymbol = true;
            } else {
                return false;
            }
        }
        return hasLetter && hasDigit && hasSymbol;
    }

    private static boolean isAsciiLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private static boolean isAsciiLetterOrDigit(char c) {
        return isAsciiLetter(c) || (c >= '0' && c <= '9');
    }
}