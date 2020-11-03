package main.constant;

import static main.constant.FieldConstantDB.*;

public class QueryConstant {

    public static final String INSERT_USER = "INSERT INTO USERS ("+ ID + ", " + FIRST_NAME + ", " + LAST_NAME + ") VALUES (?, ?, ?)";
    public static final String FIND_USER = "SELECT * FROM USERS WHERE " + ID + "=?;";
    public static final String UPDATE_USER = "UPDATE USERS SET " + FIRST_NAME + "=?, " + LAST_NAME + "=?, "
            + BALANCE + "=? WHERE " + ID + "=?;";
    public static final String DROP_USER = "DELETE FROM USERS WHERE " + ID + "=?;";

    public static final String FIND_USER_TASKS = "SELECT * FROM tasks where " + ID_USER + "=? AND " + DATE_END + " IS NULL;";
    public static final String FIND_FREE_TASKS = "SELECT * FROM tasks WHERE " + ID_USER + " IS NULL AND " + DATE_START + " IS NULL;";
    public static final String FIND_ALL_TASKS = "SELECT * FROM tasks";
    public static final String INSERT_TASK = "INSERT INTO tasks (" + ID + ", " + TEXT + ", " + BONUSES + ", " + DATE_CREATED + ") " +
            "VALUES (?, ?, ?, ?)";
    public static final String FIND_TASK_BY_ID = "SELECT * FROM tasks WHERE " + ID + "=?;";
    public static final String UPDATE_TASK_OWNER = "UPDATE tasks SET " + ID_USER + "=?, " + DATE_START + "=? WHERE " + ID + "=?;";
    public static final String FINISH_TASK = "UPDATE tasks SET " + DATE_END + "=? WHERE " + ID + "=?;";
    public static final String TASKS_COUNT = "SELECT count(*) as task_count FROM tasks WHERE " + ID_USER + "=? AND " + DATE_END + " IS NULL;";

    public static final String FIND_ALL_COURCES = "SELECT * FROM courses";
    public static final String ADD_NEW_COURSE = "INSERT INTO courses (" + ID + ", " + NAME + ", " + LINK + ", " +  COST + ") " +
            "VALUES (?,?,?,?)";
    public static final String FIND_COURSE_BY_ID = "SELECT * FROM courses WHERE " + ID + "=?;";

    public static final String FIND_BOUGHT_COURSES_FOR_USER = "SELECT * FROM boughtCourses WHERE " + ID_USER + "=?;";
    public static final String INSERT_BOUGHT_COURSE = "INSERT INTO boughtCourses (" + ID_USER + ", " + ID_COURSE + ") VALUES (?, ?);";
}
