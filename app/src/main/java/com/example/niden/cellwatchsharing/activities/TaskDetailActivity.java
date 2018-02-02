package com.example.niden.cellwatchsharing.activities;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.adapters.ImageUploadLRecyclerAdapter;
import com.example.niden.cellwatchsharing.controllers.Zip;
import com.example.niden.cellwatchsharing.database.TaskEntityDatabase;
import com.example.niden.cellwatchsharing.utils.GallaryUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import net.lingala.zip4j.exception.ZipException;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import static com.example.niden.cellwatchsharing.adapters.RecyclerTechniciansAdapter.ID_KEY;

public class TaskDetailActivity extends AppCompatActivity {

    static final int RESULT_LOAD_IMAGE = 1;
    RecyclerView recyclerView;
    ImageView imageView, btnCamera;
    EditText etTaskName, etClass, etDescription, etAddress, etSuburb;
    String strTaskName, strDescription, strAddress, strClass, strSuburb;
    DatabaseReference mDataReference;
    TaskEntityDatabase taskEntityDatabase = new TaskEntityDatabase();
    private ImageUploadLRecyclerAdapter imageUploadLRecyclerAdapter;
    private StorageReference mStorage;
    public List<String> fileNameList;
    public List<String> fileDoneList;
    Zip mZip = new Zip();
    String zipFileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        setTitle(getString(R.string.toolbar_task_detail));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_second);
        setSupportActionBar(toolbar);
        mStorage = FirebaseStorage.getInstance().getReference();
        bindingViews();

        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();
        imageUploadLRecyclerAdapter = new ImageUploadLRecyclerAdapter(fileNameList, fileDoneList);

        //RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imageUploadLRecyclerAdapter);

        displayTaskDetail(etTaskName, etClass, etDescription, etAddress, etSuburb);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(actMain);
                TaskDetailActivity.this.finish();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GallaryUtils.openGallary(TaskDetailActivity.this, RESULT_LOAD_IMAGE);

            }
        });
    }

    private void bindingViews() {
        imageView = (ImageView) findViewById(R.id.gallaryImage);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnCamera = (ImageView) findViewById(R.id.button_camera);
        etTaskName = (EditText) findViewById(R.id.et_task_name);
        etClass = (EditText) findViewById(R.id.et_task_class);
        etDescription = (EditText) findViewById(R.id.et_task_desc);
        etAddress = (EditText) findViewById(R.id.et_task_address);
        etSuburb = (EditText) findViewById(R.id.et_task_suburb);
    }


    public void displayTaskDetail(final EditText etTaskName, final EditText etClass, final EditText etDescription, final EditText etAddress, final EditText etSuburb) {
        String taskKey = getIntent().getStringExtra(ID_KEY);
        mDataReference = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("tasks").child(taskKey);

        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    taskEntityDatabase = dataSnapshot.getValue(TaskEntityDatabase.class);
                    strTaskName = taskEntityDatabase.getTask_name();
                    strDescription = taskEntityDatabase.getTask_description();
                    strAddress = taskEntityDatabase.getTask_address();
                    strClass = taskEntityDatabase.getTask_class();
                    strSuburb = taskEntityDatabase.getTask_suburb();

                    etTaskName.setText(strTaskName);
                    etClass.setText(strClass);
                    etDescription.setText(strDescription);
                    etAddress.setText(strAddress);
                    etSuburb.setText(strSuburb);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
        Intent technicianActivityIntent = new Intent(this, MainActivity.class);
        startActivity(technicianActivityIntent);
    }


    // ACTIVITY RESULT
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int totalItemsSelected = data.getClipData().getItemCount();
                for (int i = 0; i < totalItemsSelected; i++) {
                    Uri fileUri = data.getClipData().getItemAt(i).getUri();
                    final String fileName = getFileName(fileUri);
                    fileNameList.add(fileName);
                    fileDoneList.add("uploading");
                    imageUploadLRecyclerAdapter.notifyDataSetChanged();

                    final StorageReference fileToUpload = mStorage.child("Gallery").child(fileName);
                    final int finalI = i;
                    fileToUpload.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            try {
                                mZip.zipper(fileNameList,fileName);
                            } catch (IOException | ZipException e) {
                                e.printStackTrace();
                            }
                            fileDoneList.remove(finalI);
                            fileDoneList.add(finalI, "done");
                            imageUploadLRecyclerAdapter.notifyDataSetChanged();
                        }
                    });

                }
            } else if (data.getData() != null) {
                Toast.makeText(TaskDetailActivity.this, "Selected Single File", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //GET FILE NAME
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

}

