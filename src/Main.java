import java.sql.*;

public class DatabaseConnection {
    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/mydatabase";
            String username = "root";
            String password = "mypassword";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Execute a query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM mytable");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("column1") + " " + resultSet.getString("column2"));
            }

            // Close the connection
            connection.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}