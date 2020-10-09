package main.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

    private static Connection connection;

    private ConnectionManager() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    Class.forName("org.h2.Driver").newInstance();
                    connection = DriverManager.getConnection("jdbc:h2:file:./data/demo", "sa", "password");
                } catch (InstantiationException | SQLException | ClassNotFoundException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
