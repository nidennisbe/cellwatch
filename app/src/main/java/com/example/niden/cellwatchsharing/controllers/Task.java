package com.example.niden.cellwatchsharing.controllers;



import android.content.Context;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.database.TaskEntityDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.StringTokenizer;

import static com.example.niden.cellwatchsharing.activities.TechnicianActivity.mUserKey;


/**
 * Created by niden on 16-Nov-17.
 */

public class Task  {
    private final String DIR_USER="users";
    private final String DIR_TASK="tasks";
    private String currentDateTimeString = String.valueOf(System.currentTimeMillis());
    public static String eachUserID;
    String name,uid;

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
        StringTokenizer tokens = new StringTokenizer(strSpinnerTech,"|");
        name = tokens.nextToken();
        uid = tokens.nextToken();


        FirebaseDatabase.getInstance()
                .getReference()
                .child(DIR_TASK)
                .push()
                .setValue(new TaskEntityDatabase(eachUserID,strTaskName,strClass, strAdress, strDesc,strSuburb
                        ,currentDateTimeString, strSpinnerType, name));
        txTaskName.setText("");
        txAddress.setText("");
        txDescription.setText("");
        txSuburb.setText("");
        txClass.setText("");
    }

//SHOW CONTENTS ON TASK DETAIL ACTIVITY
    public void displayTaskDetailForTechnician(String taskKey, final EditText etTaskName, final EditText etClass, final EditText etDescription, final EditText etAddress, final EditText etSuburb ){
       DatabaseReference mDataReference = FirebaseDatabase.getInstance().getReference().child("tasks");
        mDataReference.child(taskKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    TaskEntityDatabase taskEntityDatabase = dataSnapshot.getValue(TaskEntityDatabase.class);
                    String strTaskName = taskEntityDatabase.getTaskName();
                    String strDescription = taskEntityDatabase.getTaskDescription();
                    String strAddress = taskEntityDatabase.getTaskAddress();
                    String strClass = taskEntityDatabase.getTaskClass();
                    String strSuburb = taskEntityDatabase.getTaskSuburb();

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

    //SHOW CONTENTS ON TASK DETAIL ACTIVITY
    public void displayTaskDetailForAdmin(String taskKey, final EditText etTaskName, final EditText etClass, final EditText etDescription, final EditText etAddress, final EditText etSuburb ){
        DatabaseReference mDataReference = FirebaseDatabase.getInstance().getReference("tasks");
        mDataReference.child(taskKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    TaskEntityDatabase taskEntityDatabase = dataSnapshot.getValue(TaskEntityDatabase.class);
                    String strTaskName = taskEntityDatabase.getTaskName();
                    String strDescription = taskEntityDatabase.getTaskDescription();
                    String strAddress = taskEntityDatabase.getTaskAddress();
                    String strClass = taskEntityDatabase.getTaskClass();
                    String strSuburb = taskEntityDatabase.getTaskSuburb();

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
