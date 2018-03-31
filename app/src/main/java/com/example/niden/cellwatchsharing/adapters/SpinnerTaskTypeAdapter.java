package com.example.niden.cellwatchsharing.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.TaskTypeEntityDatabase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by niden on 03-Feb-18.
 */

public class SpinnerTaskTypeAdapter extends FirebaseListAdapter<TaskTypeEntityDatabase> {


    public SpinnerTaskTypeAdapter(Context context, Class<TaskTypeEntityDatabase> modelClass, int modelLayout, Query query) {
        super(context, modelClass, modelLayout, query);

    }

    @Override
    protected void populateView(final View v, final TaskTypeEntityDatabase model, int position) {
        DatabaseReference mRefTaskType = FirebaseDatabase.getInstance().getReference().child("taskType");
        mRefTaskType.child(getRef(position).getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView textview = (TextView) v.findViewById(R.id.txt_type_task);
                textview.setText(model.getType());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
