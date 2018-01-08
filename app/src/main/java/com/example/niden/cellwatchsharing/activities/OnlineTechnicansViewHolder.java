package com.example.niden.cellwatchsharing.activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;

/**
 * Created by niden on 25-Nov-17.
 */

public class OnlineTechnicansViewHolder extends RecyclerView.ViewHolder {
    public TextView txtEmail;
    public OnlineTechnicansViewHolder(View itemView) {
        super(itemView);
        txtEmail = (TextView)itemView.findViewById(R.id.txt_email);
    }
}
