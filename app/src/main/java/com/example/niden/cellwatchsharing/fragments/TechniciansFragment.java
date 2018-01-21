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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by niden on 16-Nov-17.
 */

public class TechniciansFragment extends Fragment {
    DatabaseReference mRef;
    View myView;
    private FirebaseListAdapter<FirebaseUserEntity> mAdapter;
    FirebaseUserEntity firebaseUserEntity = new FirebaseUserEntity();
    RecyclerView recyclerView;
    private ListView listOfTechnicians;
    Context context;
    User mUser = new User();
    DatabaseReference firebaseDatabase;
    TextView technicianName;
    String technicianList;
    private static HashMap<String, String> personList = new HashMap<>();;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_technicians_layout,container,false);
         technicianName = (TextView) myView.findViewById(R.id.txt_name);
        listOfTechnicians = (ListView) myView.findViewById(R.id.list_technician);
        displayFriendsList();

        return myView;
    }
    public void displayFriendsList() {
         mRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("info");
        mAdapter = new FirebaseListAdapter<FirebaseUserEntity>(getActivity(), FirebaseUserEntity.class,
                R.layout.item_list_technician, mRef) {
            @Override
            protected void populateView(View v, FirebaseUserEntity model, int position) {

            }
        };
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                     firebaseUserEntity = postSnapshot.getValue(FirebaseUserEntity.class);

                    personList.put(FirebaseAuth.getInstance().getUid(), firebaseUserEntity.getName());



                   technicianList = firebaseUserEntity.getName();
               technicianName.setText(technicianList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listOfTechnicians.setAdapter(mAdapter);
    }
}
