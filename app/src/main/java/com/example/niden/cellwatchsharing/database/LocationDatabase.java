package com.example.niden.cellwatchsharing.database;

/**
 * Created by niden on 07-Feb-18.
 */

public class LocationDatabase {
    public double longitude;
    public double latitude;
    public String technicianName;
    public String address;

public  LocationDatabase(){

}

    public LocationDatabase(double latitude, double longitude,String technicianName,String address) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.technicianName = technicianName;
        this.address = address;
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
}
