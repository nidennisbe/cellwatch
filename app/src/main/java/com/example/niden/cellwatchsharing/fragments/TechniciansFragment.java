package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;

import com.example.niden.cellwatchsharing.adapters.RecyclerTechniciansAdapter;
import com.example.niden.cellwatchsharing.utils.KeyboardUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import static com.example.niden.cellwatchsharing.database.DataQuery.QUERY_TECHNICIAN;
import static com.example.niden.cellwatchsharing.database.DataQuery.QUERY_TECHNICIAN_BY_NAME;

/**
 * Created by niden on 16-Nov-17.
 */

public class TechniciansFragment extends Fragment {
    View myView;
    Activity activity = getActivity();
    RecyclerTechniciansAdapter buildRecyclerTechniciansAdapter;
    RecyclerView technicianList;
    EditText mSearchField;
    Query query=QUERY_TECHNICIAN;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_technicians_layout, container, false);
        activity = getActivity();
        activity.setTitle(getString(R.string.toobar_technicians));
        setHasOptionsMenu(true);

        mSearchField = (EditText) myView.findViewById(R.id.search_field);
        technicianList = (RecyclerView) myView.findViewById(R.id.list_technician);
        initialTechnicianAdapter();


        technicianList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftKeyboard(v, activity);
            }
        });

        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = mSearchField.getText().toString();
                query = QUERY_TECHNICIAN_BY_NAME.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
                initialTechnicianAdapter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return myView;
    }


    private void initialTechnicianAdapter(){
        buildRecyclerTechniciansAdapter = new RecyclerTechniciansAdapter(query,activity,R.layout.item_technician);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        technicianList.setHasFixedSize(true);
        technicianList.setLayoutManager(layoutManager);
        technicianList.setAdapter(buildRecyclerTechniciansAdapter);
    }


}
