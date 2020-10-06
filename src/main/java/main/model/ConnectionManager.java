package main.model;

import java.sql.*;

public final class ConnectionManager {

    private static Connection connection;

    private ConnectionManager() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.h2.Driver").newInstance();
                connection = DriverManager.getConnection("jdbc:h2:file:./data/demo", "sa", "password");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }

    public static ResultSet executeSimpleQuery(String query) {
        Connection connection = getConnection();
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
             resultSet = statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }
}
