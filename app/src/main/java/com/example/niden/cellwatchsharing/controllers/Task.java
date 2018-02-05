package com.example.niden.cellwatchsharing.controllers;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.activities.MainActivity;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.database.TaskEntityDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.StringTokenizer;


/**
 * Created by niden on 16-Nov-17.
 */

public class Task  {
    private final String DIR_USER="users";
    private final String DIR_TASK="tasks";
    private String currentDateTimeString = String.valueOf(System.currentTimeMillis());
    public String name;
    public  String id;
    public String eachUserID;
      //Insert new task

    public void insertTask(EditText txTaskName, EditText txClass, EditText txAddress, EditText txDescription, EditText txSuburb, Spinner spinner, Spinner spinnerTech) {
        String strTaskName=txTaskName.getText().toString();
        String strClass =txClass.getText().toString();
        String strAdress = txAddress.getText().toString();
        String strDesc = txAddress.getText().toString();
        String strSuburb=txSuburb.getText().toString();
        String strSpinnerType = spinner.getSelectedItem().toString();
        String strSpinnerTech = spinnerTech.getSelectedItem().toString();
        FirebaseUserEntity  data = (FirebaseUserEntity)spinnerTech.getSelectedItem();
        eachUserID= data.getId();
        StringTokenizer tokens = new StringTokenizer(strSpinnerTech, ":");
        name = tokens.nextToken();
        id = tokens.nextToken();
        FirebaseDatabase.getInstance()
                .getReference()
                .child(DIR_USER)
                .child(eachUserID)
                .child(DIR_TASK)
                .push()
                .setValue(new TaskEntityDatabase(strTaskName,strClass, strAdress, strDesc,strSuburb
                        ,currentDateTimeString, strSpinnerType, strSpinnerTech));
        txTaskName.setText("");
        txAddress.setText("");
        txDescription.setText("");
        txSuburb.setText("");
        txClass.setText("");
    }

    public void displayTaskDetail(String taskKey, final EditText etTaskName, final EditText etClass, final EditText etDescription, final EditText etAddress, final EditText etSuburb ){
       DatabaseReference mDataReference = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("tasks").child(taskKey);

        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    TaskEntityDatabase taskEntityDatabase = dataSnapshot.getValue(TaskEntityDatabase.class);
                    String strTaskName = taskEntityDatabase.getTask_name();
                    String strDescription = taskEntityDatabase.getTask_description();
                    String strAddress = taskEntityDatabase.getTask_address();
                    String strClass = taskEntityDatabase.getTask_class();
                    String strSuburb = taskEntityDatabase.getTask_suburb();

                    etTaskName.setText(strTaskName);
                    etClass.setText(strClass);
                    etDescription.setText(strDescription);
                    etAddress.setText(strAddress);
                    etSuburb.setText(strSuburb);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }



}
