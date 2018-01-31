package com.example.niden.cellwatchsharing.controllers;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by niden on 25-Nov-17.
 */

public class User extends Application {

    private String strName, strBio, strPhone, strHobby, strDateBirth, strProfileUrl;
    private DatabaseReference mMessagesDatabaseReference;
    private FirebaseUserEntity firebaseUserEntity = new FirebaseUserEntity();


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
                Picasso.with(context).load(strProfileUrl).centerCrop()
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

    public void displayProfileImage(final String mUserKey,final Context context, final TextView textViewName, final TextView textViewBio, final ImageView profilePicture) {
        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(mUserKey).child("info");
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

    public void uploadProfilePicture(final Context context, Uri filePath, StorageReference storageReference, final DatabaseReference databaseReference) {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference mRefStorage = storageReference.child("profile_images/" + UUID.randomUUID().toString());
            mRefStorage.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            String url = downloadUrl.toString();
                            Map<String, Object> user = new HashMap<>();
                            user.put("profile_url", url);
                            databaseReference.child("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("info").updateChildren(user);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }
}
