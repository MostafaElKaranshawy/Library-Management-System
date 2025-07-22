package Interfaces;

import Models.Book;

public interface Borrowable {
    public void borrowBook(Book book);
    public void returnBook(Book book);
}
