package com.example.niden.cellwatchsharing.database;

/**
 * Created by niden on 18-Jan-18.
 */

public class ProfilePhotoEntityDatabase {
    private String profileUrl;

    public ProfilePhotoEntityDatabase(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
