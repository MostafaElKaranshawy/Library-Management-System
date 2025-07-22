package Models;

import Interfaces.Borrowable;
import Services.SearchService;

import java.util.ArrayList;
import java.util.List;

public class RegularUser extends User {
    String address;
    String phoneNumber;
    List<Book> borrowedBooks;

    private SearchService<Book> userBooksSearchService;

    public RegularUser() {
        super();
        borrowedBooks = new ArrayList<Book>();
    }
    public RegularUser(String name, String email, String password, String address, String phoneNumber) {
        super(name, email, password);
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void borrowBook(Book book) {
        if (userBooksSearchService.searchById(book.getId(), borrowedBooks) != null)
            throw new RuntimeException("Book already borrowed");

        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        if (userBooksSearchService.searchById(book.getId(), borrowedBooks) != null)
            borrowedBooks.remove(book);
        else
            throw new RuntimeException("Book not found in borrowed list");

    }
}
