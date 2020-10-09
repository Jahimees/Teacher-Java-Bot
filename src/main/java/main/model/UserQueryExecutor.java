package main.model;

import main.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.constant.FieldConstantDB.*;

public class UserQueryExecutor implements QueryExecutor<User> {

    @Override
    public void executeNonQuery(String query, HashMap<Integer, Object> params) {
        execute(query, params, true);
        ConnectionManager.closeConnection();
    }

    @Override
    public List<User> executeQuery(String query, HashMap<Integer, Object> params) {
        List<User> userList = new ArrayList<>();
        ResultSet rs = execute(query, params, false);
        try {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(ID));
                user.setBalance(rs.getInt(BALANCE));
                user.setFirstName(rs.getString(FIRST_NAME));
                user.setLastName(rs.getString(LAST_NAME));
                userList.add(user);
            }
        } catch (SQLException e) {
            //LOG
        }
        ConnectionManager.closeConnection();
        return userList;
    }
}