package com.example.niden.cellwatchsharing.controllers;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.EditProfileActivity;
import com.example.niden.cellwatchsharing.activities.LoginActivity;
import com.example.niden.cellwatchsharing.activities.MainActivity;
import com.example.niden.cellwatchsharing.serivces.LocationBackgroundService;
import com.example.niden.cellwatchsharing.serivces.LocationService;
import com.example.niden.cellwatchsharing.utils.ToastUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by niden on 20-Nov-17.
 */

public class Account {
    private static final String TAG = Account.class.getSimpleName();
    private FirebaseAuth firebaseAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    private final String DIR_USER = "users";
    private final String DIR_CLOCKIN_INFO = "clock_in_info";
    private LocationBackgroundService gps;
    private DatabaseReference mDatabaseLocationDetails;
    public LocationService locationService = new LocationService();


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
        } else {
            Toast.makeText(context, "Error connection", Toast.LENGTH_SHORT).show();
        }
    }

    //Check who is currently login
    public void isUserCurrentlyLogin(final Context context) {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                DatabaseReference onlineUser = FirebaseDatabase.getInstance().getReference(DIR_USER).child(user.getUid());
                Map<String, Object> result = new HashMap<>();
                if (null != user) {
                    Intent profileIntent = new Intent(context, MainActivity.class);
                    context.startActivity(profileIntent);
                    result.put("online", true);
                    onlineUser.updateChildren(result);

                } else {
                    Intent loginIntent = new Intent(context, LoginActivity.class);
                    context.startActivity(loginIntent);
                    result.put("online", false);
                    onlineUser.updateChildren(result);
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
                        if (!task.isSuccessful()) {
                            Toast.makeText(context, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            myDialog.dismiss();
                        } else {
                            Intent myIntent = new Intent(context, EditProfileActivity.class);
                            context.startActivity(myIntent);
                            ((Activity) context).finish();
                        }
                    }
                });
    }


    //Login USER method
    public void loginAUser(final LinearLayout v, final Context context, final String email, String password, final ProgressDialog myDialog) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            ToastUtils.showSnackbar(v, context.getString(R.string.alert_check_emailpassword), Snackbar.LENGTH_LONG);
                            myDialog.dismiss();
                        } else {
                            myDialog.dismiss();
                            String currentDateTimeString = String.valueOf(System.currentTimeMillis());
                            final Task<Void> mRef = FirebaseDatabase.getInstance().getReference().child(DIR_USER).child(firebaseAuth.getCurrentUser().getUid())
                                    .child(DIR_CLOCKIN_INFO).push().setValue(currentDateTimeString);
                            Intent profileIntent = new Intent(context, MainActivity.class);
                            context.startActivity(profileIntent);
                            ((Activity) context).finish();
                            mDatabaseLocationDetails = FirebaseDatabase.getInstance().getReference().child("location").push();
                            userOnlineisTrue();
                           /* gps = new LocationBackgroundService(context);
                            context.startService(new Intent(context,LocationBackgroundService.class));*/
                            context.startService(new Intent(context, LocationService.class));
                         /*   if(gps.canGetLocation()){
                                //gps.getLocation().get
                                double latitude = gps.getLatitude();
                                double longitude = gps.getLongitude();
                                try {
                                    storeInDatabase(latitude,longitude,email,context);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(context, latitude+" ::: "+ longitude, Toast.LENGTH_SHORT).show();
                            }else{
                                gps.showSettingsAlert();
                            }*/
                        }
                    }

                    ;
                });

    }
    public void logoutUser(){

    }

    private void userOnlineisTrue() {
        DatabaseReference onlineUser = FirebaseDatabase.getInstance().getReference(DIR_USER).child(getFirebaseUserAuthenticateId());
        Map<String, Object> result = new HashMap<>();
        result.put("online", true);
        onlineUser.updateChildren(result);
    }

    public void userOnlineisFalse(String uId) {
        DatabaseReference onlineUser = FirebaseDatabase.getInstance().getReference(DIR_USER).child(uId);
        Map<String, Object> result = new HashMap<>();
        result.put("online", false);
        onlineUser.updateChildren(result);
    }


}
