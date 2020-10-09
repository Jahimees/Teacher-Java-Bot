package main.model.entity;

import lombok.Data;

@Data
public class Task extends Entity {
    private String text;
    private int idUser;
}
