package com.example.niden.cellwatchsharing.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.example.niden.cellwatchsharing.database.TaskTypeEntityDatabase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by niden on 03-Feb-18.
 */

public class SpinnerTaskTypeAdapter extends FirebaseListAdapter<TaskTypeEntityDatabase> {


    public SpinnerTaskTypeAdapter(Context context, Class<TaskTypeEntityDatabase> modelClass, int modelLayout, Query query) {
        super(context, modelClass, modelLayout, query);

    }

    @Override
    protected void populateView(final View v, final TaskTypeEntityDatabase model, int position) {
        DatabaseReference mRefTaskType = FirebaseDatabase.getInstance().getReference();
        mRefTaskType.child("taskType").child(getRef(position).getKey()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    TextView textview = (TextView) v.findViewById(android.R.id.text1);
                    textview.setText(model.getType());
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
