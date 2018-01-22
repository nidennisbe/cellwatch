package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
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
import com.example.niden.cellwatchsharing.activities.TechnicianActivity;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.database.User;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by niden on 16-Nov-17.
 */

public class TechniciansFragment extends Fragment {
    DatabaseReference mRef;
    View myView;
    Activity referenceActivity;
    private FirebaseListAdapter<FirebaseUserEntity> mAdapter;
    FirebaseUserEntity firebaseUserEntity = new FirebaseUserEntity();
    private ListView listOfTechnicians;
    Context context;
    DatabaseReference firebaseDatabase;
    TextView technicianName;
    public String technicianList;
    ArrayList<String> list = new ArrayList<String>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_technicians_layout,container,false);
        referenceActivity = getActivity();


        listOfTechnicians = (ListView) myView.findViewById(R.id.list_technician);
        displayFriendsList();

        return myView;
    }
    public void displayFriendsList() {
         mRef = FirebaseDatabase.getInstance().getReference().child("users");
        mAdapter = new FirebaseListAdapter<FirebaseUserEntity>(referenceActivity, FirebaseUserEntity.class,
                R.layout.item_list_technician, mRef) {


            @Override
            protected void populateView(View v, FirebaseUserEntity model, int position) {
                final TextView name_user = (TextView)v.findViewById(R.id.txt_name);
                mRef.child(mAdapter.getRef(position).getKey()).child("info").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        firebaseUserEntity = dataSnapshot.getValue(FirebaseUserEntity.class);
                        name_user.setText(firebaseUserEntity.getName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };


        listOfTechnicians.setAdapter(mAdapter);

    }
}
