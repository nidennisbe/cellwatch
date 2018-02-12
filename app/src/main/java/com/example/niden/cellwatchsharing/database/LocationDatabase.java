package com.example.niden.cellwatchsharing.database;

/**
 * Created by niden on 07-Feb-18.
 */

public class LocationDatabase {
    public double longitude;
    public double latitude;

public  LocationDatabase(){

}

    public LocationDatabase(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
