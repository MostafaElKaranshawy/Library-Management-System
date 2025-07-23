package Services;

import Models.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBService {
    static  {
        try {
            JDBC.connect();
            JDBC.createTables();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM users INNER JOIN admins ON users.id = admins.id";
        try {
            var resultSet = JDBC.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");
                String password = resultSet.getString("password");
                Admin admin = new Admin(id, name, password);
                admins.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }
    public static List<RegularUser> getAllRegularUsers() {
        List<RegularUser> regularUsers = new ArrayList<>();
        String query = "SELECT * FROM users INNER JOIN regular_users ON users.id = regular_users.id";
        try {
            var resultSet = JDBC.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phone_number");
                RegularUser user = new RegularUser(id, name, address, phoneNumber);
                regularUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regularUsers;
    }

    public static void addUser(User user) {
        String query = "INSERT INTO users (id, name, role) VALUES (?, ?, ?)";
        try {
            var preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Book> getAllBooks() {
        String query = "SELECT * FROM books";
        try {
            var resultSet = JDBC.executeQuery(query);
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                int availableCopies = resultSet.getInt("available_copies");
                Book book = new Book(id, title, author, genre, availableCopies);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public static void addBook(Book book) {
        String query = "INSERT INTO books (id, title, author, genre, available_copies) VALUES (?, ?, ?, ?, ?)";
        try {
            var preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, book.getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getGenre());
            preparedStatement.setInt(5, book.getAvailableCopies());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateBook(Book book) {
        String query = "UPDATE books SET title = ?, author = ?, genre = ?, available_copies = ? WHERE id = ?";
        try {
            var preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.setInt(4, book.getAvailableCopies());
            preparedStatement.setString(5, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteBook(String bookId) {
        String query = "DELETE FROM books WHERE id = ?";
        try {
            var preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void borrowBook(String userId, String bookId) {
        String query = "INSERT INTO borrowed_books (user_id, book_id) VALUES (?, ?)";
        try {
            var preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String updateQuery = "UPDATE books SET available_copies = available_copies - 1 WHERE id = ?";
        try {
            var preparedStatement = JDBC.connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void returnBook(String userId, String bookId) {
        String query = "DELETE FROM borrowed_books WHERE user_id = ? AND book_id = ?";
        try {
            var preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Book> getBorrowedBooks(String userId) {
        String query = "SELECT books.* FROM borrowed_books INNER JOIN books ON borrowed_books.book_id = books.id WHERE borrowed_books.user_id = ?";
        List<Book> borrowedBooks = new ArrayList<>();
        try {
            var preparedStatement = JDBC.connection.prepareStatement(query);
            preparedStatement.setString(1, userId);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                int availableCopies = resultSet.getInt("available_copies");
                Book book = new Book(id, title, author, genre, availableCopies);
                borrowedBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowedBooks;
    }
    public static void close() {
        try {
            JDBC.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
