import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class UsersApp {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("users.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String trimmed = line.trim();
                String[] parts = trimmed.isEmpty() ? new String[0] : trimmed.split("\\s+");

                if (parts.length != 2) {
                    System.out.println(line + " Please enter a valid Email as username");
                    continue;
                }

                try {
                    users.add(new User(parts[0], parts[1]));
                } catch (IllegalArgumentException e) {
                    System.out.println(line + " " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return;
        }

        Collections.sort(users,
                (u1, u2) -> u1.getUsername().compareTo(u2.getUsername()));

        for (User user : users) {
            System.out.println(user.getUsername() + " " + user.getPassword());
        }
    }
}
class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        if (username == null || !isEmailFormatValid(username)) {
            if (username != null && username.length() > 50) {
                throw new IllegalArgumentException(
                        "Username is too long, try something shorter");
            }
            throw new IllegalArgumentException(
                    "Please enter a valid Email as username");
        }

        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException(
                    "Your password is too short, add more characters");
        }
        if (password.length() > 12) {
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
        if (email.length() > 50) {
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

        if (domain.isEmpty() || !isAsciiLetterOrDigit(domain.charAt(0))) {
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
            } else if ((c >= '!' && c <= '+') || c == '@') {
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
