package com.example.niden.cellwatchsharing.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.OnlineUserEntityDatabase;
import com.example.niden.cellwatchsharing.database.UserEntityDatabase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnlineTechniciansActivity extends AppCompatActivity {
    //Firebase
public  static DatabaseReference onlineRef,currentUserRef,counterRef;
FirebaseRecyclerAdapter<OnlineUserEntityDatabase,OnlineTechnicansViewHolder> adapter;

//View
    RecyclerView onlineTechnicians;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_technicians);

        onlineTechnicians = (RecyclerView)findViewById(R.id.listOnline);
        onlineTechnicians.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        onlineTechnicians.setLayoutManager(layoutManager);

        onlineRef= FirebaseDatabase.getInstance().getReference().child("info/connected");
        counterRef=FirebaseDatabase.getInstance().getReference().child("lastOnline");
        currentUserRef=FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        setupSystem();
        updateList();


    }

    private void updateList() {
        adapter= new FirebaseRecyclerAdapter<OnlineUserEntityDatabase, OnlineTechnicansViewHolder>(

                OnlineUserEntityDatabase.class,
                R.layout.item_online_techincians,
                OnlineTechnicansViewHolder.class,
                counterRef
        ) {
            @Override
            protected void populateViewHolder(OnlineTechnicansViewHolder viewHolder, OnlineUserEntityDatabase model, int position) {
viewHolder.txtEmail.setText((model.getUserEmail()));
            }
        };
        adapter.notifyDataSetChanged();
        onlineTechnicians.setAdapter(adapter);
    }

    private void setupSystem() {
        onlineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Boolean.class)){
                currentUserRef.onDisconnect().removeValue();
                counterRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(new OnlineUserEntityDatabase(FirebaseAuth.getInstance().getCurrentUser().getEmail(),"Online"));
                adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        counterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    OnlineUserEntityDatabase user = postSnapshot.getValue(OnlineUserEntityDatabase.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
