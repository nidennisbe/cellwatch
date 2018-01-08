package com.example.niden.cellwatchsharing.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.database.User;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by niden on 16-Nov-17.
 */

public class CirclesFragment extends Fragment {
    DatabaseReference myRef;
    View myView;
    private FirebaseListAdapter<FirebaseUserEntity> mAdapter;
    RecyclerView recyclerView;
    private ListView listOfTechnicians;
    Context context;
    User mUser = new User();
    DatabaseReference firebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_circles_layout,container,false);
        //recyclerView = (RecyclerView) myView.findViewById(R.id.technician_recyclerview);
        listOfTechnicians = (ListView) myView.findViewById(R.id.list_technician);
        displayFriendsList();

        return myView;
    }
    public void displayFriendsList() {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getFirebaseAuth().getUid()).child(mUser.getFirebaseAuth().getCurrentUser().getDisplayName());
        mAdapter = new FirebaseListAdapter<FirebaseUserEntity>(getActivity(), FirebaseUserEntity.class,
                R.layout.item_list_technician, mRef) {
            @Override
            protected void populateView(View v, FirebaseUserEntity model, int position) {
                TextView technicianName = (TextView) v.findViewById(R.id.txt_name);
                technicianName.setText(model.getName());
            }
        };
        listOfTechnicians.setAdapter(mAdapter);
    }
}
