package com.example.niden.cellwatchsharing.database;


public class FirebaseUserEntity {

    private String id;
    private String email;
    private String name;
    private String bio;
    private String phone;
    private String hobby;
    private String expirationDate;
    private String profileUrl;
    private String userType;
    private Boolean online;



    public FirebaseUserEntity(){
    }

    @Override
    public String toString() {
        return getName()+"|"+getId();

    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FirebaseUserEntity){
            FirebaseUserEntity c = (FirebaseUserEntity ) obj;
            if(c.getName().equals(name) && c.getId()== id) return true;
        }

        return false;
    }


    public FirebaseUserEntity(String id, String email, String name, String bio, String phone, String hobby, String expirationDate, String profileUrl, String userType, Boolean online) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.bio = bio;
        this.phone = phone;
        this.hobby = hobby;
        this.expirationDate = expirationDate;
        this.profileUrl = profileUrl;
        this.userType = userType;
        this.online = online;
    }


    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getPhone() {
        return phone;
    }


    public String getHobby() {
        return hobby;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getUserType() {
        return userType;
    }

    public Boolean getOnline() {
        return online;
    }
}
