package com.example.niden.cellwatchsharing.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;



/**
 * Created by niden on 03-Feb-18.
 */

public class DataQuery {
    public static DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    public final static Query QUERY_TASK_TYPE = db.child("taskType");

    public final static Query QUERY_TECHNICIAN = db.child("users");

    public final static Query QUERY_ALL_TASK_INDIVIDUAL = db.child("users")
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
            .child("tasks");

    public final static Query QUERY_TASK_BY_TYPE = db.child("users")
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
            .child("tasks").orderByChild("task_type").equalTo("Install");

    public final static Query QUERY_ONLY_TECHNICIAN = db.child("users").orderByChild("userType").equalTo("technician");

    public final static Query QUERY_TECHNICIAN_BY_NAME = db.child("users");

}
