package com.example.niden.cellwatchsharing.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.TechnicianActivity;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by niden on 18-Nov-17.
 */

public class ListTechniciansAdapter extends FirebaseRecyclerAdapter<FirebaseUserEntity,ListTechniciansAdapter.Viewholder>{
    public Activity activity;
    DatabaseReference mRef;
    private FirebaseListAdapter<FirebaseUserEntity> mAdapter;
    FirebaseUserEntity firebaseUserEntity = new FirebaseUserEntity();
    private ListView listOfTechnicians;

    public ListTechniciansAdapter(Query ref, Activity activity, int layout) {
        super(FirebaseUserEntity.class, layout, ListTechniciansAdapter.Viewholder.class, ref);
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected void populateViewHolder(Viewholder viewHolder, FirebaseUserEntity model, int position) {
        viewHolder.name.setText(model.getName());
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;

        public Viewholder(View view) {
            super(view);
//            imageView = (ImageView) view.findViewById(R.id.imageView);
             name = (TextView) view.findViewById(R.id.txt_name);



        }
    }

    public void displayFriendsList() {
        mRef = FirebaseDatabase.getInstance().getReference().child("users");
        mAdapter = new FirebaseListAdapter<FirebaseUserEntity>(activity, FirebaseUserEntity.class,
                R.layout.item_technician, mRef) {


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
        listOfTechnicians.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(activity, TechnicianActivity.class);
                activity.startActivity(myIntent);
            }
        });
    }
}
