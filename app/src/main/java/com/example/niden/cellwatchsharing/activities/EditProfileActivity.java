package com.example.niden.cellwatchsharing.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.controllers.UserProfile;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.utils.DatePickerUtils;
import com.example.niden.cellwatchsharing.utils.GallaryUtils;
import com.example.niden.cellwatchsharing.utils.KeyboardUtils;
import com.example.niden.cellwatchsharing.utils.ToastUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import static com.example.niden.cellwatchsharing.activities.MainActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
import static com.example.niden.cellwatchsharing.utils.DialogsUtils.showAlertDialogDismiss;
import static com.example.niden.cellwatchsharing.utils.ShakerAnimationUtils.shakeError;


//sixth push
public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = EditProfileActivity.class.getSimpleName();

    int RESULT_LOAD_IMAGE = 1;
    private TextInputLayout profileNameWrapper, profileBioWrapper, profilePhoneWrapper, profileEmailWrapper;
    //Firebase
    FirebaseUserEntity firebaseUserEntity;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private EditText editProfileName;
    private EditText editProfileBio;
    private EditText editProfileContact;
    private EditText editProfileHobby;
    private ImageView profile;
    private Button datePicker;
    UserProfile mUserProfile = new UserProfile();
    public CoordinatorLayout coordinatorLayout;
    LinearLayout parentLayout;
    Activity mActivity;
    Vibrator vibrator ;
    public final int timeOfVibrate = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_secondary);
        mActivity=this;
        setTitle(getString(R.string.toolbar_edit_profile_info));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_second);
        setSupportActionBar(toolbar);
        bindingView();
        vibrator=(Vibrator) getSystemService(mActivity.VIBRATOR_SERVICE);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = storage.getReferenceFromUrl("gs://cellwatchsharing.appspot.com/");


        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftKeyboard(v, mActivity);
            }
        });
        mUserProfile.displayEditInfo(mActivity, editProfileName, editProfileBio, editProfileContact, editProfileHobby, datePicker, profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                }
                GallaryUtils.openGallary(mActivity, RESULT_LOAD_IMAGE);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actMain = new Intent(mActivity, MainActivity.class);
                startActivity(actMain);
                mActivity.finish();
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerUtils.openDatePicker(mActivity, datePicker);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent actMain = new Intent(mActivity, MainActivity.class);
        this.finish();
        startActivity(actMain);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE) {
            Uri filePath = data.getData();
            Picasso.with(mActivity).load(data.getData()).noPlaceholder().centerCrop().fit()
                    .into((ImageView) findViewById(R.id.btn_change_profile));
            mUserProfile.uploadProfilePicture(mActivity, filePath, storageReference, databaseReference);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        fieldsValidation();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

    private void onSaveClick() {
        String profileName = editProfileName.getText().toString();
        String profileBio = editProfileBio.getText().toString();
        String profileContact = editProfileContact.getText().toString();
        String profileHobby = editProfileHobby.getText().toString();
        String profileExpDate = datePicker.getText().toString();

        // update the account profile information in Firebase database.\
        if (TextUtils.isEmpty(profileName) && TextUtils.isEmpty(profileBio) && TextUtils.isEmpty(profileContact)
                && TextUtils.isEmpty(profileHobby) && TextUtils.isEmpty(profileExpDate)) {
            ToastUtils.showSnackbar(coordinatorLayout, "Please fill in all the fields", Snackbar.LENGTH_LONG);
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent firebaseUserIntent = new Intent(mActivity, LoginActivity.class);
            startActivity(firebaseUserIntent);
            finish();
        } else {
            String id = user.getUid();
            String profileEmail = user.getEmail();

            firebaseUserEntity = new FirebaseUserEntity(id, profileEmail, profileName, profileBio, profileContact, profileHobby,  profileExpDate,"" , "technician",true);
            mUserProfile.saveUserProfileInfo(id, firebaseUserEntity);
            Toast.makeText(mActivity, "Edit Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mActivity, MainActivity.class);
            startActivity(intent);
            mActivity.finish();
        }
        //ToastUtils.showSnackbar(coordinatorLayout, "Saved Complete", Snackbar.LENGTH_LONG);
    }
    private void bindingView(){
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cor_layout);
        profile = (ImageView) findViewById(R.id.btn_change_profile);
        editProfileName = (EditText) findViewById(R.id.profile_name);
        editProfileBio = (EditText) findViewById(R.id.profile_bio);
        editProfileContact = (EditText) findViewById(R.id.profile_phone);
        editProfileHobby = (EditText) findViewById(R.id.ed_profile_hobby);
       //editProfileExp = (EditText) findViewById(R.id.ed_profile_exp_date);
        parentLayout = (LinearLayout) findViewById(R.id.layout_parent);
        datePicker = (Button)findViewById(R.id.btnExpirationDate);
        profileNameWrapper = (TextInputLayout) parentLayout.findViewById(R.id.profile_name_wrapper);
        profileBioWrapper = (TextInputLayout) parentLayout.findViewById(R.id.profile_bio_wrapper);
        profilePhoneWrapper = (TextInputLayout) parentLayout.findViewById(R.id.profile_phone_wrapper);
        profileEmailWrapper = (TextInputLayout) parentLayout.findViewById(R.id.profile_email_wrapper);
    }

    private void fieldsValidation() {
        String strProfileName = editProfileName.getText().toString();
        String strProfileBio = editProfileBio.getText().toString();
        String strProfilePhone = editProfileContact.getText().toString();
        String strProfileEmail = editProfileHobby.getText().toString();

        //Validation
        if (TextUtils.isEmpty(strProfileName)) {
            profileNameWrapper.setError("Please field in profile name");
            profileNameWrapper.startAnimation(shakeError());
            vibrator.vibrate(timeOfVibrate);
        } else {
            profileNameWrapper.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(strProfileBio)) {
            profileBioWrapper.setError("Please field in bio");
            profileBioWrapper.startAnimation(shakeError());
            vibrator.vibrate(timeOfVibrate);
        } else {
            profileBioWrapper.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(strProfilePhone)) {
            profilePhoneWrapper.setError("Please field in phone");
            profilePhoneWrapper.startAnimation(shakeError());
            vibrator.vibrate(timeOfVibrate);
        } else {
            profilePhoneWrapper.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(strProfileEmail)) {
            profileEmailWrapper.setError("Please field in email");
            profileEmailWrapper.startAnimation(shakeError());
            vibrator.vibrate(timeOfVibrate);
        } else {
            profileEmailWrapper.setErrorEnabled(false);
        }

        if (!TextUtils.isEmpty(strProfileName) && !TextUtils.isEmpty(strProfileBio) && !TextUtils.isEmpty(strProfilePhone) && !TextUtils.isEmpty(strProfileEmail)) {
            onSaveClick();
        }
    }
}
