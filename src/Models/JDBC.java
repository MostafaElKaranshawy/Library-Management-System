package Models;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class JDBC {
    public static Connection connection;

    public static void connect() throws SQLException {
        try {

            Dotenv dotenv = Dotenv.load();
            String dbUrl = dotenv.get("db_url");
            String dbUser = dotenv.get("db_username");
            String dbPassword = dotenv.get("db_password");

            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            if (connection ==  null || connection.isClosed()) {
                throw new SQLException("Failed to establish a connection to the database.");
            }
        } catch (SQLException e) {
            throw new SQLException("Connection failed: " + e.getMessage(), e);
        }
    }
    public static void close() throws SQLException {
        connection.close();
    }

    public static void createTables() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id VARCHAR(255) PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "role VARCHAR(50)" +
                ")";
        String createAdminsTable = "CREATE TABLE IF NOT EXISTS admins (" +
                "id VARCHAR(255) PRIMARY KEY, " +
                "password VARCHAR(255), " +
                "FOREIGN KEY (id) REFERENCES users(id)" +
                ")";
        String createRegularUsersTable = "CREATE TABLE IF NOT EXISTS regular_users (" +
                "id VARCHAR(255) PRIMARY KEY, " +
                "address VARCHAR(255), " +
                "phone_number VARCHAR(50), " +
                "FOREIGN KEY (id) REFERENCES users(id)" +
                ")";
        String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                "id VARCHAR(255) PRIMARY KEY, " +
                "title VARCHAR(255), " +
                "author VARCHAR(255), " +
                "genre VARCHAR(100), " +
                "available_copies INT" +
                ")";
        String createBorrowedBooksTable = "CREATE TABLE IF NOT EXISTS borrowed_books (" +
                "user_id VARCHAR(255), " +
                "book_id VARCHAR(255), " +
                "PRIMARY KEY (user_id, book_id), " +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (book_id) REFERENCES books(id)  ON DELETE CASCADE" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createUsersTable);
            statement.execute(createAdminsTable);
            statement.execute(createRegularUsersTable);
            statement.execute(createBooksTable);
            statement.execute(createBorrowedBooksTable);
        } catch (SQLException e) {
            throw new RuntimeException("Table creation failed: " + e.getMessage(), e);
        }
    }

    public static ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException("Query execution failed: " + e.getMessage(), e);
        }
    }
}
