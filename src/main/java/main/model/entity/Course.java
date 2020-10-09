package main.model.entity;

import lombok.Data;

@Data
public class Course extends Entity {
    private String link;
    private String name;
    private int cost;
}

