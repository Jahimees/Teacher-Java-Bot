package main.model;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface QueryExecutor<T> {

    default ResultSet execute(String query, HashMap<Integer, Object> params, boolean isUpdate) {
        Connection connection = ConnectionManager.getConnection();
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<Integer, Object> param : params.entrySet()) {
                    statement.setObject(param.getKey(), param.getValue());
                }
            }
            if (isUpdate) {
                statement.executeUpdate();
            } else {
                resultSet = statement.executeQuery();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    void executeNonQuery(String query, HashMap<Integer, Object> params);

    List<T> executeQuery(String query, HashMap<Integer, Object> params);
}
