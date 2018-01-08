package com.example.niden.cellwatchsharing.helper;



import com.example.niden.cellwatchsharing.activities.UserProfile;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.database.UserEntityDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
        Map<String, FirebaseUserEntity> user = new HashMap<>();
        user.put(userId, firebaseUserEntity);

        databaseReference.child("users").child(userId).setValue(user);
    }


    private List<UserProfile> adapterSourceData(DataSnapshot dataSnapshot, String uId){
        List<UserProfile> allUserData = new ArrayList<UserProfile>();
        if(dataSnapshot.getKey().equals(uId)){
            FirebaseUserEntity userInformation = dataSnapshot.getValue(FirebaseUserEntity.class);
            allUserData.add(new UserProfile(Helper.NAME, userInformation.getName()));
            allUserData.add(new UserProfile(Helper.BIO, userInformation.getBio()));
            allUserData.add(new UserProfile(Helper.EMAIL, userInformation.getEmail()));
            allUserData.add(new UserProfile(Helper.BIRTHDAY, userInformation.getBirthday()));
            allUserData.add(new UserProfile(Helper.CONTACT, userInformation.getPhone()));
            allUserData.add(new UserProfile(Helper.HOBBY_INTEREST, userInformation.getHobby()));
            allUserData.add(new UserProfile(Helper.TASK, userInformation.getTask()));
        }
        return allUserData;
    }
}
