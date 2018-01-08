package com.example.niden.cellwatchsharing.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.helper.FirebaseDatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = EditProfileActivity.class.getSimpleName();

    private EditText editProfileName;
    private EditText editProfileBio;
    private EditText editProfileContact;
    private EditText editProfileHobby;
    private EditText editProfileBirthday;
    String task="";

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setTitle("Edit Profile Information");

        editProfileName = (EditText)findViewById(R.id.profile_name);
        editProfileBio = (EditText)findViewById(R.id.profile_bio);
        editProfileContact = (EditText)findViewById(R.id.profile_phone);
        editProfileHobby = (EditText)findViewById(R.id.profile_hobby);
        editProfileBirthday = (EditText)findViewById(R.id.profile_birth);
        Button saveEditButton = (Button)findViewById(R.id.save_edit_button);


        saveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String profileName = editProfileName.getText().toString();
                String profileBio = editProfileBio.getText().toString();
                String profileContact = editProfileContact.getText().toString();
                String profileHobby = editProfileHobby.getText().toString();
                String profileBirthday = editProfileBirthday.getText().toString();

                // update the user profile information in Firebase database.
                if(TextUtils.isEmpty(profileName) || TextUtils.isEmpty(profileBio) || TextUtils.isEmpty(profileContact)
                        || TextUtils.isEmpty(profileHobby) || TextUtils.isEmpty(profileBirthday)){
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


                    FirebaseUserEntity userEntity = new FirebaseUserEntity(id,profileEmail, profileName, profileBio, profileContact,profileHobby,profileBirthday);
                    FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
                    firebaseDatabaseHelper.createUserInFirebaseDatabase(id, userEntity);


                    editProfileName.setText("");
                    editProfileBio.setText("");
                    editProfileContact.setText("");
                    editProfileHobby.setText("");
                    editProfileBirthday.setText("");
                }
            }
        });
    }

}
