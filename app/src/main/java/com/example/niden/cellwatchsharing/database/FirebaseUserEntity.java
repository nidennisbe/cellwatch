package com.example.niden.cellwatchsharing.database;


public class FirebaseUserEntity {

    private String uId;
    private String email;

    public FirebaseUserEntity(String user_type) {

    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String bio;
    private String contact;
    private String birthday;
    private String hobby;
    private String task;



    public FirebaseUserEntity(){
    }

    public FirebaseUserEntity(String uId, String email, String name, String bio, String contact, String birthday, String hobby,int user_type) {
        this.uId = uId;
        this.email = email;
        this.name = name;
        this.bio = bio;
        this.contact = contact;
        this.birthday = birthday;
        this.hobby = hobby;
        this.task = task;

    }

    public String getId() {
        return uId;
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
        return contact;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getHobby() {
        return hobby;
    }

    public String getTask() {
        return task;
    }

}
