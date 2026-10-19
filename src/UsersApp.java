
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UsersApp {

    public static void main(String[] args) {

        try {
            // TODO 1: Open users.txt
            File file = new File("users.txt");

            // TODO 2: Create the Scanner
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                // TODO 3: Split the line by whitespace
                String[] parts = /* YOUR CODE */;

                // TODO 4: Extract the username
                String username = /* YOUR CODE */;

                // TODO 5: Extract the password
                String password = /* YOUR CODE */;

                // TODO 6: Print both values

            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}
