import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class JDBC {
    private static Connection connection;

    public static void connect() throws SQLException {
        try {

            Dotenv dotenv = Dotenv.load();
            String dbUrl = dotenv.get("db_url");
            String dbUser = dotenv.get("db_username");
            String dbPassword = dotenv.get("db_password");

            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            if (connection != null) {
                System.out.println("Connected to the database successfully!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            throw new SQLException("Connection failed: " + e.getMessage(), e);
        }
    }
    public static void close() throws SQLException {
        connection.close();
    }

    public static void createTables() {

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
