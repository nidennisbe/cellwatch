package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.niden.cellwatchsharing.R;

import com.example.niden.cellwatchsharing.adapters.RecyclerTechniciansAdapter;

import static com.example.niden.cellwatchsharing.database.DataQuery.QUERY_TECHNICIAN;

/**
 * Created by niden on 16-Nov-17.
 */

public class TechniciansFragment extends Fragment {
    View myView;
    Activity activity = getActivity();
    RecyclerTechniciansAdapter buildRecyclerTechniciansAdapter;
    RecyclerView technicianList;



    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_technicians_layout, container, false);
        activity = getActivity();
        activity.setTitle(getString(R.string.toobar_technicians));
        setHasOptionsMenu(true);

        technicianList = (RecyclerView) myView.findViewById(R.id.list_technician);
        buildRecyclerTechniciansAdapter = new RecyclerTechniciansAdapter(QUERY_TECHNICIAN,activity,R.layout.item_technician);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        technicianList.setHasFixedSize(true);
        technicianList.setLayoutManager(layoutManager);
        technicianList.setAdapter(buildRecyclerTechniciansAdapter);
        return myView;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
