package com.example.niden.cellwatchsharing.database;

import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by niden on 25-Nov-17.
 */

public class FireBaseRetrieve {

    String strName,strBio,strPhone,strHobby,strDateBirth;
    DatabaseReference mMessagesDatabaseReference;
    User user = new User();
    FirebaseUserEntity firebaseUserEntity = new FirebaseUserEntity();




    //Showing profile information
    public void displayProfileInfo(final TextView textViewName, final TextView textViewBio,final TextView textViewPhone,final TextView textViewHobby,final TextView textViewDateBirth){
        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("info");
        mMessagesDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                    firebaseUserEntity = dataSnapshot.getValue(FirebaseUserEntity.class);
                    strName=firebaseUserEntity.getName();
                    strBio=firebaseUserEntity.getBio();
                    strPhone=firebaseUserEntity.getPhone();
                    strHobby=firebaseUserEntity.getHobby();
                    strDateBirth=firebaseUserEntity.getBirthday();

                    textViewPhone.setText(strPhone);
                    textViewBio.setText(strBio);
                    textViewName.setText(strName);
                    textViewHobby.setText(strHobby);
                    textViewDateBirth.setText(strDateBirth);
//                Log.d("a",strBio);

                // textViewContact.setText(strContact);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
