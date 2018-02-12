package com.example.niden.cellwatchsharing.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.LocationDatabase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by niden on 07-Feb-18.
 */

public class GoogleMapAdapter implements OnMapReadyCallback {

    private GoogleMap mMap;
    Activity activity;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap= googleMap;
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("location").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LocationDatabase locationDatabase = dataSnapshot.getValue(LocationDatabase.class );
                LatLng targetLocation = new LatLng(locationDatabase.getLatitude(), locationDatabase.getLongitude());

                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.badge_technician);
                mMap.addMarker(new MarkerOptions().position(targetLocation).snippet("Technician")
                        .icon(icon)
                        .title(targetLocation+""+dataSnapshot.getKey()));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(targetLocation)      // Sets the center of the map to Mountain View
                        .zoom(6)                   // Sets the zoom
                        .bearing(10)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
