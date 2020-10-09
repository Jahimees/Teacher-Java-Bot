package main.constant;

import static main.constant.FieldConstantDB.*;

public class QueryConstant {

    public static final String INSERT_USER = "INSERT INTO USERS ("+ ID + ", " + FIRST_NAME + ", " + LAST_NAME + ") VALUES (?, ?, ?)";
}
