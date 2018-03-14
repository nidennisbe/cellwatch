package com.example.niden.cellwatchsharing.controllers;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.adapters.ImageUploadLRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by niden on 31-Jan-18.
 */

public class Zip {


    public void putImagesToZip(String zippath, ArrayList<String> allFiles) throws IOException, ZipException {
        if (new File(zippath).exists()) {
            new File(zippath).delete();
        }

        ZipFile zipFile = new ZipFile(zippath);
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        zipParameters.setPassword("Reset");

        if (allFiles.size() > 0) {
            for (String fileName : allFiles) {

                File file = new File(fileName);
                zipFile.addFile(file, zipParameters);

            }

        }
    }

    public void uploadZipFile(Context context, int i, String zippath, final ArrayList<String> fileDoneList,
                              final ImageUploadLRecyclerAdapter imageUploadLRecyclerAdapter, final ImageView btnCamera,
                              final ImageView imageViewZip, final String taskKey) {
        StorageReference mStorage = FirebaseStorage.getInstance().getReference();
        final StorageReference storageRef = mStorage.child("Gallery");
        final int j = i;
        Toast.makeText(context, zippath, Toast.LENGTH_SHORT).show();
        Uri zipUri = Uri.fromFile(new File(zippath));
        Log.d("file", zipUri.getPath());
        StorageReference readyToUpload = storageRef.child(zipUri.getLastPathSegment());
        UploadTask uploadTask = readyToUpload.putFile(zipUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("myStorage", "failure :(");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String readyToAddDownloadUrl= String.valueOf(downloadUrl);
                Map<String, Object> result = new HashMap<>();
                result.put("zipFileUrl",readyToAddDownloadUrl);
                DatabaseReference mDataReference = FirebaseDatabase.getInstance().getReference().child("tasks").child(taskKey);
                mDataReference.updateChildren(result);
                fileDoneList.remove(j);
                fileDoneList.add(j, "done");
                imageUploadLRecyclerAdapter.notifyDataSetChanged();
                btnCamera.setVisibility(View.INVISIBLE);
                imageViewZip.setVisibility(View.VISIBLE);
            }
        });
    }


}
