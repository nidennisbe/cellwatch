package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.SignUpActivity;
import com.example.niden.cellwatchsharing.adapters.RecyclerTechniciansAdapter;
import com.example.niden.cellwatchsharing.utils.KeyboardUtils;
import com.google.firebase.database.Query;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

import static com.example.niden.cellwatchsharing.database.DataQuery.QUERY_ONLY_TECHNICIAN;
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
    Button btnAddTech;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_technicians_layout, container, false);
        activity = getActivity();
        activity.setTitle(getString(R.string.toobar_technicians));
        setHasOptionsMenu(true);

        mSearchField = (EditText) myView.findViewById(R.id.search_field);
        technicianList = (RecyclerView) myView.findViewById(R.id.list_technician);
        btnAddTech = (Button)myView.findViewById(R.id.btn_add_new_tech);
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

        btnAddTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SignUpActivity.class));
                getActivity().finish();
            }
        });
        return myView;
    }


    private void initialTechnicianAdapter(){
        buildRecyclerTechniciansAdapter = new RecyclerTechniciansAdapter(QUERY_ONLY_TECHNICIAN,activity,R.layout.item_technician);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        technicianList.setHasFixedSize(true);
        technicianList.setLayoutManager(layoutManager);
        technicianList.setItemAnimator(new SlideInDownAnimator(new OvershootInterpolator(1f)));
        technicianList.getItemAnimator().setChangeDuration(1000);
        technicianList.getItemAnimator().setAddDuration(1000);
        technicianList.getItemAnimator().setMoveDuration(1000);
        technicianList.setAdapter(buildRecyclerTechniciansAdapter);
    }


}
