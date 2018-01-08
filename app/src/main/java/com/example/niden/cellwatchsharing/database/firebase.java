package com.example.niden.cellwatchsharing.database;

import android.widget.EditText;

import com.example.niden.cellwatchsharing.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.niden.cellwatchsharing.fragments.AnnouncementFragment.database;


/**
 * Created by niden on 16-Nov-17.
 */

public class firebase extends MainActivity {


      //Create new post for announment
    public void insertPostToFirebase(EditText txMessage) {
        FirebaseDatabase.getInstance()
                .getReference().child("public_chat")
                .push()
                .setValue(new PostEntityDatabase(txMessage.getText().toString(),
                        FirebaseAuth.getInstance()
                                .getCurrentUser()
                                .getEmail())
                );
    }
}
