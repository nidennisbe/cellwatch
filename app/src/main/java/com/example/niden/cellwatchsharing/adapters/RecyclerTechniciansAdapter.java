package com.example.niden.cellwatchsharing.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.TechnicianActivity;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
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

public class RecyclerTechniciansAdapter extends FirebaseRecyclerAdapter<FirebaseUserEntity, RecyclerTechniciansAdapter.Viewholder> {
    public Activity activity;
    public final static String ID_KEY = "key";



    public RecyclerTechniciansAdapter(Query ref, Activity activity, int layout) {
        super(FirebaseUserEntity.class, layout, RecyclerTechniciansAdapter.Viewholder.class, ref);
        this.activity = activity;
    }


    @Override
    protected void populateViewHolder(final Viewholder viewHolder, final FirebaseUserEntity model, final int position) {
        DatabaseReference mTechRef = FirebaseDatabase.getInstance().getReference().child("users");
        mTechRef.child(getRef(position).getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    viewHolder.name_user.setText(model.getName());
                    viewHolder.textTypeUser.setText(model.getUser_type());
                    String url = model.getProfileUrl();
                    if (url.isEmpty()) {
                        viewHolder.profile_user.setImageResource(R.drawable.ic_user_blue);
                    } else {
                        Picasso.with(activity).load(url)
                                .resize(110, 110).centerCrop()
                                .into(viewHolder.profile_user);
                    }
                    Boolean userOnline= model.getOnline();
                    String strOnline = String.valueOf(userOnline);
                  //Check online users
                    if (strOnline.equals("true")){
                        viewHolder.onlineIcon.setImageResource(R.drawable.ic_online);
                    }else{
                        viewHolder.onlineIcon.setImageResource(R.drawable.ic_offline);
                    }
                    viewHolder.card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.zoom_in));
                            Intent myIntent = new Intent(activity, TechnicianActivity.class);
                            myIntent.putExtra(ID_KEY, getRef(position).getKey());
                            activity.startActivity(myIntent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        ImageView profile_user, onlineIcon;
        TextView name_user,textTypeUser;
        LinearLayout card;

        public Viewholder(View view) {
            super(view);
            name_user = (TextView) view.findViewById(R.id.txt_name);
            profile_user = (ImageView) view.findViewById(R.id.technician_profile);
            card = (LinearLayout) view.findViewById(R.id.item_click);
            textTypeUser= (TextView)view.findViewById(R.id.txt_role);
            onlineIcon = (ImageView) view.findViewById(R.id.online_status_icon);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

}
