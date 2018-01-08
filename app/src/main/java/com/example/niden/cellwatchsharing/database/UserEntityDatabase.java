package com.example.niden.cellwatchsharing.database;

/**
 * Created by niden on 18-Nov-17.
 */

public class UserEntityDatabase {
    private String userName;
    private String userBio;
    private String userProfile;
    private String userContact;
    private String userEmail;
    private String userStatus;

    public UserEntityDatabase(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public UserEntityDatabase(String userName, String userBio, String userProfile, String userContact, String userEmail) {
        this.userName = userName;
        this.userBio = userBio;
        this.userProfile = userProfile;
        this.userContact = userContact;
        this.userEmail = userEmail;
    }

    public String getUserStatus() {
        return userStatus;
    }
}
