package com.example.niden.cellwatchsharing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.adapters.ListTaskAdapter;
import com.example.niden.cellwatchsharing.classes.FireBaseRetrieve;
import com.example.niden.cellwatchsharing.classes.User;
import com.example.niden.cellwatchsharing.utils.IntentUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

import static com.example.niden.cellwatchsharing.activities.MainActivity.activity;

public class TechnicianActivity extends AppCompatActivity {
    TextView textViewName,textViewBio,textViewPhone,textViewHobby,textViewDateBirth;
    User user = new User();
    ImageView profileImage;
    RecyclerView recyclerView;
    Query mRef;
    FireBaseRetrieve mFirebaseRetrive = new FireBaseRetrieve();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician1);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        profileImage = (ImageView)findViewById(R.id.user_profile_pic);
        textViewName = (TextView)findViewById(R.id.user_profile_name);
        textViewBio = (TextView)findViewById(R.id.user_profile_short_bio);
        //textViewContact = (TextView)findViewById(R.id.user);
        recyclerView = (RecyclerView) findViewById(R.id.listTask);



        mFirebaseRetrive.displayProfileImage(TechnicianActivity.this,textViewName,textViewBio,profileImage);

        mRef = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("tasks");

        ListTaskAdapter mAdapter = new ListTaskAdapter(mRef, activity,R.layout.item_task );
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new SlideInDownAnimator(new OvershootInterpolator(1f)));
        recyclerView.getItemAnimator().setChangeDuration(1000);
        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.setAdapter(mAdapter);




    }

    public void btnOnClickEditProfile(View view) {
        Intent editProfileIntent = new Intent(this, EditProfileActivity.class);
        startActivity(editProfileIntent);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        IntentUtils.openMainActivity(activity);
    }

    @Override
    protected void onRestart() {
        user.checkUserLogin(activity);
        super.onRestart();
    }



}
