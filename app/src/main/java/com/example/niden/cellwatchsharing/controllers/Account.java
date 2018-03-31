package com.example.niden.cellwatchsharing.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.EditProfileActivity;
import com.example.niden.cellwatchsharing.activities.LoginActivity;
import com.example.niden.cellwatchsharing.activities.MainActivity;
import com.example.niden.cellwatchsharing.serivces.LocationBackgroundService;
import com.example.niden.cellwatchsharing.serivces.LocationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.example.niden.cellwatchsharing.utils.DialogsUtils.showAlertDialogDismiss;

/**
 * Created by niden on 20-Nov-17.
 */

public class Account {
    private static final String TAG = Account.class.getSimpleName();
    public FirebaseAuth firebaseAuth;
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
                            showAlertDialogDismiss(context, "Validatoin", context.getString(R.string.alert_check_emailpassword));
                            //ToastUtils.showSnackbar(v, context.getString(R.string.alert_check_emailpassword), Snackbar.LENGTH_LONG);
                            myDialog.dismiss();
                        } else {
                         checkGPS(context);
                         myDialog.dismiss();
                        }
                    }

                    ;
                });

    }

    private  void checkGPS(final Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        //No GPS run below code
        if (!gps_enabled && !network_enabled) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                }
            });
            dialog.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();

            //if GPS location enabled run below code
        }else {
            String currentDateTimeString = String.valueOf(System.currentTimeMillis());
            final Task<Void> mRef = FirebaseDatabase.getInstance().getReference().child(DIR_USER).child(firebaseAuth.getCurrentUser().getUid())
                    .child(DIR_CLOCKIN_INFO).push().setValue(currentDateTimeString);
            Intent profileIntent = new Intent(context, MainActivity.class);
            context.startActivity(profileIntent);
            ((Activity) context).finish();
            mDatabaseLocationDetails = FirebaseDatabase.getInstance().getReference().child("location").push();
            userOnlineisTrue();
            context.startService(new Intent(context, LocationService.class));
        }
    }


    private void userOnlineisTrue() {
        DatabaseReference onlineUserRef = FirebaseDatabase.getInstance().getReference(DIR_USER).child(getFirebaseUserAuthenticateId());
        Map<String, Object> result = new HashMap<>();
        result.put("online", true);
        onlineUserRef.updateChildren(result);
    }

    public void userOnlineisFalse(DatabaseReference onlineUserRef) {
        Map<String, Object> resultUser = new HashMap<>();
        resultUser.put("online", false);
        onlineUserRef.updateChildren(resultUser);
    }


}
