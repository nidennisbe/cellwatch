package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.EditProfileActivity;
import com.example.niden.cellwatchsharing.controllers.Account;
import com.example.niden.cellwatchsharing.controllers.UserProfile;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;

import static com.example.niden.cellwatchsharing.activities.MainActivity.activity;


/**
 * Created by niden on 16-Nov-17.
 */

public class ProfileFragment extends Fragment {

    private Activity refActivity;
    private UserProfile mUserProfile = new UserProfile();
    private Account mAccount = new Account();
    TextView textViewName, textViewBio, textViewPhone, textViewHobby, textViewExpDate;
    ImageView profileImage, addTaskFragment;
    View parentHolder;
    LinearLayout btnComplete;
    public TextView taskButton,taskUncompleteButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        refActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_profile_layout, container, false);
        setHasOptionsMenu(true);
        mAccount.isUserCurrentlyLogin(activity);
        getActivity().setTitle("Profile");
        bindingViews();
        final FragmentManager fragmentManager = getFragmentManager();

        mUserProfile.displayProfileInfo(refActivity, textViewName, textViewBio, textViewPhone, textViewHobby, textViewExpDate, profileImage);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new TaskFragment()).commit();
            }
        });
        addTaskFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new CreateTaskForTechnicianFragment()).commit();
            }
        });

        return parentHolder;
    }

    @Override
    public void onResume() {
        mAccount.isUserCurrentlyLogin(activity);
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(refActivity, EditProfileActivity.class));
        refActivity.finish();
        return super.onOptionsItemSelected(item);
    }

    private void bindingViews() {
        btnComplete = (LinearLayout)parentHolder.findViewById(R.id.btn_complete);
        profileImage = (ImageView) parentHolder.findViewById(R.id.profile_image);
        textViewName = (TextView) parentHolder.findViewById(R.id.user_profile_name);
        textViewBio = (TextView) parentHolder.findViewById(R.id.user_profile_short_bio);
        textViewPhone = (TextView) parentHolder.findViewById(R.id.tv_phonenumber);
        textViewHobby = (TextView) parentHolder.findViewById(R.id.prof_tv_hobby);
        textViewExpDate = (TextView) parentHolder.findViewById(R.id.prof_tv_exp_date);
        addTaskFragment = (ImageView)parentHolder.findViewById(R.id.btn_add_task);
    }


}
