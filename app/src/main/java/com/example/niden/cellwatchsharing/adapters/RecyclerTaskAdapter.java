package com.example.niden.cellwatchsharing.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.TaskDetailForAdminActivity;
import com.example.niden.cellwatchsharing.activities.TaskDetailForTechnicianActivity;
import com.example.niden.cellwatchsharing.database.TaskEntityDatabase;
import com.example.niden.cellwatchsharing.utils.TSConverterUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import static com.example.niden.cellwatchsharing.activities.TechnicianActivity.mUserKey;
import static com.example.niden.cellwatchsharing.utils.NotificationUltils.showAlertNotifcation;


/**
 * Created by niden on 21-Nov-17.
 */

public class RecyclerTaskAdapter extends FirebaseRecyclerAdapter<TaskEntityDatabase, RecyclerTaskAdapter.Viewholder> {
    public Activity activity;

    public RecyclerTaskAdapter(Query ref, Activity activity, int layout) {
        super(TaskEntityDatabase.class, layout, Viewholder.class, ref);
        this.activity = activity;
    }



    @Override
    protected void populateViewHolder(final Viewholder viewholder, final TaskEntityDatabase model, final int position) {


        final DatabaseReference mTaskRef = FirebaseDatabase.getInstance().getReference().child("tasks");
        mTaskRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                   showAlertNotifcation(activity);
                if (dataSnapshot.exists()){
                    long date = Long.parseLong(model.getTaskDate());
                    String strTimeStamp = TimeAgo.from(Long.parseLong(model.getTaskDate()));
                    viewholder.tvTaskName.setText(model.getTaskName());
                    viewholder.tvDate.setText(TSConverterUtils.getDateFormat(date));
                    viewholder.tvDateAgo.setText(strTimeStamp);
                    viewholder.tvTime.setText(TSConverterUtils.getTimeFormat(date));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(final DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        viewholder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder mAlertDialog= new AlertDialog.Builder(viewholder.linearLayout.getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert);
                mAlertDialog.setTitle("ATTENTION");
                mAlertDialog.setMessage("Are you sure you want to delete task "+model.getTaskName()+"?");
                mAlertDialog.setCancelable(false);
                mAlertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(activity, "successfully deleted task from technician: "+model.getTaskTechnicianName(), Toast.LENGTH_LONG).show();
                        mTaskRef.child(getRef(position).getKey()).removeValue();
                    }
                });
                mAlertDialog.setNegativeButton("Cancel",null);
                mAlertDialog.show();
                return true;
            }
        });

        viewholder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.zoom_in));
                Intent myIntent = new Intent(activity, TaskDetailForAdminActivity.class);
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
        TextView tvTaskName, tvDate, tvDateAgo,tvTime,emptyView;
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
