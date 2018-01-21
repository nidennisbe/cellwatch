package com.example.niden.cellwatchsharing.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.fragments.TechniciansFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by niden on 18-Nov-17.
 */

public class FriendsAdapter extends FirebaseRecyclerAdapter<FirebaseUserEntity,FriendsAdapter.Viewholder>{


    public FriendsAdapter(Query ref, TechniciansFragment activity, int layout) {
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
