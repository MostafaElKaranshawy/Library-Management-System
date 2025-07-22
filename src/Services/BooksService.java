package Services;

import Interfaces.Borrowable;
import Models.Book;
import Models.RegularUser;
import Models.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BooksService {

    // title, book.
    private Map<String, Book> books;
    private Set<String> genres;
    private List<Book> availableBooks;
    private SearchService<Book> bookSearchService;

    public BooksService(Map<String, Book> books, Map<String, User> users, Set<String> genres,
                        List<Book> availableBooks, SearchService<Book> bookSearchService) {
        this.books = books;
        this.genres = genres;
        this.availableBooks = availableBooks;
        this.bookSearchService = bookSearchService;
    }

    public void addBook(Book book) {
        books.put(book.getTitle(), book);
    }
    public void removeBook(String title) {
        books.remove(title);
    }

    public void borrowBook(int bookId, RegularUser user) {
        Book book = bookSearchService.searchById(bookId, availableBooks);
        if (book != null && book.getAvailableCopies() > 0) {
            try {
                user.borrowBook(book);
                book.borrowBook();
            }
            catch (Exception e) {
                throw new RuntimeException("Error borrowing book: " + e.getMessage());
            }

        } else {
            throw new RuntimeException("Book not available for borrowing");
        }
    }

    public void returnBook(int bookId, RegularUser user) {
        Book book = bookSearchService.searchById(bookId, availableBooks);
        if (book != null) {
            try {
                user.returnBook(book);
                book.returnBook();
            } catch (Exception e) {
                throw new RuntimeException("Error returning book: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("Book not found in borrowed list");
        }
    }

    public List<Book> getAvailableBooks() {
        return availableBooks;
    }
}
