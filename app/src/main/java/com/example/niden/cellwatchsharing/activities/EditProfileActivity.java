package com.example.niden.cellwatchsharing.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.helper.FirebaseDatabaseHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.UUID;

import static com.example.niden.cellwatchsharing.activities.MainActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
import static com.example.niden.cellwatchsharing.helper.Helper.SELECT_PICTURE;


//sixth push
public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = EditProfileActivity.class.getSimpleName();


    //Firebase
    Uri downloadUrl;
    FirebaseUserEntity firebaseUserEntity;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    Uri filePath;
    StorageReference storageReference;
    private EditText editProfileName;
    private EditText editProfileBio;
    private EditText editProfileContact;
    private EditText editProfileHobby;
    private EditText editProfileBirthday;
    FirebaseUserEntity userEntity;
    ImageView profile;
    String task = "";

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setTitle(getString(R.string.toolbar_edit_profile_info));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        profile = (ImageView)findViewById(R.id.btn_change_profile);
        editProfileName = (EditText) findViewById(R.id.profile_name);
        editProfileBio = (EditText) findViewById(R.id.profile_bio);
        editProfileContact = (EditText) findViewById(R.id.profile_phone);
        editProfileHobby = (EditText) findViewById(R.id.profile_expiration_date);
        editProfileBirthday = (EditText) findViewById(R.id.profile_hobby);
        Button saveEditButton = (Button) findViewById(R.id.save_edit_button);

profile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
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
                // update the user profile information in Firebase database.
                if (TextUtils.isEmpty(profileName) || TextUtils.isEmpty(profileBio) || TextUtils.isEmpty(profileContact)
                        || TextUtils.isEmpty(profileHobby) || TextUtils.isEmpty(profileBirthday)) {
                    //.displayMessageToast(EditProfileActivity.this, "All fields must be filled");
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    Intent firebaseUserIntent = new Intent(EditProfileActivity.this, LoginActivity.class);
                    startActivity(firebaseUserIntent);
                    finish();
                } else {
                    String userId = user.getDisplayName();
                    String id = user.getUid();
                    String profileEmail = user.getEmail();

                    uploadImage();
                    userEntity = new FirebaseUserEntity(id, profileEmail, profileName, profileBio, profileContact, profileHobby, profileBirthday, profileHobby,downloadUrl+"");
                    FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
                    firebaseDatabaseHelper.createUserInFirebaseDatabase(id, userEntity);


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
            if (requestCode == SELECT_PICTURE) {

                Uri filePath = data.getData();
                Picasso.with(EditProfileActivity.this).load(data.getData()).noPlaceholder().centerCrop().fit()
                        .into((ImageView) findViewById(R.id.btn_change_profile));
            }

        }
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            downloadUrl = taskSnapshot.getDownloadUrl();
                          //  String DOWNLOAD_URL = downloadUrl.getPath();


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}
