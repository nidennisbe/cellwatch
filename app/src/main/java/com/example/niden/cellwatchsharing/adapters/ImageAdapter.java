package com.example.niden.cellwatchsharing.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.PhotoEntityDatabase;

/**
 * Created by niden on 16-Jan-18.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private PhotoEntityDatabase[] itemsData;

    public ImageAdapter(PhotoEntityDatabase[] itemsData) {
        this.itemsData = itemsData;
    }


    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_selection, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(Integer.parseInt(itemsData[position].getImageUrl()));
    }

    @Override
    public int getItemCount() {
        return itemsData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.gallaryImage);
        }
    }
}