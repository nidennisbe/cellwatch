package com.example.niden.cellwatchsharing.database;

public class UserProfile {

    private String header;

    private String profileContent;

    public UserProfile(String header, String profileContent) {
        this.header = header;
        this.profileContent = profileContent;
    }

    public String getHeader() {
        return header;
    }

    public String getProfileContent() {
        return profileContent;
    }
}
