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
import com.firebase.ui.database.FirebaseListAdapter;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by niden on 16-Nov-17.
 */

public class TaskFragment extends Fragment {
    private FirebaseListAdapter<PostEntityDatabase> mAdapter;
    private ListView listOfTasks;
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_task_layout,container,false);
        listOfTasks = (ListView) myView.findViewById(R.id.list_of_tasks);
        return myView;


    }


    public void displayChatMessages() {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("public_chat");
        mAdapter = new FirebaseListAdapter<PostEntityDatabase>(getActivity(), PostEntityDatabase.class,
                R.layout.item_post, mRef) {
            @Override
            protected void populateView(View v, PostEntityDatabase model, int position) {
                // Get references to the DialogsUtils of item_message.xmle.xml
                TextView messageText = (TextView) v.findViewById(R.id.message_text);
                TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                TextView messageTime = (TextView) v.findViewById(R.id.message_time);
                //setText
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                //Covert time to ago
                long timeInMillis = System.currentTimeMillis();
                String CovertMessageTime = TimeAgo.from(model.getMessageTime());
                messageTime.setText(CovertMessageTime);
                //listOfMessages.setStackFromBottom(true);
            }
        };
        listOfTasks.setAdapter(mAdapter);

    }
}
