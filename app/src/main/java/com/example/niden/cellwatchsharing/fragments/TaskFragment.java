package com.example.niden.cellwatchsharing.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.PostEntityDatabase;
import com.example.niden.cellwatchsharing.database.TaskEntityDatabase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.github.marlonlom.utilities.timeago.TimeAgo;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_task_layout,container,false);
        listOfTasks = (ListView) myView.findViewById(R.id.list_of_tasks);
        displayListOfTechnician();
        return myView;


    }


    public void displayListOfTechnician() {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("technician");
        mAdapter = new FirebaseListAdapter<TaskEntityDatabase>(getActivity(), TaskEntityDatabase.class,
                R.layout.item_post, mRef) {
            @Override
            protected void populateView(View v, TaskEntityDatabase model, int position) {
                TextView messageText = (TextView) v.findViewById(R.id.message_text);


                //setText

            }

        };
        listOfTasks.setAdapter(mAdapter);
        //testing
    }
}
