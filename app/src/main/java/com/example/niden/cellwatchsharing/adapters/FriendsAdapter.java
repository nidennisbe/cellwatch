package com.example.niden.cellwatchsharing.adapters;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.fragments.CirclesFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by niden on 18-Nov-17.
 */

public class FriendsAdapter extends FirebaseRecyclerAdapter<FirebaseUserEntity,FriendsAdapter.Viewholder>{


    public FriendsAdapter(Query ref, CirclesFragment activity, int layout) {
        super(FirebaseUserEntity.class, layout, FriendsAdapter.Viewholder.class, ref);
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
//            name = (TextView) view.findViewById(R.id.txt_name);



        }
    }
}
