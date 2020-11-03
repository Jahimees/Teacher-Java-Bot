package main.model;

import main.model.entity.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static main.constant.FieldConstantDB.*;

public class CourseQueryExecutor implements QueryExecutor<Course> {

    @Override
    public void executeNonQuery(String query, Map<Integer, Object> params) {
        execute(query, params, true);
        ConnectionManager.closeConnection();
    }

    @Override
    public List<Course> executeQuery(String query, Map<Integer, Object> params) {
        List<Course> courseList = new ArrayList<>();
        ResultSet rs = execute(query, params, false);
        try {
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getString(ID));
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
