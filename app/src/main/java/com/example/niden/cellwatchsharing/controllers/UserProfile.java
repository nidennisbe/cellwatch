package com.example.niden.cellwatchsharing.controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
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

public class UserProfile {

    private String strName, strBio, strPhone, strHobby, strExpDate, strProfileUrl;
    private DatabaseReference mRefUserInfo;
    private FirebaseUserEntity firebaseUserEntity = new FirebaseUserEntity();
    private Account mAccount = new Account();
    public static final String DIR_USER="users";
    public static final String DIR_STORAGE_PROFILE_PHOTO="user_profile_image/";


    public void saveUserProfileInfo(String userId, FirebaseUserEntity firebaseUserEntity){
       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
       /* Map<String, Object> result = new HashMap<>();
        result.put(userId,firebaseUserEntity);
        databaseReference.child("users").child(userId).updateChildren(result);*/

        Map<String, Object> result = new HashMap<>();
        result.put("name",firebaseUserEntity.getName());
        result.put("bio",firebaseUserEntity.getBio());
        result.put("expiration_date",firebaseUserEntity.getExpiration_date());
        result.put("hobby",firebaseUserEntity.getHobby());
        result.put("phone",firebaseUserEntity.getPhone());
        result.put("user_type",firebaseUserEntity.getUser_type());
        result.put("id",firebaseUserEntity.getId());
        result.put("profile_url",firebaseUserEntity.getProfile_url());
        databaseReference.child("users").child(FirebaseAuth.getInstance().getUid()).updateChildren(result);
    }



    //Showing profile information
    public void displayProfileInfo(final Context context, final TextView textViewName, final TextView textViewBio, final TextView textViewPhone, final TextView textViewExpDate, final TextView textViewHobby, final ImageView profilePicture) {
        mRefUserInfo = FirebaseDatabase.getInstance().getReference(DIR_USER)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mRefUserInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    getUserInfo(dataSnapshot);
                    textViewPhone.setText(strPhone);
                    textViewBio.setText(strBio);
                    textViewName.setText(strName);
                    textViewHobby.setText(strHobby);
                    textViewExpDate.setText(strExpDate);
                    if (strProfileUrl.isEmpty()) {
                        profilePicture.setImageResource(R.drawable.ic_user_blue);
                    }else {
                        Picasso.with(context).load(strProfileUrl).centerCrop()
                                .resize(110, 110).centerCrop()
                                .into(profilePicture);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    //Show profile Picture

    public void displayProfileImage(final String mUserKey,final Context context, final TextView textViewName, final TextView textViewBio, final ImageView profilePicture) {
        mRefUserInfo = FirebaseDatabase.getInstance().getReference(DIR_USER)
                .child(mUserKey);
        mRefUserInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    getUserInfo(dataSnapshot);
                    strName = firebaseUserEntity.getName();
                    strBio = firebaseUserEntity.getBio();
                    strProfileUrl = firebaseUserEntity.getProfile_url();
                    textViewBio.setText(strBio);
                    textViewName.setText(strName);
                    if (strProfileUrl.isEmpty()) {
                        profilePicture.setImageResource(R.drawable.ic_user_blue);
                    }else {
                        Picasso.with(context).load(strProfileUrl).centerCrop()
                                .resize(110, 110).centerCrop()
                                .into(profilePicture);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Showing profile information
    public void displayEditInfo(final Context context, final EditText editProfileName, final EditText editProfileBio, final EditText editProfileContact, final EditText editProfileHobby, final EditText editProfileExpDate, final ImageView profile) {
        mRefUserInfo = FirebaseDatabase.getInstance().getReference(DIR_USER)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mRefUserInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    getUserInfo(dataSnapshot);
                    editProfileContact.setText(strPhone);
                    editProfileBio.setText(strBio);
                    editProfileName.setText(strName);
                    editProfileHobby.setText(strHobby);
                    editProfileExpDate.setText(strExpDate);
                    if (strProfileUrl.isEmpty()) {
                        profile.setImageResource(R.drawable.ic_user_blue);
                    }else {
                        Picasso.with(context).load(strProfileUrl).centerCrop()
                                .resize(110, 110).centerCrop()
                                .into(profile);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void uploadProfilePicture(final Context context, Uri filePath, StorageReference storageReference, final DatabaseReference databaseReference) {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference mRefStorage = storageReference.child(DIR_STORAGE_PROFILE_PHOTO+ UUID.randomUUID().toString());
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
                            databaseReference.child(DIR_USER)
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(user);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded" + (int) progress + "%");
                        }
                    });
        }
    }
    private void getUserInfo(DataSnapshot dataSnapshot) {
        firebaseUserEntity = dataSnapshot.getValue(FirebaseUserEntity.class);
        strName = firebaseUserEntity.getName();
        strBio = firebaseUserEntity.getBio();
        strPhone = firebaseUserEntity.getPhone();
        strHobby = firebaseUserEntity.getHobby();
        strExpDate = firebaseUserEntity.getExpiration_date();
        strProfileUrl = firebaseUserEntity.getProfile_url();
    }
}
