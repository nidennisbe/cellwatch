package com.example.niden.cellwatchsharing.database;

import android.widget.EditText;
import android.widget.Spinner;

import com.example.niden.cellwatchsharing.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;


/**
 * Created by niden on 16-Nov-17.
 */

public class firebase extends MainActivity {

    public static FirebaseAuth firebaseAuth;
      //Create new post for announment
      String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    public void insertTaskToFirebase(EditText txTaskName, EditText txClass, EditText txAddress, EditText txDescription, EditText txSuburb, Spinner spinner) {
        FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("tasks")
                .push()
                .setValue(new TaskEntityDatabase(txTaskName.getText().toString(),txClass.getText().toString(),txAddress.getText().toString(),txDescription.getText().toString(),
                        txSuburb.getText().toString(),currentDateTimeString,spinner.getSelectedItem().toString(),""

                ));
    }
}
