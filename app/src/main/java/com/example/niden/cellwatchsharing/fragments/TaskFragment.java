package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.TaskContentActivity;
import com.example.niden.cellwatchsharing.database.PostEntityDatabase;
import com.example.niden.cellwatchsharing.database.TaskEntityDatabase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

/**
 * Created by niden on 16-Nov-17.
 */

public class TaskFragment extends Fragment {
    private FirebaseListAdapter<TaskEntityDatabase> mAdapter;
    private ListView listOfTasks;
    View myView;
    private Activity refActivity=getActivity();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_task_layout,container,false);
        listOfTasks = (ListView) myView.findViewById(R.id.list_of_tasks);
        displayListOfTask();

//Set action on click of listview
        listOfTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent myIntent = new Intent(getActivity(), TaskContentActivity.class);
                getActivity().startActivity(myIntent);

                    Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();

            }
        });
        return myView;
    }



    public void displayListOfTask() {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("tasks");
        mAdapter = new FirebaseListAdapter<TaskEntityDatabase>(getActivity(), TaskEntityDatabase.class,
                R.layout.item_task, mRef) {
            @Override
            protected void populateView(View v, TaskEntityDatabase model, int position) {
                TextView tvTaskName = (TextView) v.findViewById(R.id.task_name);
                TextView tvDate = (TextView)v.findViewById(R.id.textViewTaskDate);
                //setText
                tvTaskName.setText(model.getTask_name());
                tvDate.setText(model.getTask_date());
            }

        };
        listOfTasks.setAdapter(mAdapter);

    }
}
