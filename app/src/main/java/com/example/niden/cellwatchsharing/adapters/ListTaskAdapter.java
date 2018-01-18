package com.example.niden.cellwatchsharing.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.TaskContentActivity;
import com.example.niden.cellwatchsharing.database.TaskEntityDatabase;
import com.example.niden.cellwatchsharing.fragments.TaskFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by niden on 21-Nov-17.
 */

public class ListTaskAdapter extends FirebaseRecyclerAdapter<TaskEntityDatabase,ListTaskAdapter.Viewholder>{
    public Activity activity;


    public ListTaskAdapter(Query ref, Activity activity, int layout) {
        super(TaskEntityDatabase.class, layout, Viewholder.class, ref);
        this.activity = activity;

    }

    @Override
    protected void populateViewHolder(Viewholder viewholder, TaskEntityDatabase model, int position) {
        viewholder.tvTaskName.setText(model.getTask_name());
        viewholder.tvDate.setText(model.getTask_date());
        viewholder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent myIntent = new Intent(activity, TaskContentActivity.class);
//                activity.startActivity(myIntent);
                Toast.makeText(activity, "Item is already existed in the Cart!! =>Press [\u002B] to ADD more or [\u2212] to DELETE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return super.getItemCount();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView tvTaskName, tvDate;
        LinearLayout linearLayout;

         public Viewholder(View itemView) {
            super(itemView);
             tvTaskName = (TextView) itemView.findViewById(R.id.task_name);
             tvDate = (TextView)itemView.findViewById(R.id.textViewTaskDate);
             linearLayout = (LinearLayout)itemView.findViewById(R.id.card);
        }
    }


}
