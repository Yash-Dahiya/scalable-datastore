package org.example;
import java.sql.*;

public class usersTableRead {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5434/yugabyte?user=yugabyte&password=yugabyte";

        try (Connection connection = DriverManager.getConnection(url)) {
            // Read user data
            readUserData(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void readUserData(Connection connection) throws SQLException {
        String selectQuery = "SELECT * FROM users";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println();
            }

            resultSet.close();
        }
    }
}
