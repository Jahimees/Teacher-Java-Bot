package main.constant;

import static main.constant.FieldConstantDB.*;

public class QueryConstant {

    public static final String INSERT_USER = "INSERT INTO USERS ("+ ID + ", " + FIRST_NAME + ", " + LAST_NAME + ") VALUES (?, ?, ?)";
    public static final String FIND_USER = "SELECT * FROM USERS WHERE " + ID + "=?;";
    public static final String UPDATE_USER = "UPDATE USERS SET " + FIRST_NAME + "=?, " + LAST_NAME + "=?, "
            + BALANCE + "=? WHERE " + ID + "=?;";
}
