package com.example.niden.cellwatchsharing.adapters;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.database.LocationDatabase;
import com.example.niden.cellwatchsharing.utils.TSConverterUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.niden.cellwatchsharing.fragments.MapFragment.mapFrag;

/**
 * Created by niden on 07-Feb-18.
 */

public class GoogleMapAdapter extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    List<LatLng> latLngs = new ArrayList<>();

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap= googleMap;
        latLngs.add(new LatLng(-36.851667, 174.63601));
        latLngs.add(new LatLng(-36.8558542, 174.7651055));
        latLngs.add(new LatLng(-36.7558542, 174.6651055));

        final DatabaseReference locationRef= rootRef.child("location");
        locationRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final LocationDatabase locationDatabase = dataSnapshot.getValue(LocationDatabase.class );
                DatabaseReference usersRef = rootRef.child("users");
                assert locationDatabase != null;
                usersRef.orderByChild("id").equalTo(locationDatabase.getEachUserID()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        FirebaseUserEntity userDatabase = dataSnapshot.getValue(FirebaseUserEntity.class );
                        assert userDatabase != null;
                        String imageUrl = userDatabase.getProfileUrl();
                        final String nameOfTechnician = userDatabase.getName();
                        Glide.with(mapFrag).asBitmap().load(imageUrl).into(new SimpleTarget<Bitmap>(55,55) {
                            @Override
                            public void onResourceReady(@NonNull Bitmap bmpProfileImage, @Nullable Transition<? super Bitmap> transition) {
                                LatLng targetLocation = new LatLng(locationDatabase.getLatitude(), locationDatabase.getLongitude());
                                long timeStamp = Long.parseLong(locationDatabase.getTimeStamp());
                                mMap.addMarker(new MarkerOptions().position(targetLocation)
                                        .snippet("Technician: "+nameOfTechnician+" | Time: "+TSConverterUtils.getTimeFormat(timeStamp))
                                        .icon(BitmapDescriptorFactory.fromBitmap(bmpProfileImage))
                                        .title(locationDatabase.getAddress()));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(targetLocation)
                                        .zoom(16)
                                        .bearing(10)
                                        .tilt(80)
                                        .build();
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                               // drawPolyLineOnMap(latLngs);
                            }
                        });
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
    // Draw polyline on map
    public void drawPolyLineOnMap(List<LatLng> list) {
        PolygonOptions polyOptions= new PolygonOptions();
       // polyOptions.color(Color.RED);
        //polyOptions.width(5);
        polyOptions.fillColor(Color.BLUE);
        polyOptions.addAll(list);

        mMap.addPolygon(polyOptions);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : list) {
            builder.include(latLng);
        }

        final LatLngBounds bounds = builder.build();

        //BOUND_PADDING is an int to specify padding of bound.. try 100.
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        mMap.animateCamera(cu);
    }
}
