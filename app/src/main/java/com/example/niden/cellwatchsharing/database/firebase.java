package com.example.niden.cellwatchsharing.database;

import android.widget.EditText;

import com.example.niden.cellwatchsharing.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.niden.cellwatchsharing.database.User.firebaseAuth;
import static com.example.niden.cellwatchsharing.fragments.AnnouncementFragment.database;


/**
 * Created by niden on 16-Nov-17.
 */

public class firebase extends MainActivity {

    public static FirebaseAuth firebaseAuth;
      //Create new post for announment
    public void insertPostToFirebase(EditText txMessage) {
        FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("tasks")
                .push()
                .setValue(new PostEntityDatabase(txMessage.getText().toString(),
                        FirebaseAuth.getInstance()
                                .getCurrentUser()
                                .getUid())
                );
    }
}
