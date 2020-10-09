package main.model;

import main.model.entity.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static main.constant.FieldConstantDB.*;

public class CourseQueryExecutor implements QueryExecutor<Course> {

    @Override
    public void executeNonQuery(String query, HashMap<Integer, Object> params) {
        execute(query, params, true);
        ConnectionManager.closeConnection();
    }

    @Override
    public List<Course> executeQuery(String query, HashMap<Integer, Object> params) {
        List<Course> courseList = new ArrayList<>();
        ResultSet rs = execute(query, params, false);
        try {
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt(ID));
                course.setLink(rs.getString(LINK));
                course.setCost(rs.getInt(COST));
                course.setName(rs.getString(NAME));
                courseList.add(course);
            }
        } catch (SQLException e) {
            //LOG
        }
        ConnectionManager.closeConnection();
        return courseList;
    }
}
