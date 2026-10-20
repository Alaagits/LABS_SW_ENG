import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UsersApp {
    private String Username;
    private String Password;


    // continue from here
    public User(){

    }
    public static void main(String[] args) {

        try {
            // TODO 1: Open users.txt
            File file = new File("users.txt");

            // TODO 2: Create the Scanner
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                // TODO 3: Split the line by whitespace
                String[] parts = line.split("\\s+");

                // TODO 4: Extract the username
                String username = parts[0];

                // TODO 5: Extract the password
                String password =parts[1];

                // TODO 6: Print both values
                System.out.println(username + " " + password);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}
