import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


/**
 * Reads Users.txt and returns the valid users.
 * Invalid lines are printed to System.err with the reason and skipped.
 */


public class UsersApp {

    public static ArrayList<User> loadUsers() {

        ArrayList<User> users = new ArrayList<>();
        File file = new File("Users.txt");

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                String trimmed = line.trim();

                if (trimmed.isEmpty()) {
                    continue;
                }

                String[] parts = trimmed.split("\\s+");

                if (parts.length != 2) {
                    System.err.println(
                            line + " Please enter a valid Email as username"
                    );
                    continue;
                }

                try {
                    users.add(new User(parts[0], parts[1]));
                } catch (IllegalArgumentException e) {
                    System.err.println(
                            line + " " + e.getMessage()
                    );
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println(
                    "File not found: " + e.getMessage()
            );
        }

        return users;
    }



    // Lab 1 console version - prints the valid users sorted. Not used by the GUI.

    public static void main(String[] args) {

        ArrayList<User> users = loadUsers();

        Collections.sort(
                users,
                (u1, u2) ->
                        u1.getUsername().compareTo(u2.getUsername())
        );

        for (User user : users) {
            System.out.println(
                    user.getUsername() + " " + user.getPassword()
            );
        }
    }
}