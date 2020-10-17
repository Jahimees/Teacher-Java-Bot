package main.constant;

import static main.constant.FieldConstantDB.*;

public class QueryConstant {

    public static final String INSERT_USER = "INSERT INTO USERS ("+ ID + ", " + FIRST_NAME + ", " + LAST_NAME + ") VALUES (?, ?, ?)";
    public static final String FIND_USER = "SELECT * FROM USERS WHERE " + ID + "=?;";
    public static final String UPDATE_USER = "UPDATE USERS SET " + FIRST_NAME + "=?, " + LAST_NAME + "=?, "
            + BALANCE + "=? WHERE " + ID + "=?;";


    public static final String FIND_USER_TASKS = "SELECT * FROM tasks where " + ID_USER + "=?;";
    public static final String FIND_FREE_TASKS = "SELECT * FROM tasks WHERE " + ID_USER + " IS NULL;";
    public static final String FIND_FREE_TASKS2 = "SELECT * FROM tasks";
    public static final String INSERT_TASK = "INSERT INTO tasks (" + ID + ", " + TEXT + ", " + BONUSES + ", " + DATE_CREATED + ") " +
            "VALUES (?, ?, ?, ?)";
}
