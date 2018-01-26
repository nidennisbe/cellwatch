package com.example.niden.cellwatchsharing.classes;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.classes.User;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by niden on 25-Nov-17.
 */

public class FireBaseRetrieve {

    String strName, strBio, strPhone, strHobby, strDateBirth,strProfileUrl;
    DatabaseReference mMessagesDatabaseReference;
    User user = new User();
    FirebaseUserEntity firebaseUserEntity = new FirebaseUserEntity();




    //Showing profile information
    public void displayProfileInfo(final Context context, final TextView textViewName, final TextView textViewBio, final TextView textViewPhone, final TextView textViewHobby, final TextView textViewDateBirth, final ImageView profilePicture) {
        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("info");
        mMessagesDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                firebaseUserEntity = dataSnapshot.getValue(FirebaseUserEntity.class);
                strName = firebaseUserEntity.getName();
                strBio = firebaseUserEntity.getBio();
                strPhone = firebaseUserEntity.getPhone();
                strHobby = firebaseUserEntity.getHobby();
                strDateBirth = firebaseUserEntity.getBirthday();
                strProfileUrl = firebaseUserEntity.getProfile_url();

                textViewPhone.setText(strPhone);
                textViewBio.setText(strBio);
                textViewName.setText(strName);
                textViewHobby.setText(strHobby);
                textViewDateBirth.setText(strDateBirth);
                Picasso.with(context).load(strProfileUrl)
                        .resize(110, 110).centerCrop()
                        .into(profilePicture);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    //Show profile Picture

    public void displayProfileImage(final Context context, final TextView textViewName, final TextView textViewBio, final ImageView profilePicture) {
        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("info");
        mMessagesDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                firebaseUserEntity = dataSnapshot.getValue(FirebaseUserEntity.class);
                strName = firebaseUserEntity.getName();
                strBio = firebaseUserEntity.getBio();
                strProfileUrl = firebaseUserEntity.getProfile_url();


                textViewBio.setText(strBio);
                textViewName.setText(strName);
                Picasso.with(context).load(strProfileUrl)
                        .resize(110, 110).centerCrop()
                        .into(profilePicture);
//
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    //Showing profile information
    public void displayEditInfo(final Context context, final EditText editProfileName, final EditText editProfileBio, final EditText editProfileContact, final EditText editProfileHobby, final EditText editProfileBirthday, final ImageView profile) {
        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("info");
        mMessagesDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                firebaseUserEntity = dataSnapshot.getValue(FirebaseUserEntity.class);
                strName = firebaseUserEntity.getName();
                strBio = firebaseUserEntity.getBio();
                strPhone = firebaseUserEntity.getPhone();
                strHobby = firebaseUserEntity.getHobby();
                strDateBirth = firebaseUserEntity.getBirthday();
                strProfileUrl = firebaseUserEntity.getProfile_url();

                editProfileContact.setText(strPhone);
                editProfileBio.setText(strBio);
                editProfileName.setText(strName);
                editProfileHobby.setText(strHobby);
                editProfileBirthday.setText(strDateBirth);
                Picasso.with(context).load(strProfileUrl)
                        .resize(110, 110).centerCrop()
                        .into(profile);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
