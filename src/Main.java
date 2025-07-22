import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        JDBC.connect();

        String query = "SELECT * FROM HELLO";
        try {
            var resultSet = JDBC.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") );
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        } finally {
            JDBC.close();
        }
    }
}