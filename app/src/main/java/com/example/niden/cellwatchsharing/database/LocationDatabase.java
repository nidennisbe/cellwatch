package com.example.niden.cellwatchsharing.database;

/**
 * Created by niden on 07-Feb-18.
 */

public class LocationDatabase {
    private double longitude;
    private double latitude;
    private String address;
    private String eachUserID;
    private String timeStamp;

    public LocationDatabase() {

    }


    public LocationDatabase(double latitude, double longitude,String address,String eachUserID,String timeStamp) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.eachUserID = eachUserID;
        this.timeStamp = timeStamp;
    }



    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getEachUserID() {
        return eachUserID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
