package main.model;

import main.model.entity.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.constant.FieldConstantDB.*;

public class TaskQueryExecutor implements QueryExecutor<Task> {

    @Override
    public void executeNonQuery(String query, HashMap<Integer, Object> params) {
        execute(query, params, true);
        ConnectionManager.closeConnection();
    }

    @Override
    public List<Task> executeQuery(String query, HashMap<Integer, Object> params) {
        List<Task> taskList = new ArrayList<>();
        ResultSet rs = execute(query, params, false);
        try {
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getString(ID));
                task.setIdUser(rs.getInt(ID_USER));
                task.setDateCreated(rs.getDate(DATE_CREATED));
                task.setDateEnd(rs.getDate(DATE_END));
                task.setDateStart(rs.getDate(DATE_START));
                task.setBonuses(rs.getInt(BONUSES));
                task.setText(rs.getString(TEXT));
                taskList.add(task);
            }
        } catch (SQLException e) {
            //LOG
        }
        ConnectionManager.closeConnection();
        return taskList;
    }
}
