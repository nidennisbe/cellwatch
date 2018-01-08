package com.example.niden.cellwatchsharing.database;

import java.util.Date;

/**
 * Created by niden on 16-Nov-17.
 */

public class TaskEntityDatabase {
    private String technicianName;


    public TaskEntityDatabase(String technicianName) {
        this.technicianName = technicianName;

    }

    public TaskEntityDatabase(){

    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }



}
