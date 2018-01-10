package com.example.niden.cellwatchsharing.database;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by niden on 25-Nov-17.
 */

public class FireBaseRetrieve {

    String strName,strBio,strUID;
    DatabaseReference mMessagesDatabaseReference;
    User user = new User();
    FirebaseUserEntity firebaseUserEntity = new FirebaseUserEntity();




    //Showing profile information
    public void displayProfileInfo(final TextView textViewName, final TextView textViewBio){
        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(user.getFirebaseAuth().getUid());
        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    FirebaseUserEntity userInfo = postSnapshot.getValue(FirebaseUserEntity.class);
                    strName=userInfo.getName();
                    strBio=userInfo.getBio();
                   // strContact=userInfo.getPhone();
                }
                // Log.d(null,uName);
                textViewName.setText(strName);
                textViewBio.setText(strBio);

                // textViewContact.setText(strContact);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
