package com.example.niden.cellwatchsharing.helper;



import com.example.niden.cellwatchsharing.activities.UserProfile;
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
        Map<String, Object> user = new HashMap<>();
        user.put("info", firebaseUserEntity);
        databaseReference.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .updateChildren(user);
    }


    private List<UserProfile> adapterSourceData(DataSnapshot dataSnapshot, String uId){
        List<UserProfile> allUserData = new ArrayList<UserProfile>();
        if(dataSnapshot.getKey().equals(uId)){
            FirebaseUserEntity firebaseUserEntity = dataSnapshot.getValue(FirebaseUserEntity.class);
            allUserData.add(new UserProfile(Helper.NAME, firebaseUserEntity.getName()));
            allUserData.add(new UserProfile(Helper.BIO, firebaseUserEntity.getBio()));
            allUserData.add(new UserProfile(Helper.EMAIL, firebaseUserEntity.getEmail()));
            allUserData.add(new UserProfile(Helper.CONTACT, firebaseUserEntity.getPhone()));
            allUserData.add(new UserProfile(Helper.HOBBY_INTEREST, firebaseUserEntity.getHobby()));
            allUserData.add(new UserProfile(Helper.EXPIRATION_DATE, firebaseUserEntity.getExpiration_date()));
            allUserData.add(new UserProfile(Helper.PROFILE_URL,firebaseUserEntity.getProfile_url()));
        }
        return allUserData;
    }
}
