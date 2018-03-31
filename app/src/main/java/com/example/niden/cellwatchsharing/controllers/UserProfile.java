package com.example.niden.cellwatchsharing.controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Button;
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
    Boolean userOnlineState;
    private DatabaseReference mRefUserInfo;
    private FirebaseUserEntity firebaseUserEntity = new FirebaseUserEntity();
    private Account mAccount = new Account();
    public static final String DIR_USER="users";
    public static final String DIR_STORAGE_PROFILE_PHOTO="user_profile_image/";



    public void saveUserProfileInfo(String userId, FirebaseUserEntity firebaseUserEntity){
       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> result = new HashMap<>();
        result.put("name",firebaseUserEntity.getName());
        result.put("bio",firebaseUserEntity.getBio());
        result.put("email",firebaseUserEntity.getEmail());
        result.put("expirationDate",firebaseUserEntity.getExpirationDate());
        result.put("hobby",firebaseUserEntity.getHobby());
        result.put("phone",firebaseUserEntity.getPhone());
        result.put("userType",firebaseUserEntity.getUserType());
        result.put("id",firebaseUserEntity.getId());
        result.put("online",true);
        result.put("profileUrl",firebaseUserEntity.getProfileUrl());
       // result.put("profileUrl",firebaseUserEntity.getProfileUrl());
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

    public void displayProfileImage(final String mUserKey, final Context context, final TextView textViewName, final TextView textViewBio, final ImageView profilePicture, final ImageView onlineIcon) {
        mRefUserInfo = FirebaseDatabase.getInstance().getReference(DIR_USER)
                .child(mUserKey);
        mRefUserInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    getUserInfo(dataSnapshot);
                    strName = firebaseUserEntity.getName();
                    strBio = firebaseUserEntity.getBio();
                    strProfileUrl = firebaseUserEntity.getProfileUrl();
                    String strOnline = String.valueOf(userOnlineState);
                    textViewBio.setText(strBio);
                    textViewName.setText(strName);

                    if (strOnline.equals("true")){
                        onlineIcon.setImageResource(R.drawable.ic_online);
                    }else{
                        onlineIcon.setImageResource(R.drawable.ic_offline);
                    }

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
    public void displayEditInfo(final Context context, final EditText editProfileName, final EditText editProfileBio, final EditText editProfileContact, final EditText editProfileHobby, final Button btnProfileExpDate, final ImageView profile) {
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
                    btnProfileExpDate.setText(strExpDate);
                    strProfileUrl = firebaseUserEntity.getProfileUrl();
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
                            user.put("profileUrl", url);
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
        strExpDate = firebaseUserEntity.getExpirationDate();
        strProfileUrl = firebaseUserEntity.getProfileUrl();
        userOnlineState = firebaseUserEntity.getOnline();
    }
}
