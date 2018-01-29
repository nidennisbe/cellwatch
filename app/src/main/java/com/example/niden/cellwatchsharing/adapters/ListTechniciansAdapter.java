package com.example.niden.cellwatchsharing.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.TechnicianActivity;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by niden on 18-Nov-17.
 */

public class ListTechniciansAdapter extends FirebaseRecyclerAdapter<FirebaseUserEntity,ListTechniciansAdapter.Viewholder>{
    public Activity activity;


    public ListTechniciansAdapter(Query ref, Activity activity, int layout) {
        super(FirebaseUserEntity.class, layout, ListTechniciansAdapter.Viewholder.class, ref);
        this.activity = activity;
    }



    @Override
    protected void populateViewHolder(Viewholder viewHolder, FirebaseUserEntity model, int position) {
        viewHolder.name.setText(model.getName());
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;

        public Viewholder(View view) {
            super(view);
//            imageView = (ImageView) view.findViewById(R.id.imageView);
             name = (TextView) view.findViewById(R.id.txt_name);



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
