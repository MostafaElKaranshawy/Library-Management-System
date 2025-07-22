import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            JDBC.connect();
        }
        catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            return;
        }


    }
}