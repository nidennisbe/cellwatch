package com.example.niden.cellwatchsharing.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by niden on 03-Feb-18.
 */

public class DataQuery {
    public final static Query QUERY_TASK_TYPE = FirebaseDatabase.getInstance().getReference().child("task_type");
    public final static Query QUERY_TECHNICIAN = FirebaseDatabase.getInstance().getReference().child("users");
    public final static Query QUERY_ALL_TASK_INDIVIDUAL = FirebaseDatabase.getInstance().getReference().child("users")
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
            .child("tasks");
}
