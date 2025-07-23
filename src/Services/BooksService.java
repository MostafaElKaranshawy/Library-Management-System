package Services;

import Interfaces.Borrowable;
import Models.Book;
import Models.RegularUser;
import Models.User;

import javax.management.InstanceNotFoundException;
import java.io.File;
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

        if(ConfigService.useFileStorage())
            fetchBooksFromFile();
        else
            fetchBooks();
    }
    private static void fetchBooksFromFile() {
        try {
            List<Book> initialBooks = FileService.getAllBooks();
            for (Book book : initialBooks) {
                books.put(book.getId(), book);
                genres.add(book.getGenre());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    private static void fetchBooks() {
        try {
            List<Book> initialBooks = DBService.getAllBooks();
            for (Book book : initialBooks) {
                books.put(book.getId(), book);
                genres.add(book.getGenre());
            }
        } catch (Exception e) {
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
            if(ConfigService.useFileStorage())
                FileService.addBook(book);
            else
                DBService.addBook(book);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
    public static void removeBook(String id) {
        if (books.containsKey(id)) {
            books.remove(id);
            try {
                if(ConfigService.useFileStorage())
                    FileService.deleteBook(id);
                else
                    DBService.deleteBook(id);
            } catch (Exception e) {
                throw new RuntimeException("Error removing book from database: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("Book not found");
        }
    }
    public static void updateBook(Book book) {
        if (books.containsKey(book.getId())) {
            books.put(book.getId(), book);
            try {
                if(ConfigService.useFileStorage())
                    FileService.updateBook(book);
                else
                    DBService.updateBook(book);
            } catch (Exception e) {
                throw new RuntimeException("Error updating book in database: " + e.getMessage());
            }
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
                if(ConfigService.useFileStorage())
                    FileService.borrowBook(user.getId(), bookId);
                else
                    DBService.borrowBook(user.getId(), bookId);
                return book;
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

        } else {
            throw new RuntimeException("Book not available for borrowing");
        }
    }

    public static void returnBook(String bookId, RegularUser user) {
        List<Book> availableBooks = new ArrayList<>(books.values());
        Book book = bookSearchService.searchById(bookId, availableBooks);
        if (book != null) {
            try {
                user.returnBook(book);
                book.returnBook();
                if (ConfigService.useFileStorage()) {
                    FileService.returnBook(user.getId(), bookId);
                    FileService.updateBook(book);
                }
                else {
                    DBService.returnBook(user.getId(), bookId);
                    DBService.updateBook(book);
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            Book newBook = bookSearchService.searchById(bookId, user.getBorrowedBooks());
            if (newBook == null) {
                throw new RuntimeException("Book not found in borrowed list");
            } else {
                try {
                    BooksService.addBook(newBook.getTitle(), newBook.getAuthor(), newBook.getGenre(), 1);
                    if(ConfigService.useFileStorage()) {
                        FileService.returnBook(user.getId(), bookId);
                        FileService.addBook(newBook);
                    }
                    else{
                        DBService.returnBook(user.getId(), bookId);
                        DBService.addBook(newBook);
                    }
                }
                catch (Exception e1) {
                    throw new RuntimeException("Book not found in the system");
                }
            }
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
