package main.model.entity;

import lombok.Data;

@Data
public class BoughtCourse extends Entity {
    private int idCourse;
    private int idUser;
}
