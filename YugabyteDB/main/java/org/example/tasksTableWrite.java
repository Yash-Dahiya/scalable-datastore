package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class tasksTableWrite {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5434/yugabyte?user=yugabyte";
//        String username = "yugabyte";
//        String password = "doracake$123";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String createTableQuery = "CREATE TABLE tasks ("
                    + "id serial PRIMARY KEY,"
                    + "userid integer REFERENCES users(id),"
                    + "name character varying,"
                    + "description character varying,"
                    + "status statusenum,"
                    + "tags character varying[]"
                    + ");";

            String createEnumQuery = "CREATE TYPE statusenum AS ENUM ('OPEN', 'IN_PROGRESS', 'CLOSED');";

            stmt.executeUpdate(createEnumQuery);
            stmt.executeUpdate(createTableQuery);

            System.out.println("Tasks table created successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to create tasks table.");
            e.printStackTrace();
        }
    }
}



