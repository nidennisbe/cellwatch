package com.example.niden.cellwatchsharing.database;

import java.util.Date;

/**
 * Created by niden on 16-Nov-17.
 */

public class TaskEntityDatabase {
    private String messageText;


    public TaskEntityDatabase(String messageText) {
        this.messageText = messageText;

    }

    public TaskEntityDatabase(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setTechnicianName(String messageText) {
        this.messageText = messageText;
    }



}
