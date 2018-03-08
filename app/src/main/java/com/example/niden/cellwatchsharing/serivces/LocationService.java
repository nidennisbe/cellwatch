package com.example.niden.cellwatchsharing.serivces;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by niden on 16-Feb-18.
 */


public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private static final String LOGSERVICE = "#######";
    private static final long MIN_TIME_BW_UPDATES = 60000;
    public DatabaseReference mDatabaseLocationDetails;
    Context context;
    double longitude, latitude;

    @Override
    public void onCreate() {
        super.onCreate();
        buildGoogleApiClient();
        Log.i(LOGSERVICE, "onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOGSERVICE, "onStartCommand");

        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
        return START_STICKY;
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.i(LOGSERVICE, "onConnected" + bundle);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (l != null) {
         /*   longitude = l.getLongitude();
            latitude = l.getLatitude();
            Log.i(LOGSERVICE, "lat " + latitude);
            Log.i(LOGSERVICE, "lng " + longitude);
            try {
                storeInDatabase(latitude, longitude);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOGSERVICE, "onConnectionSuspended " + i);

    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        Log.i(LOGSERVICE, "lat " + latitude);
        Log.i(LOGSERVICE, "lng " + latitude);
        LatLng mLocation = (new LatLng(latitude, longitude));
        //EventBus.getDefault().post(mLocation);
        try {
            storeInDatabase(latitude, longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOGSERVICE, "onDestroy");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void initLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(MIN_TIME_BW_UPDATES);
        mLocationRequest.setFastestInterval(MIN_TIME_BW_UPDATES);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    private void startLocationUpdate() {
        initLocationRequest();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(LOGSERVICE, "onConnectionFailed ");
    }

    private void storeInDatabase(double latitude, double longitude) throws IOException {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StringTokenizer tokens = new StringTokenizer(email, "@");
        String technicianName = tokens.nextToken();
        String currentDateTimeString = String.valueOf(System.currentTimeMillis());
        Uri photoUrl = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
        
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        addresses = geocoder.getFromLocation(latitude, longitude, 1);

        String address = addresses.get(0).getAddressLine(0);
        mDatabaseLocationDetails = FirebaseDatabase.getInstance().getReference().child("location").push();

        mDatabaseLocationDetails.child("longitude").setValue(longitude);
        mDatabaseLocationDetails.child("latitude").setValue(latitude);
        mDatabaseLocationDetails.child("eachUserID").setValue(uID);
        mDatabaseLocationDetails.child("technicianName").setValue(technicianName);
        mDatabaseLocationDetails.child("address").setValue(address);
        mDatabaseLocationDetails.child("timeStamp").setValue(currentDateTimeString);
        mDatabaseLocationDetails.child("currentLocation").setValue(true);

    }
}

