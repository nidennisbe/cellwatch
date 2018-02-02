package com.example.niden.cellwatchsharing.helper;



import com.example.niden.cellwatchsharing.database.UserProfile;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDatabaseHelper {

    private static final String TAG = FirebaseDatabaseHelper.class.getSimpleName();
    private DatabaseReference databaseReference;


    public FirebaseDatabaseHelper(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void createUserInFirebaseDatabase(String userId, FirebaseUserEntity firebaseUserEntity){
        Map<String, Object> result = new HashMap<>();
        result.put(userId,firebaseUserEntity);
        databaseReference.child("users").updateChildren(result);
    }

}
