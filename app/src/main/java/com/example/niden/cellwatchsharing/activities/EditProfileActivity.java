package com.example.niden.cellwatchsharing.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.classes.User;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.helper.FirebaseDatabaseHelper;
import com.example.niden.cellwatchsharing.utils.GallaryUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import static com.example.niden.cellwatchsharing.activities.MainActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
import static com.example.niden.cellwatchsharing.utils.ToastUtils.displayMessageToast;


//sixth push
public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = EditProfileActivity.class.getSimpleName();

    //Firebase
    FirebaseUserEntity firebaseUserEntity;
    DatabaseReference databaseReference;
    int RESULT_LOAD_IMAGE=1;
    StorageReference storageReference;
    private EditText editProfileName;
    private EditText editProfileBio;
    private EditText editProfileContact;
    private EditText editProfileHobby;
    private EditText editProfileBirthday;
    private ImageView profile;
    private User mUser = new User();

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle(getString(R.string.toolbar_edit_profile_info));


        FirebaseStorage storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = storage.getReferenceFromUrl("gs://cellwatchsharing.appspot.com/");



        profile = (ImageView)findViewById(R.id.btn_change_profile);
        editProfileName = (EditText) findViewById(R.id.profile_name);
        editProfileBio = (EditText) findViewById(R.id.profile_bio);
        editProfileContact = (EditText) findViewById(R.id.profile_phone);
        editProfileHobby = (EditText) findViewById(R.id.profile_expiration_date);
        editProfileBirthday = (EditText) findViewById(R.id.profile_hobby);
        Button saveEditButton = (Button) findViewById(R.id.save_edit_button);

        mUser.displayEditInfo(EditProfileActivity.this,editProfileName,editProfileBio,editProfileContact,editProfileHobby,editProfileBirthday,profile);


profile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
        GallaryUtils.openGallary(EditProfileActivity.this,RESULT_LOAD_IMAGE);
    }
});
        saveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String profileName = editProfileName.getText().toString();
                String profileBio = editProfileBio.getText().toString();
                String profileContact = editProfileContact.getText().toString();
                String profileHobby = editProfileHobby.getText().toString();
                String profileBirthday = editProfileBirthday.getText().toString();
                //String profilePicUrl = downloadUrl.toString();
                // update the account profile information in Firebase database.
                if (TextUtils.isEmpty(profileName) || TextUtils.isEmpty(profileBio) || TextUtils.isEmpty(profileContact)
                        || TextUtils.isEmpty(profileHobby) || TextUtils.isEmpty(profileBirthday)) {
                    displayMessageToast(EditProfileActivity.this, "All fields must be filled");
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Intent firebaseUserIntent = new Intent(EditProfileActivity.this, LoginActivity.class);
                    startActivity(firebaseUserIntent);
                    finish();
                } else {
                    String id = user.getUid();
                    String profileEmail = user.getEmail();


                    firebaseUserEntity = new FirebaseUserEntity(id, profileEmail, profileName, profileBio, profileContact, profileHobby, profileBirthday, profileHobby,"");
                    FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
                    firebaseDatabaseHelper.createUserInFirebaseDatabase(id, firebaseUserEntity);


                    editProfileName.setText("");
                    editProfileBio.setText("");
                    editProfileContact.setText("");
                    editProfileHobby.setText("");
                    editProfileBirthday.setText("");
                    EditProfileActivity.this.finish();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        this.finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE) {

                Uri filePath = data.getData();
                Picasso.with(EditProfileActivity.this).load(data.getData()).noPlaceholder().centerCrop().fit()
                        .into((ImageView) findViewById(R.id.btn_change_profile));
                mUser.uploadProfilePicture(EditProfileActivity.this,filePath,storageReference,databaseReference);
            }

        }
    }

}
