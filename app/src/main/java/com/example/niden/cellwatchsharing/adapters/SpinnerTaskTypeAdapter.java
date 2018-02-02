package com.example.niden.cellwatchsharing.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;

/**
 * Created by niden on 03-Feb-18.
 */

public class SpinnerTaskTypeAdapter extends FirebaseListAdapter<String> {


    public SpinnerTaskTypeAdapter(Context context, Class<String> modelClass, int modelLayout, Query query) {
        super(context, modelClass, modelLayout, query);

    }

    @Override
    protected void populateView(View v, String model, int position) {
        TextView textview = (TextView) v.findViewById(android.R.id.text1);
        textview.setText(model);
    }

}
