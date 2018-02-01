package com.example.niden.cellwatchsharing.database;


public class FirebaseUserEntity {

    private String uId;
    private String email;
    private String name;
    private String bio;
    private String phone;
    private String birthday;
    private String hobby;
    private String expiration_date;
    private String profile_url;
    private int user_type;



    public FirebaseUserEntity(){
    }

    public FirebaseUserEntity(String uId, String email, String name, String bio, String phone, String birthday, String hobby, String expiration_date,String profile_url,int user_type) {
        this.uId = uId;
        this.email = email;
        this.name = name;
        this.bio = bio;
        this.phone = phone;
        this.birthday = birthday;
        this.hobby = hobby;
        this.expiration_date = expiration_date;
        this.profile_url = profile_url;
        this.user_type = user_type;

    }

    public FirebaseUserEntity(String profile_url) {
        this.profile_url = profile_url;
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
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getHobby() {
        return hobby;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setuId(String uId) {
        this.uId = uId;
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

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public int getUser_type() {
        return user_type;
    }
}
