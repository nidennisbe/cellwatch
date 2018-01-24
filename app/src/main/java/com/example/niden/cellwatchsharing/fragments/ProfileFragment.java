package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.EditProfileActivity;
import com.example.niden.cellwatchsharing.classes.FireBaseRetrieve;
import com.example.niden.cellwatchsharing.classes.User;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;

import static com.example.niden.cellwatchsharing.activities.MainActivity.activity;

/**
 * Created by niden on 16-Nov-17.
 */

public class ProfileFragment extends Fragment {

    private Activity refActivity;
    private FireBaseRetrieve mFirebaseRetrive = new FireBaseRetrieve();
    private User mUser = new User();
    TextView textViewName,textViewBio,textViewPhone,textViewHobby,textViewDateBirth;
    ImageView imageViewEditProfile,profileImage;
    View parentHolder;

    FirebaseUserEntity firebaseUserEntity = new FirebaseUserEntity();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        refActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_profile_layout,container,false);
        mUser.isUserCurrentlyLogin(activity);
        getActivity().setTitle("Profile");

        profileImage = (ImageView)parentHolder.findViewById(R.id.profile_image) ;
        imageViewEditProfile = (ImageView)parentHolder.findViewById(R.id.btn_edit_profile);
        textViewName = (TextView)parentHolder.findViewById(R.id.user_profile_name);
        textViewBio = (TextView)parentHolder.findViewById(R.id.user_profile_short_bio);
        textViewPhone = (TextView)parentHolder.findViewById(R.id.tv_phonenumber);
        textViewHobby = (TextView)parentHolder.findViewById(R.id.tv_hobby);
        textViewDateBirth = (TextView)parentHolder.findViewById(R.id.tv_date_birth);

        mFirebaseRetrive.displayProfileInfo(refActivity,textViewName,textViewBio,textViewPhone,textViewHobby,textViewDateBirth,profileImage);
        imageViewEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(refActivity, EditProfileActivity.class));
                refActivity.finish();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        return parentHolder;


    }



    @Override
    public void onResume() {
        mUser.isUserCurrentlyLogin(activity);
        super.onResume();

    }


}
