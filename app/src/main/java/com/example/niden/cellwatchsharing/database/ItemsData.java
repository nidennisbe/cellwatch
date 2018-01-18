package com.example.niden.cellwatchsharing.database;

/**
 * Created by niden on 18-Jan-18.
 */

public class ItemsData {
    private String imageUrl;

    public ItemsData(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
