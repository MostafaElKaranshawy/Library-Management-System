package Services;

import Interfaces.Borrowable;
import Models.Book;
import Models.RegularUser;
import Models.User;

import javax.management.InstanceNotFoundException;
import java.util.*;

public class BooksService {

    // id, book.
    private static Map<String, Book> books;
    private static Set<String> genres;
    private static SearchService<Book> bookSearchService = new SearchService<>();

    static  {
        // Initialize the books map and genres set
        books = new HashMap<>();
        genres = new HashSet<>();

        // Add some initial books
        try {

            addBook("To Kill a Mockingbird", "Harper Lee", "Classic", 5);
            addBook("1984", "George Orwell", "Dystopian", 4);
            addBook("Pride and Prejudice", "Jane Austen", "Romance", 3);
            addBook("The Great Gatsby", "F. Scott Fitzgerald", "Classic", 2);
            addBook("The Hobbit", "J.R.R. Tolkien", "Fantasy", 6);
            addBook("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy", 10);
            addBook("A Brief History of Time", "Stephen Hawking", "Science", 4);
            addBook("The Art of War", "Sun Tzu", "Philosophy", 3);
            addBook("The Catcher in the Rye", "J.D. Salinger", "Classic", 2);
            addBook("The Alchemist", "Paulo Coelho", "Adventure", 5);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addBook(String title, String author, String genre, int copies) throws Exception {
        if (title == null || author == null || genre == null || copies <= 0) {
            throw new RuntimeException("Invalid book details");
        }
        String id;
        try {
            do {
                id = UUID.randomUUID().toString().substring(0, 8); // shorter ID (optional)
            } while (books.containsKey(id));
            Book book = new Book(id, title, author, genre, copies);
            books.put(id, book);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
    public static void removeBook(String id) {
        if (books.containsKey(id)) {
            books.remove(id);
        } else {
            throw new RuntimeException("Book not found");
        }
    }
    public static void updateBook(Book book) {
        if (books.containsKey(book.getId())) {
            books.put(book.getId(), book);
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    public static Book borrowBook(String bookId, RegularUser user) {
        List<Book> availableBooks = new ArrayList<>(books.values());
        Book book = bookSearchService.searchById(bookId, availableBooks);

        if (book != null && book.getAvailableCopies() > 0) {
            try {
                user.borrowBook(book);
                book.borrowBook();
                return book;
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

        } else {
            throw new RuntimeException("Book not available for borrowing");
        }
    }

    public static void returnBook(String bookId, RegularUser user) throws InstanceNotFoundException {
        List<Book> availableBooks = new ArrayList<>(books.values());
        Book book = bookSearchService.searchById(bookId, availableBooks);
        if (book != null) {
            try {
                user.returnBook(book);
                book.returnBook();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new InstanceNotFoundException("Book not found in borrowed list");
        }
    }

    public static List<Book> getAvailableBooks() {
        return new ArrayList<>(books.values());
    }

    public static Book getBookById(String id) {
        if (id == null || id.isEmpty()) {
            throw new RuntimeException("Book ID cannot be null or empty");
        }
        Book book = bookSearchService.searchById(id, new ArrayList<>(books.values()));
        if (book == null) {
            throw new RuntimeException("Book not found");
        }
        return book;
    }
    public static List<Book> getBooksByGenre(String genre) {
        if (genre == null || genre.isEmpty()) {
            throw new RuntimeException("Genre cannot be null or empty");
        }
        List<Book> booksByGenre = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                booksByGenre.add(book);
            }
        }
        return booksByGenre;
    }
    public static List<Book> getBooksByAuthor(String author) {
        if (author == null || author.isEmpty()) {
            throw new RuntimeException("Author cannot be null or empty");
        }
        List<Book> booksByAuthor = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                booksByAuthor.add(book);
            }
        }
        return booksByAuthor;
    }
    public static Set<String> getGenres() {
        return genres;
    }

}
