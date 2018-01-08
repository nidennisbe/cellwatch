package com.example.niden.cellwatchsharing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.database.User;
import com.example.niden.cellwatchsharing.database.UserEntityDatabase;
import com.example.niden.cellwatchsharing.helper.Helper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.niden.cellwatchsharing.activities.MainActivity.activity;

public class TechnicianActivity extends AppCompatActivity {
    TextView textViewName,textViewBio,textViewContact;
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewName = (TextView)findViewById(R.id.user_profile_name);
        textViewBio = (TextView)findViewById(R.id.user_profile_short_bio);
        //textViewContact = (TextView)findViewById(R.id.user);


    }

    public void btnOnClickEditProfile(View view) {
        Intent editProfileIntent = new Intent(this, EditProfileActivity.class);
        startActivity(editProfileIntent);
    }

    @Override
    protected void onRestart() {
        user.checkUserLogin(activity);
        super.onRestart();
    }

}
