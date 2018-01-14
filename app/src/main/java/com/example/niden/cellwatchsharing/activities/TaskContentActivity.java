package com.example.niden.cellwatchsharing.activities;

import android.content.Intent;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.database.TaskEntityDatabase;
import com.example.niden.cellwatchsharing.database.User;
import com.example.niden.cellwatchsharing.fragments.TaskFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaskContentActivity extends AppCompatActivity {

    EditText etTaskName,etClass,etDescription,etAddress,etSuburb;
    String strTaskName,strDescription,strAddress,strClass,strSuburb;
    DatabaseReference mMessagesDatabaseReference;
    User user = new User();
    TaskEntityDatabase taskEntityDatabase = new TaskEntityDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_content);


        etTaskName = (EditText)findViewById(R.id.editTextTaskName);
        etClass = (EditText)findViewById(R.id.editTextClass);
        etDescription = (EditText)findViewById(R.id.editTextDescription);
        etAddress = (EditText)findViewById(R.id.editTextAddress);
        etSuburb = (EditText)findViewById(R.id.editTextSuburb);


        displayTaskDetail(etTaskName,etClass,etDescription,etAddress,etSuburb);





    }


    public void displayTaskDetail(final EditText etTaskName, final EditText etClass,final EditText etDescription,final EditText etAddress,final EditText etSuburb){
        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(user.getFirebaseAuth().getUid())
                .child("tasks")
        ;
        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    taskEntityDatabase = postSnapshot.getValue(TaskEntityDatabase.class);
                    strTaskName=taskEntityDatabase.getTask_name();
                    strDescription=taskEntityDatabase.getTask_description();
                    strAddress=taskEntityDatabase.getTask_address();
                    strClass=taskEntityDatabase.getTask_class();
                    strSuburb=taskEntityDatabase.getTask_suburb();

                }
                etTaskName.setText(strTaskName);
                etClass.setText(strClass);
                etDescription.setText(strDescription);
                etAddress.setText(strAddress);
                etSuburb.setText(strSuburb);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        this.finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    }

