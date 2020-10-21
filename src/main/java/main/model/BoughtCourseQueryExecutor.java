package main.model;

import main.model.entity.BoughtCourse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static main.constant.FieldConstantDB.*;

public class BoughtCourseQueryExecutor implements QueryExecutor<BoughtCourse> {

    @Override
    public void executeNonQuery(String query, Map<Integer, Object> params) {
        execute(query, params, true);
        ConnectionManager.closeConnection();
    }

    @Override
    public List<BoughtCourse> executeQuery(String query, Map<Integer, Object> params) {
        List<BoughtCourse> courseList = new ArrayList<>();
        ResultSet rs = execute(query, params, false);
        try {
            while (rs.next()) {
                BoughtCourse course = new BoughtCourse();
                course.setId(rs.getInt(ID));
                course.setIdCourse(rs.getInt(ID_COURSE));
                course.setIdUser(rs.getInt(ID_USER));
                courseList.add(course);
            }
        } catch (SQLException e) {
            //LOG
        }
        ConnectionManager.closeConnection();
        return courseList;
    }
}
