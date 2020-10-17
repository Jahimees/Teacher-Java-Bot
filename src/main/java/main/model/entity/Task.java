package main.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Task {
    private String id;
    private String text;
    private int idUser;
    private Date dateCreated;
    private Date dateStart;
    private Date dateEnd;
    private int bonuses;
}
