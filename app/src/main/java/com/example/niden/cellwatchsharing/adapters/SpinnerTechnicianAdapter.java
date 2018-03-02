package com.example.niden.cellwatchsharing.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by niden on 03-Feb-18.
 */

public class SpinnerTechnicianAdapter extends FirebaseListAdapter<FirebaseUserEntity> {


    public SpinnerTechnicianAdapter(Context context, Class<FirebaseUserEntity> modelClass, int modelLayout, Query query) {
        super(context, modelClass, modelLayout, query);
    }

    @Override
    protected void populateView(final View v, final FirebaseUserEntity model, final int position) {
        DatabaseReference mRefUser = FirebaseDatabase.getInstance().getReference().child("users");
        mRefUser.child(getRef(position).getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    TextView textView = (TextView) v.findViewById(R.id.item_s_name);
                    TextView txUid = (TextView) v.findViewById(R.id.item_s_uid);
                    ImageView imageView = (ImageView) v.findViewById(R.id.item_s_profile);
                    //To String

                    String resultUid = model.getId();
                    String resultName = String.valueOf(model.getName());
                    String imageUrl = model.getProfileUrl();
                    //Set Value to Views
                    txUid.setText(resultUid);
                    textView.setText(resultName);
                    if (imageUrl.isEmpty()) {
                        imageView.setImageResource(R.drawable.ic_user_blue);
                    } else {
                        Picasso.with(mContext).load(imageUrl)
                                .resize(110, 110).centerCrop()
                                .into(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
