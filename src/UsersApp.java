
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UsersApp {

    public static void main(String[] args) {

        try {
            File file = new File("users.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                String[] parts = line.trim().split("\\s+");

                // Skip lines without two values
                if (parts.length != 2) {
                    System.out.println("Invalid input line: " + line);
                    continue;
                }

                String username = parts[0];
                String password = parts[1];

                User user = new User(username, password);

                // Temporary testing
                System.out.println("Username: " + user.getUsername());
                System.out.println("Password: " + user.getPassword());

                System.out.println(
                        "Length valid: " + user.isUsernameLengthValid()
                );

                System.out.println(
                        "@ valid: " + user.hasAtSymbol()
                );

                System.out.println(
                        "Dot valid: " + user.isDotPositionValid()
                );
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}


// Second class in the same file
class User {

    private String username;
    private String password;

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // 1. Username length validation
    public boolean isUsernameLengthValid() {

        int length = username.length();

        if (length <= 50) {
            return true;
        } else {
            return false;
        }
    }

    // 2. Check @ position
    public boolean hasAtSymbol() {

        int atPosition = username.indexOf("@");

        // TODO: Return true when @ exists
        // and is not the first character.

        return false;
    }

    // 3. Check dot position
    public boolean isDotPositionValid() {

        int atPosition = username.indexOf("@");
        int dotPosition = username.lastIndexOf(".");
        int length = username.length();

        // TODO 1: At least one character
        // between @ and the final dot.

        if( atPosition > 0 &&dotPosition > atPosition +1  && dotPosition <= length -3 ) {
            return true;
        }
        else {
            return false;
        }
    }


    public boolean hasExactlyOneat() {
        int firstAt= username.indexOf("@");
        int lastAt=username.lastIndexOf("@");

        if
    }


}
