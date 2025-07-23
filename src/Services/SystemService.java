package Services;

import Models.Admin;
import Models.Book;
import Models.RegularUser;
import Models.User;
import org.mindrot.jbcrypt.BCrypt;
import Services.BooksService;
import Services.SearchService;

import javax.management.InstanceNotFoundException;
import java.util.*;


public class SystemService {

    static Map<String, User> users;
    static SearchService<User> userSearchService = new SearchService<>();
    static SearchService<Book> bookSearchService = new SearchService<>();
    static {
        // Initialize the users map and services
        users = new HashMap<>();
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
    public static Admin login(String id, String password) {
        Admin user = (Admin)users.get(id);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
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
            System.out.println(e.getMessage());
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
            BooksService.addBook(title, author, genre, copies);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static void removeBook(String bookId) {
        if (bookId == null || bookId.isEmpty()) {
            throw new RuntimeException("Book ID cannot be null or empty");
        }
        try {
            BooksService.removeBook(bookId);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static void updateBook(String bookId, String newTitle, String newAuthor, String newGenre, int newCopies) {
        Book existingBook = BooksService.getBookById(bookId);
        if(newTitle != null && !newTitle.isEmpty())
            existingBook.setTitle(newTitle);
        if(newAuthor != null && !newAuthor.isEmpty())
            existingBook.setAuthor(newAuthor);
        if(newGenre != null && !newGenre.isEmpty())
            existingBook.setGenre(newGenre);
        existingBook.setAvailableCopies(newCopies);
        try {
            BooksService.updateBook(existingBook);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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
            return BooksService.borrowBook(bookId, user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void returnBook(String bookId, String userId) throws Exception {
        RegularUser user = (RegularUser)userSearchService.searchById(
            userId, new ArrayList<>(users.values())
        );
        if (bookId == null || bookId.isEmpty()) {
            throw new RuntimeException("Book ID cannot be null or empty");
        }
        try {
            BooksService.returnBook(bookId, user);
        } catch (InstanceNotFoundException e) {
            Book book = bookSearchService.searchById(bookId, user.getBorrowedBooks());
            if (book == null) {
                throw new RuntimeException("Book not found in borrowed list");
            } else {
                try {
                    BooksService.addBook(book.getTitle(), book.getAuthor(), book.getGenre(), 1);
                }
                catch (Exception e1) {
                    throw new RuntimeException("Book not found in the system");
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<Book> getAllBooks() {
        return BooksService.getAvailableBooks();
    }
    public static Book getBookById(String id) {
        if (id == null || id.isEmpty()) {
            throw new RuntimeException("Book ID cannot be null or empty");
        }
        return BooksService.getBookById(id);
    }
}
