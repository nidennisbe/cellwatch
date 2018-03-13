package com.example.niden.cellwatchsharing.database;

import java.io.Serializable;

/**
 * Created by niden on 16-Nov-17.
 */

public class TaskEntityDatabase implements Serializable{
    private String taskName;
    private String taskDescription;
    private String taskComment;
    private String taskAddress;
    private String taskSuburb;
    private String taskClass;
    private String taskDate;
    private String taskType;
    private String taskTechnicianName;
    private String taskStartDate;
    private String taskEndDate;
    private String taskStatus;

    public String getEachUserID() {
        return eachUserID;
    }

    private String eachUserID;

    public TaskEntityDatabase(){

    }


    public TaskEntityDatabase(String eachUserID, String taskName, String taskClass, String taskDescription, String taskAddress, String taskSuburb, String taskDate, String taskType, String taskTechnicianName,String taskComment,
                              String taskStartDate,String taskEndDate,String taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskAddress = taskAddress;
        this.taskSuburb = taskSuburb;
        this.taskClass = taskClass;
        this.taskDate = taskDate;
        this.taskType = taskType;
        this.taskTechnicianName = taskTechnicianName;
        this.eachUserID = eachUserID;
        this.taskComment = taskComment;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.taskStatus= taskStatus;
    }




    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskAddress() {
        return taskAddress;
    }

    public void setTaskAddress(String taskAddress) {
        this.taskAddress = taskAddress;
    }

    public String getTaskSuburb() {
        return taskSuburb;
    }

    public void setTaskSuburb(String taskSuburb) {
        this.taskSuburb = taskSuburb;
    }

    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getTaskTechnicianName() {
        return taskTechnicianName;
    }

    public String getTaskComment() {
        return taskComment;
    }

    public String getTaskStartDate() {
        return taskStartDate;
    }

    public String getTaskEndDate() {
        return taskEndDate;
    }

    public String getTaskStatus() {
        return taskStatus;
    }
}