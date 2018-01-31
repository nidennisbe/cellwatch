package com.example.niden.cellwatchsharing.controllers;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.EditProfileActivity;
import com.example.niden.cellwatchsharing.activities.LoginActivity;
import com.example.niden.cellwatchsharing.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by niden on 20-Nov-17.
 */

public class Account {
    private static final String TAG = Account.class.getSimpleName();
    public static FirebaseAuth firebaseAuth;
    public static FirebaseAuth.AuthStateListener mAuthListener;


    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth = FirebaseAuth.getInstance();
    }

    public String getFirebaseUserAuthenticateId() {
        String userId = null;
        if (firebaseAuth.getCurrentUser() != null) {
            userId = firebaseAuth.getCurrentUser().getUid();
        }
        return userId;
    }

    //Check if account already login open their profile
    public void checkUserLogin(final Context context) {
        if (firebaseAuth.getCurrentUser() != null) {
            Intent profileIntent = new Intent(context, MainActivity.class);
            context.startActivity(profileIntent);
        }
    }

    //Check who is currently login
    public void isUserCurrentlyLogin(final Context context) {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (null != user) {
                    Intent profileIntent = new Intent(context, MainActivity.class);
                    context.startActivity(profileIntent);
                } else {
                    Intent loginIntent = new Intent(context, LoginActivity.class);
                    context.startActivity(loginIntent);
                }
            }
        };
    }

    //Method for Account registration
    public void createNewUser(final Context context, String email, String password, final ProgressDialog myDialog) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Toast.makeText(context, "Fail to Register due to." + task.getException(), Toast.LENGTH_SHORT).show();
                            myDialog.dismiss();
                        } else {
//                            IntentUtils.openMainActivity(context);
                            Intent myIntent = new Intent(context, EditProfileActivity.class);
                            context.startActivity(myIntent);
                            ((Activity) context).finish();
                            //insertUserInformation();
                        }
                    }
                });
    }

    //Method for get account information and put in Database
    private void insertUserInformation() {

        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users");
        // Get references to the DialogsUtils of item_message.xmle.xml
        // EditText inputEmail = (EditText) findViewById(R.id.message_text);
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("uid:", firebaseAuth.getUid());
        userData.put("userEmail:", firebaseAuth.getCurrentUser().getEmail());
        mRef.push().setValue(userData);
    }


    //Login USER method
    public void loginAUser(final Context context, String email, String password, final ProgressDialog myDialog) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(context, R.string.alert_check_emailpassword, Toast.LENGTH_SHORT).show();
                            myDialog.dismiss();
                        } else {
                            myDialog.dismiss();
                            String currentDateTimeString = String.valueOf(System.currentTimeMillis());
                            final Task<Void> mRef = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid())
                                    .child("userLoginTime").push().setValue(currentDateTimeString);
                            Intent profileIntent = new Intent(context, MainActivity.class);
                            context.startActivity(profileIntent);


                        }
                    }

                    ;
                });

    }


}
