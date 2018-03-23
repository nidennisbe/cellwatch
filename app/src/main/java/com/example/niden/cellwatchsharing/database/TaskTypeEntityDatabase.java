package com.example.niden.cellwatchsharing.database;

import java.io.Serializable;

/**
 * Created by niden on 18-Jan-18.
 */

public class TaskTypeEntityDatabase implements Serializable {
    private String type;

    public TaskTypeEntityDatabase(){

    }

    public TaskTypeEntityDatabase(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
