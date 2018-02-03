package com.example.niden.cellwatchsharing.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.adapters.RecyclerTaskAdapter;
import com.example.niden.cellwatchsharing.controllers.UserProfile;
import com.example.niden.cellwatchsharing.controllers.Account;
import com.example.niden.cellwatchsharing.utils.IntentUtils;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static com.example.niden.cellwatchsharing.activities.MainActivity.activity;
import static com.example.niden.cellwatchsharing.fragments.TaskFragment.recyclerView;

public class TechnicianActivity extends AppCompatActivity {
    TextView textViewName,textViewBio,textViewPhone,textViewHobby,textViewDateBirth;
    Account account = new Account();
    ImageView profileImage;
    Query mRef;
    UserProfile mUserProfile = new UserProfile();
    RelativeLayout cover;
    public  static String mUserKey;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        bindingViews();

        mUserKey = getIntent().getStringExtra("key");
        mUserProfile.displayProfileImage(mUserKey,TechnicianActivity.this,textViewName,textViewBio,profileImage);
        mRef = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(mUserKey)
                .child("tasks");

        RecyclerTaskAdapter mAdapter = new RecyclerTaskAdapter(mRef, activity,R.layout.item_task );
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
        OverScrollDecoratorHelper.setUpStaticOverScroll(cover, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

    }


    @Override
    public void onBackPressed() {
        this.finish();
        IntentUtils.openMainActivity(activity);
    }

    @Override
    protected void onRestart() {
        account.checkUserLogin(activity);
        super.onRestart();
    }

    private void bindingViews(){
        profileImage= findViewById(R.id.profile_image);
        textViewName = (TextView)findViewById(R.id.user_profile_name);
        textViewBio = (TextView)findViewById(R.id.user_profile_short_bio);
        recyclerView = (RecyclerView) findViewById(R.id.listTask);
        cover = (RelativeLayout)findViewById(R.id.background);
    }



}
