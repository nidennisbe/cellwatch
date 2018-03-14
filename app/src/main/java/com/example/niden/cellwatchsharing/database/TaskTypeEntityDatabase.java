package com.example.niden.cellwatchsharing.database;

/**
 * Created by niden on 18-Jan-18.
 */

public class TaskTypeEntityDatabase {
    private String type;

    public TaskTypeEntityDatabase(){

    }

    public TaskTypeEntityDatabase(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
