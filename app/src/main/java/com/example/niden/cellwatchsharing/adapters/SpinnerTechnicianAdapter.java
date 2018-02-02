package com.example.niden.cellwatchsharing.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
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

public class SpinnerTechnicianAdapter extends FirebaseListAdapter<FirebaseUserEntity> {

    public SpinnerTechnicianAdapter(Context context, Class<FirebaseUserEntity> modelClass, int modelLayout, Query query) {
        super(context, modelClass, modelLayout, query);
    }

    @Override
    protected void populateView(final View v, final FirebaseUserEntity model, int position) {
        DatabaseReference mRefUser = FirebaseDatabase.getInstance().getReference().child("users");
        mRefUser.child(getRef(position).getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    FirebaseUserEntity firebaseUserEntity = dataSnapshot.getValue(FirebaseUserEntity.class);
                    TextView textView= (TextView) v.findViewById(android.R.id.text1);
                    Spinner spinner = (Spinner)v.findViewById(R.id.spinnerTechnician);
                    textView.setText(model.getName());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
