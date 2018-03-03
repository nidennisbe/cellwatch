package com.example.niden.cellwatchsharing.database;

/**
 * Created by niden on 07-Feb-18.
 */

public class LocationDatabase {
    public double longitude;
    public double latitude;
    public String technicianName;
    public String address;
    public String eachUserID;

    public LocationDatabase() {

    }


    public LocationDatabase(double latitude, double longitude,String technicianName,String address,String eachUserID) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.technicianName = technicianName;
        this.address = address;
        this.eachUserID = eachUserID;
    }



    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public String getAddress() {
        return address;
    }

    public String getEachUserID() {
        return eachUserID;
    }
}
