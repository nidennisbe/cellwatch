package com.example.niden.cellwatchsharing.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Spinner;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.adapters.GoogleMapAdapter;
import com.example.niden.cellwatchsharing.adapters.RecyclerTechniciansAdapter;
import com.example.niden.cellwatchsharing.adapters.SpinnerTechnicianAdapter;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static com.example.niden.cellwatchsharing.database.DataQuery.QUERY_ONLY_TECHNICIAN;

/**
 * Created by niden on 16-Nov-17.
 */

public class MapFragment extends Fragment {
    MapView mMapView;
    private GoogleMap googleMap;
    View myView;
    public  static Activity mapFrag;
    GoogleMapAdapter showMapAdapter = new GoogleMapAdapter();
    RecyclerTechniciansAdapter buildRecyclerTechniciansAdapter;
    RecyclerView technicianList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_map_layout, container, false);
        mapFrag= getActivity();
        getActivity().setTitle("Map");
        mMapView = (MapView) myView.findViewById(R.id.google_map_view);
        mMapView.onCreate(savedInstanceState);
        technicianList = (RecyclerView)myView.findViewById(R.id.map_frag_recyclerview_technician);
        initialTechnicianAdapter();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {
                googleMap = mMap;
                MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(mapFrag, R.raw.map_theme);
                googleMap.setMapStyle(style);
                mMapView.onResume(); // needed to get the map to display immediately
                showMapAdapter.onMapReady(googleMap);
                if (ActivityCompat.checkSelfPermission(mapFrag, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mapFrag, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    return;
                }
                googleMap.setMyLocationEnabled(true);

            }
        });
        return myView;

    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void initialTechnicianAdapter(){
        buildRecyclerTechniciansAdapter = new RecyclerTechniciansAdapter(QUERY_ONLY_TECHNICIAN,mapFrag,R.layout.item_technician_map_frag);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mapFrag, LinearLayoutManager.HORIZONTAL, false);
        technicianList.setHasFixedSize(true);
        technicianList.setLayoutManager(layoutManager);
        technicianList.setItemAnimator(new SlideInRightAnimator(new OvershootInterpolator(1f)));
        technicianList.getItemAnimator().setChangeDuration(1000);
        technicianList.getItemAnimator().setAddDuration(1000);
        technicianList.getItemAnimator().setMoveDuration(1000);
        technicianList.setAdapter(buildRecyclerTechniciansAdapter);
        OverScrollDecoratorHelper.setUpOverScroll(technicianList, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
    }


}
