package Models;

public class Admin extends User {
    public boolean addBook(String title, String author, String genre, int availableCopies) {

        return true;
    }

    public boolean deleteBook(String title, String author, String genre, int availableCopies) {
        return true;
    }

    public boolean updateBook(String title, String author, String genre, int availableCopies) {
        return true;
    }

    public boolean registerUser(String name, String email, String password, String address, String phoneNumber) {
        try {
            RegularUser user = new RegularUser(name, email, password, address, phoneNumber);
            // Assuming there's a method to save the user to the database
            // saveUserToDatabase(user);
            return true;
        } catch (Exception e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }
}
