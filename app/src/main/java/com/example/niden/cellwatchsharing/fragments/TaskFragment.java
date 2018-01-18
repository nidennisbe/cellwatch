package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.TaskContentActivity;
import com.example.niden.cellwatchsharing.adapters.ListTaskAdapter;
import com.example.niden.cellwatchsharing.adapters.adapter;
import com.example.niden.cellwatchsharing.database.PostEntityDatabase;
import com.example.niden.cellwatchsharing.database.TaskEntityDatabase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Console;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by niden on 16-Nov-17.
 */

public class TaskFragment extends Fragment {
    public FirebaseListAdapter<TaskEntityDatabase> mAdapter;
    View myView;
    public Activity refActivity=getActivity();
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_task_layout,container,false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.listTask);


        Query mRef;
         mRef = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("tasks");
        ListTaskAdapter mAdapter = new ListTaskAdapter(mRef, refActivity,R.layout.item_task );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(refActivity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.getItemAnimator().setChangeDuration(1000);
        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setMoveDuration(1000);


        recyclerView.setAdapter(mAdapter);

        return myView;
    }



}
