package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.database.UserEntityDatabase;
import com.example.niden.cellwatchsharing.classes.firebase;
import com.example.niden.cellwatchsharing.utils.DatePickerUtils;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by niden on 16-Nov-17.
 * Admin side for inserting task
 */

public class CreateTaskFragment extends Fragment {

    private Activity referenceActivity;
    private EditText txTaskName,txDescription,txAddress,txSuburb,txClass;
    public static FirebaseDatabase database;
    private firebase mFirebase = new firebase();
    private Button mBtnStartDate,mBtnEndDate;
    private FirebaseListAdapter<UserEntityDatabase> mfirebaseListAdapter;
    Spinner spinner,dropDownTechnician;
    DatePickerDialog datePickerDialog;
    View parentHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        referenceActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_create_task_layout, container, false);
        getActivity().setTitle("New Task");

        dropDownTechnician = (Spinner)parentHolder.findViewById(R.id.spinnerTechnician);
        mBtnStartDate= (Button)parentHolder.findViewById(R.id.btnStartDate);
        mBtnEndDate = (Button)parentHolder.findViewById(R.id.btnEndDate);
        txTaskName = (EditText) parentHolder.findViewById(R.id.editTextTaskName);
        txAddress = (EditText) parentHolder.findViewById(R.id.editTextAddress);
        txDescription = (EditText) parentHolder.findViewById(R.id.editTextDescription);
        txSuburb = (EditText) parentHolder.findViewById(R.id.editTextSuburb);
        txClass = (EditText) parentHolder.findViewById(R.id.editTextClass);
        spinner = (Spinner)parentHolder.findViewById(R.id.spinnerType);
        final Button btnPost = (Button) parentHolder.findViewById(R.id.fragment_announcement_btn_post);


        DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("task_type");

        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(referenceActivity,String.class,android.R.layout.simple_list_item_1,mref) {
            @Override
            protected void populateView(View v, String model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model);
            }
        };
        spinner.setAdapter(firebaseListAdapter);



        final DatabaseReference mrefTechnician = FirebaseDatabase.getInstance().getReference().child("users");
        mfirebaseListAdapter= new FirebaseListAdapter<UserEntityDatabase>(referenceActivity, UserEntityDatabase.class, android.R.layout.simple_list_item_1, mrefTechnician) {
            @Override
            protected void populateView(final View v, final UserEntityDatabase model, int position) {
                mrefTechnician.child(mfirebaseListAdapter.getRef(position).getKey()).child("info").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       FirebaseUserEntity firebaseUserEntity = dataSnapshot.getValue(FirebaseUserEntity.class);
                        ((TextView) v.findViewById(android.R.id.text1)).setText(firebaseUserEntity.getName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };
        dropDownTechnician.setAdapter(mfirebaseListAdapter);



        mBtnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerUtils.openDatePicker(referenceActivity,datePickerDialog,mBtnStartDate,mBtnEndDate);
    }
        });

        mBtnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerUtils.openEndDatePicker(referenceActivity,datePickerDialog,mBtnStartDate,mBtnEndDate);
            }
        });



        btnPost.setEnabled(false);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebase.insertTaskToFirebase(txTaskName,txClass,txDescription,txAddress,txSuburb,spinner);
                txTaskName.setText("");
                txAddress.setText("");
                txDescription.setText("");
                txSuburb.setText("");
                txClass.setText("");
                FragmentManager fragmentManager =getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,new TaskFragment()).commit();
            }
        });




        //Listener for disable and enable button item_post depend on EditText
        txTaskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().trim().length() == 0) {
                    btnPost.setEnabled(false);
                    btnPost.setBackgroundResource(R.drawable.style_button_border_only);
                    btnPost.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    btnPost.setEnabled(true);
                    btnPost.setBackgroundResource(R.color.colorPrimary);
                    btnPost.setTextColor(getResources().getColor(R.color.colorTextLight));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return parentHolder;

    }


}
