package com.example.niden.cellwatchsharing.database;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by niden on 16-Nov-17.
 */

public class TaskEntityDatabase implements Serializable{
    private String task_name;
    private String task_description;
    private String task_address;
    private String task_suburb;
    private String task_class;
    private String task_date;
    private String task_type;
    private String task_technician_name;

    public TaskEntityDatabase(){

    }


    TaskEntityDatabase(String task_name, String task_class, String task_description, String task_address, String task_suburb,String task_date,String task_type, String task_technician_name) {
        this.task_name = task_name;
        this.task_description = task_description;
        this.task_address = task_address;
        this.task_suburb = task_suburb;
        this.task_class = task_class;
        this.task_date = task_date;
        this.task_type = task_type;
        this.task_technician_name = task_technician_name;
    }




    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }

    public String getTask_address() {
        return task_address;
    }

    public void setTask_address(String task_address) {
        this.task_address = task_address;
    }

    public String getTask_suburb() {
        return task_suburb;
    }

    public void setTask_suburb(String task_suburb) {
        this.task_suburb = task_suburb;
    }

    public String getTask_class() {
        return task_class;
    }

    public void setTask_class(String task_class) {
        this.task_class = task_class;
    }

    public String getTask_date() {
        return task_date;
    }

    public void setTask_date(String task_date) {
        this.task_date = task_date;
    }

    public String getTask_type() {
        return task_type;
    }

    public String getTask_technician_name() {
        return task_technician_name;
    }
}