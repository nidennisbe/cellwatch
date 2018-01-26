package com.example.niden.cellwatchsharing.database;

/**
 * Created by niden on 18-Jan-18.
 */

public class ProfilePhotoEntityDatabase {
    private String profile_url;

    public ProfilePhotoEntityDatabase(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }
}
