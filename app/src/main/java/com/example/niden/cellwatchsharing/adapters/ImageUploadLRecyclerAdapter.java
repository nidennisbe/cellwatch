package com.example.niden.cellwatchsharing.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;

import java.util.List;

/**
 * Created by akshayejh on 19/12/17.
 */

public class ImageUploadLRecyclerAdapter extends RecyclerView.Adapter<ImageUploadLRecyclerAdapter.ViewHolder> {


    private List<String> fileNameList;
    private List<String> fileDoneList;

    public ImageUploadLRecyclerAdapter(List<String> fileNameList, List<String> fileDoneList) {

        this.fileDoneList = fileDoneList;
        this.fileNameList = fileNameList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String fileName = fileNameList.get(position);
        holder.fileNameView.setText(fileName);

        String fileDone = fileDoneList.get(position);

        if (fileDone.equals("uploading")) {

            holder.fileDoneView.setImageResource(R.mipmap.progress);
        } else {
            holder.fileDoneView.setImageResource(R.mipmap.checked);
        }

    }

    @Override
    public int getItemCount() {
        return fileNameList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView fileNameView;
        ImageView fileDoneView;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            fileNameView = (TextView) mView.findViewById(R.id.upload_filename);
            fileDoneView = (ImageView) mView.findViewById(R.id.upload_loading);
        }

    }
}
