package org.example;

import java.sql.*;

public class CheckTableExists {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5434/yugabyte?user=yugabyte";

        try (Connection connection = DriverManager.getConnection(url)) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "users", null);

            if (tables.next()) {
                System.out.println("Table 'users' exists.");
            } else {
                System.out.println("Table 'users' does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

