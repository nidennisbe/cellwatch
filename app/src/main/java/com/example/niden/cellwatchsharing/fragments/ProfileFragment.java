package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.EditProfileActivity;
import com.example.niden.cellwatchsharing.activities.LoginActivity;
import com.example.niden.cellwatchsharing.activities.SignUpActivity;
import com.example.niden.cellwatchsharing.database.FireBaseRetrieve;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.database.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;
import static com.example.niden.cellwatchsharing.activities.SignUpActivity.auth;
import static com.example.niden.cellwatchsharing.database.User.firebaseAuth;

/**
 * Created by niden on 16-Nov-17.
 */

public class ProfileFragment extends Fragment {
    View myView;
    View parentHolder;
    Activity referenceActivity;
    ImageView imageViewEditProfile;
    FireBaseRetrieve mFirebaseRetrive = new FireBaseRetrieve();

    TextView textViewName,textViewBio,textViewPhone,textViewHobby,textViewDateBirth;

    FirebaseUserEntity firebaseUserEntity = new FirebaseUserEntity();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        referenceActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_profile_layout,container,false);
       // myView = inflater.inflate(R.layout.fragment_profile_layout,container,false);
        getActivity().setTitle("Profile");

        imageViewEditProfile = (ImageView)parentHolder.findViewById(R.id.btn_edit_profile);
        textViewName = (TextView)parentHolder.findViewById(R.id.user_profile_name);
        textViewBio = (TextView)parentHolder.findViewById(R.id.user_profile_short_bio);
        textViewPhone = (TextView)parentHolder.findViewById(R.id.tv_phonenumber);
        textViewHobby = (TextView)parentHolder.findViewById(R.id.tv_hobby);
        textViewDateBirth = (TextView)parentHolder.findViewById(R.id.tv_date_birth);

        mFirebaseRetrive.displayProfileInfo(textViewName,textViewBio,textViewPhone,textViewHobby,textViewDateBirth);
        imageViewEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(referenceActivity, EditProfileActivity.class));
                referenceActivity.finish();
            }
        });
        return parentHolder;
    }

}
