package com.example.niden.cellwatchsharing.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.TaskDetailActivity;
import com.example.niden.cellwatchsharing.database.TaskEntityDatabase;
import com.example.niden.cellwatchsharing.utils.TSConverterUtils;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by niden on 21-Nov-17.
 */

public class ListTaskAdapter extends FirebaseRecyclerAdapter<TaskEntityDatabase, ListTaskAdapter.Viewholder> {
    public Activity activity;

    public ListTaskAdapter(Query ref, Activity activity, int layout) {
        super(TaskEntityDatabase.class, layout, Viewholder.class, ref);
        this.activity = activity;

    }

    @Override
    protected void populateViewHolder(final Viewholder viewholder, final TaskEntityDatabase model, final int position) {
        long date = Long.parseLong(model.getTask_date());
        String strTimeStamp = TimeAgo.from(Long.parseLong(model.getTask_date()));
        viewholder.tvTaskName.setText(model.getTask_name());
        viewholder.tvDate.setText(TSConverterUtils.getDateFormat(date));
        viewholder.tvDateAgo.setText(strTimeStamp);
        viewholder.tvTime.setText(TSConverterUtils.getTimeFormat(date));
        viewholder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.zoom_in));
                Toast.makeText(activity, getItemCount()+"", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(activity, TaskDetailActivity.class);
                myIntent.putExtra("key",getRef(position).getKey());
                activity.startActivity(myIntent);

            }
        });
    }


    @Override
    public TaskEntityDatabase getItem(int position) {
        return super.getItem(position);
    }


    @Override
    public int getItemCount() {

        return mSnapshots.size();

    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView tvTaskName, tvDate, tvDateAgo,tvTime;
        LinearLayout linearLayout;

        public Viewholder(View itemView) {
            super(itemView);
            tvTaskName = (TextView) itemView.findViewById(R.id.task_name);
            tvDate = (TextView) itemView.findViewById(R.id.textViewTaskDate);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.card);
            tvDateAgo = (TextView) itemView.findViewById(R.id.txt_date_ago);
            tvTime = (TextView)itemView.findViewById(R.id.txt_time);

        }
    }


}
