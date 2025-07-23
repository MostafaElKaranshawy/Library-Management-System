package Services;

import Models.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileService<T> {
    private static final String USERS_FILE = Dotenv.load().get("FILES_PATH") + "/users.dat";
    private static final String BOOKS_FILE = Dotenv.load().get("FILES_PATH") + "/books.dat";

    public static <T> List<T> readList(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static <T> void writeList(List<T> list, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Admin> getAllAdmins() {
        return readList(USERS_FILE).stream()
                .filter(u -> u instanceof Admin)
                .map(u -> (Admin) u)
                .collect(Collectors.toList());
    }

    public static List<RegularUser> getAllRegularUsers() {
        return readList(USERS_FILE).stream()
                .filter(u -> u instanceof RegularUser)
                .map(u -> (RegularUser) u)
                .collect(Collectors.toList());
    }

    public static void addUser(User user) {
        List<User> users = readList(USERS_FILE);
        users.add(user);
        writeList(users, USERS_FILE);
    }

    public static List<Book> getAllBooks() {
        return readList(BOOKS_FILE);
    }

    public static void addBook(Book book) {
        List<Book> books = readList(BOOKS_FILE);
        books.add(book);
        writeList(books, BOOKS_FILE);
    }

    public static void updateBook(Book updatedBook) {
        List<Book> books = readList(BOOKS_FILE);
        books = books.stream()
                .map(book -> book.getId().equals(updatedBook.getId()) ? updatedBook : book)
                .collect(Collectors.toList());
        writeList(books, BOOKS_FILE);
    }

    public static void deleteBook(String bookId) {
        List<Book> books = readList(BOOKS_FILE);
        books.removeIf(book -> book.getId().equals(bookId));
        writeList(books, BOOKS_FILE);
    }

    public static void borrowBook(String userId, String bookId) {
        List<Book> books = readList(BOOKS_FILE);
        List<User> users = readList(USERS_FILE);

        Book targetBook = null;
        for (Book book : books) {
            if (book.getId().equals(bookId) && book.getAvailableCopies() > 0) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
                targetBook = book;
                break;
            }
        }

        if (targetBook != null) {
            for (User user : users) {
                if (user instanceof RegularUser && user.getId().equals(userId)) {
                    ((RegularUser) user).getBorrowedBooks().add(targetBook);
                    break;
                }
            }
        }

        writeList(books, BOOKS_FILE);
        writeList(users, USERS_FILE);
    }

    public static void returnBook(String userId, String bookId) {
        List<Book> books = readList(BOOKS_FILE);
        List<User> users = readList(USERS_FILE);

        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                book.setAvailableCopies(book.getAvailableCopies() + 1);
                break;
            }
        }

        for (User user : users) {
            if (user instanceof RegularUser && user.getId().equals(userId)) {
                List<Book> borrowed = ((RegularUser) user).getBorrowedBooks();
                borrowed.removeIf(book -> book.getId().equals(bookId));
                break;
            }
        }

        writeList(books, BOOKS_FILE);
        writeList(users, USERS_FILE);
    }

    public static List<Book> getBorrowedBooks(String userId) {
        List<User> users = readList(USERS_FILE);
        for (User user : users) {
            if (user instanceof RegularUser && user.getId().equals(userId)) {
                return ((RegularUser) user).getBorrowedBooks();
            }
        }
        return new ArrayList<>();
    }

    public static void close() {
        // Nothing to close for file-based DB
    }
}
