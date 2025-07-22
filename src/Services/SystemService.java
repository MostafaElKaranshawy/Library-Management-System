package Services;

import Models.Admin;
import Models.Book;
import Models.RegularUser;
import Models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.*;


public class SystemService {
    // name, user.
    static Map<String, User> users;
    static private SearchService<User> userSearchService;
    static private BooksService booksService;

    static {
        // Initialize the users map and services
        users = new HashMap<>();
        booksService = new BooksService();
        userSearchService = new SearchService<>();
        String adminPass1 = BCrypt.hashpw("admin123", BCrypt.gensalt());
        String adminPass2 = BCrypt.hashpw("secure456", BCrypt.gensalt());

        Admin admin1 = new Admin("admin001", "Alice", adminPass1);
        Admin admin2 = new Admin("admin002", "Bob", adminPass2);

        users.put(admin1.getId(), admin1);
        users.put(admin2.getId(), admin2);
        
        RegularUser user1 = new RegularUser("user001", "Charlie", "123 Elm Street", "01012345678");
        RegularUser user2 = new RegularUser("user002", "Diana", "456 Oak Avenue", "01087654321");
        RegularUser user3 = new RegularUser("user003", "Ethan", "789 Pine Road", "01099887766");

        users.put(user1.getId(), user1);
        users.put(user2.getId(), user2);
        users.put(user3.getId(), user3);
    }
    public static void login(String id, String password) {
        Admin user = (Admin)users.get(id);

        System.out.println("Attempting login for user: " + id);
        System.out.println("Password provided: " + password);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("Login successful for user: " + user.getName());
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }
    public static void registerNewUser(String name, String address, String phoneNumber) {
        String id;
        try {
            do {
                id = UUID.randomUUID().toString().substring(0, 8); // shorter ID (optional)
            } while (users.containsKey(id));

            RegularUser newUser = new RegularUser(id, name, address, phoneNumber);
            users.put(id, newUser);
        }
        catch (Exception e) {
            System.out.println("Error while registering new user: " + e.getMessage());
        }
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    public static User getUserByName(String name) throws Exception {
        User user = userSearchService.searchByName(name, getAllUsers());
        if(user == null)
            throw new Exception("User not found with name: " + name);
        return user;
    }

    // Admin book management methods
    public static void addBook(String title, String author, String genre, int copies) {
        try {
            booksService.addBook(title, author, genre, copies);
        }
        catch (Exception e) {
            throw new RuntimeException("Error while adding book: " + e.getMessage());
        }
    }
    public static void removeBook(String bookId) {
        if (bookId == null || bookId.isEmpty()) {
            throw new RuntimeException("Book ID cannot be null or empty");
        }
        try {
            booksService.removeBook(bookId);
        }
        catch (Exception e) {
            throw new RuntimeException("Error while removing book: " + e.getMessage());
        }
    }
    public static void updateBook(String bookId, String newTitle, String newAuthor, String newGenre, int newCopies) {

        Book book = new Book(bookId, newTitle, newAuthor, newGenre, newCopies);
        try {
            booksService.updateBook(book);
        }
        catch (Exception e) {
            throw new RuntimeException("Error while updating book: " + e.getMessage());
        }
    }
    public static Book borrowBook(String bookId, String userId) {
        RegularUser user = (RegularUser)userSearchService.searchById(
                userId, new ArrayList<>(users.values())
        );
        if (bookId == null || bookId.isEmpty()) {
            throw new RuntimeException("Book ID cannot be null or empty");
        }
        try {
            return booksService.borrowBook(bookId, user);
        } catch (Exception e) {
            throw new RuntimeException("Error while borrowing book: " + e.getMessage());
        }
    }

    public static void returnBook(String bookId, String userId) {
        RegularUser user = (RegularUser)userSearchService.searchById(
            userId, new ArrayList<>(users.values())
        );
        if (bookId == null || bookId.isEmpty()) {
            throw new RuntimeException("Book ID cannot be null or empty");
        }
        try {
            booksService.returnBook(bookId, user);
        } catch (Exception e) {
            throw new RuntimeException("Error while returning book: " + e.getMessage());
        }
    }

    public static List<Book> getAllBooks() {
        return booksService.getAvailableBooks();
    }
}
