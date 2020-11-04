package main.model.entity;

import lombok.Data;

@Data
public class BoughtCourse extends Entity {
    private String idCourse;
    private int idUser;
}
