package org.example;
import java.sql.*;

public class usersTableWrite     {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5435/yugabyte?user=yugabyte&password=yugabyte";

        try (Connection connection = DriverManager.getConnection(url)) {
            // Create users table if it doesn't exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY, name VARCHAR(255), email VARCHAR(255))";
            try (Statement statement = connection.createStatement()) {
                statement.execute(createTableQuery);
            }

            // Insert user data
            insertUserData(connection, 1, "John Doe", "john.doe@example.com");
            insertUserData(connection, 2, "Jane Smith", "jane.smith@example.com");
            insertUserData(connection, 3, "David Johnson", "david.johnson@example.com");

            System.out.println("Data inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertUserData(Connection connection, int id, String name, String email) throws SQLException {
        String insertQuery = "INSERT INTO users (id, name, email) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
        }
    }
}