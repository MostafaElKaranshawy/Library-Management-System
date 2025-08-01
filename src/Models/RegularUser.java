package Models;

import Interfaces.Borrowable;
import Services.SearchService;

import java.util.ArrayList;
import java.util.List;

public class RegularUser extends User {
    String address;
    String phoneNumber;
    List<Book> borrowedBooks = new ArrayList<>();

    private SearchService<Book> userBooksSearchService = new SearchService<>();

    public RegularUser() {
        super();
        borrowedBooks = new ArrayList<Book>();
        userBooksSearchService = new SearchService<Book>();
    }
    public RegularUser(String id, String name, String address, String phoneNumber) {
        super(id, name, "Regular User");
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public void borrowBook(Book book) {
        if (userBooksSearchService.searchById(book.getId(), this.borrowedBooks) != null)
            throw new RuntimeException("Book already borrowed");

        if (this.borrowedBooks.size() >= 5)
            throw new RuntimeException("Cannot borrow more than 5 books at a time");
        this.borrowedBooks.add(book);
    }

    @Override
    public void returnBook(Book book) {
        if (userBooksSearchService.searchById(book.getId(), this.borrowedBooks) != null) {
            this.borrowedBooks.removeIf(b -> b.getId().equals(book.getId()));
        }
        else
            throw new RuntimeException("Book not found in borrowed list");

    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public SearchService<Book> getUserBooksSearchService() {
        return userBooksSearchService;
    }
}
