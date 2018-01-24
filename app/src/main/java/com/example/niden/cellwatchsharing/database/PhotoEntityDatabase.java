package com.example.niden.cellwatchsharing.database;

/**
 * Created by niden on 18-Jan-18.
 */

public class PhotoEntityDatabase {
    private String imageUrl;

    public PhotoEntityDatabase(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
