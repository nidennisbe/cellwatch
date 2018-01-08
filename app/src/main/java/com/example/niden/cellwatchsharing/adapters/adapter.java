package com.example.niden.cellwatchsharing.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.PostEntityDatabase;
import com.example.niden.cellwatchsharing.fragments.CirclesFragment;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by niden on 16-Nov-17.
 */

public class adapter extends FirebaseListAdapter<PostEntityDatabase> {


    public adapter(Context context, ObservableSnapshotArray<PostEntityDatabase> dataSnapshots, int modelLayout) {
        super(context, dataSnapshots, modelLayout);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        return null;
    }

    @Override
    protected void populateView(View v, PostEntityDatabase model, int position) {
        PostEntityDatabase PostEntityDatabase = new PostEntityDatabase();

                TextView messageText = (TextView) v.findViewById(R.id.message_text);
                TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                TextView messageTime = (TextView) v.findViewById(R.id.message_time);

                // Set their text
               // displayEmail.setText(model.getMessageUser());
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
    }

}
