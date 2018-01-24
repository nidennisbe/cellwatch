package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.EditProfileActivity;
import com.example.niden.cellwatchsharing.activities.MainActivity;
import com.example.niden.cellwatchsharing.classes.FireBaseRetrieve;
import com.example.niden.cellwatchsharing.classes.User;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.google.firebase.auth.FirebaseAuth;

import static android.app.Activity.RESULT_OK;
import static com.example.niden.cellwatchsharing.activities.MainActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
import static com.example.niden.cellwatchsharing.activities.MainActivity.activity;
import static com.example.niden.cellwatchsharing.helper.Helper.SELECT_PICTURE;

/**
 * Created by niden on 16-Nov-17.
 */

public class ProfileFragment extends Fragment {
    View myView;
    View parentHolder;
    Activity referenceActivity;
    ImageView imageViewEditProfile,profileImage;
    FireBaseRetrieve mFirebaseRetrive = new FireBaseRetrieve();
    User mUser = new User();
    TextView textViewName,textViewBio,textViewPhone,textViewHobby,textViewDateBirth;

    FirebaseUserEntity firebaseUserEntity = new FirebaseUserEntity();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        referenceActivity = getActivity();
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

        mFirebaseRetrive.displayProfileInfo(referenceActivity,textViewName,textViewBio,textViewPhone,textViewHobby,textViewDateBirth,profileImage);
        imageViewEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(referenceActivity, EditProfileActivity.class));
                referenceActivity.finish();
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
