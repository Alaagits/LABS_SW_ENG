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

                String[] parts = line.split("\\s+");

                String username = parts[0];
                String password = parts[1];


                User user = new User(username, password);

                //testing
                if (user.isUsernameLengthValid()) {
                    System.out.println("Username length is valid");
                } else {
                    System.out.println("Username is too long");
                }




                System.out.println(user.getUsername() + " " + user.getPassword());
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }


}


// This is a SECOND class, in the same file.
class User {

    private String username;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;

    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }



    public boolean isUsernameLengthValid(){
        int length = username.length();
        if(length<=50){
            return true;

        }
        else {
            return false;
        }

    }


}
