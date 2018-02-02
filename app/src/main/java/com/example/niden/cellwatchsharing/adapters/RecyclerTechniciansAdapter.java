package com.example.niden.cellwatchsharing.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.TaskDetailActivity;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by niden on 18-Nov-17.
 */

public class RecyclerTechniciansAdapter extends FirebaseRecyclerAdapter<FirebaseUserEntity, RecyclerTechniciansAdapter.Viewholder> {
    public Activity activity;
    public final static String ID_KEY="key";


    public RecyclerTechniciansAdapter(Query ref, Activity activity, int layout) {
        super(FirebaseUserEntity.class, layout, RecyclerTechniciansAdapter.Viewholder.class, ref);
        this.activity = activity;
    }


    @Override
    protected void populateViewHolder(Viewholder viewHolder, FirebaseUserEntity model, final int position) {
        viewHolder.name.setText(model.getName());
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(activity, TaskDetailActivity.class);
                myIntent.putExtra(ID_KEY, getRef(position).getKey());
                activity.startActivity(myIntent);
            }
        });
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        LinearLayout card;
        ListView listView;

        public Viewholder(View view) {
            super(view);
            //          imageView = (ImageView) view.findViewById(R.id.imageView);
            name = (TextView) view.findViewById(R.id.txt_name);
            card = (LinearLayout) view.findViewById(R.id.item_click);
            listView = (ListView) view.findViewById(R.id.list_technician);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public FirebaseUserEntity getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public DatabaseReference getRef(int position) {
        return super.getRef(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
