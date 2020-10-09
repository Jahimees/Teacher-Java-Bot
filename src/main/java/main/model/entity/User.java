package main.model.entity;

import lombok.Data;

@Data
public class User extends Entity {
    private String firstName;
    private String lastName;
    private int balance;

}
