package com.example.niden.cellwatchsharing.database;

/**
 * Created by niden on 25-Nov-17.
 */

public class OnlineUserEntityDatabase {
    private String userStatus;
    private String userEmail;

    public OnlineUserEntityDatabase(String userStatus, String userEmail) {
        this.userStatus = userStatus;
        this.userEmail = userEmail;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
