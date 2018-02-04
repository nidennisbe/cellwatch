package com.example.niden.cellwatchsharing.activities;


import android.content.Intent;
import android.net.Uri;
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
import com.example.niden.cellwatchsharing.controllers.Gallary;
import com.example.niden.cellwatchsharing.controllers.Task;
import com.example.niden.cellwatchsharing.controllers.Zip;
import com.example.niden.cellwatchsharing.utils.GallaryUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;

import static com.example.niden.cellwatchsharing.adapters.RecyclerTechniciansAdapter.ID_KEY;

public class TaskDetailActivity extends AppCompatActivity {

    static final int RESULT_LOAD_IMAGE = 1;
    RecyclerView recyclerImageUpload;
    ImageView imageView, btnCamera;
    EditText etTaskName, etClass, etDescription, etAddress, etSuburb;
    private ImageUploadLRecyclerAdapter imageUploadLRecyclerAdapter;
    private StorageReference mStorage;
    public ArrayList<String> fileNameList;
    public ArrayList<String> fileDoneList;
    public ArrayList<String> filePathList;
    Zip mZip = new Zip();
    Task mTask = new Task();
    public String zipFileName;
    Gallary mGallery = new Gallary();


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
        filePathList = new ArrayList<>();
        imageUploadLRecyclerAdapter = new ImageUploadLRecyclerAdapter(fileNameList, fileDoneList);

        //RecyclerView
        recyclerImageUpload.setLayoutManager(new LinearLayoutManager(this));
        recyclerImageUpload.setHasFixedSize(true);
        recyclerImageUpload.setAdapter(imageUploadLRecyclerAdapter);
        String taskKey = getIntent().getStringExtra(ID_KEY);
        mTask.displayTaskDetail(taskKey, etTaskName, etClass, etDescription, etAddress, etSuburb);


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
                    final Uri fileUri = data.getClipData().getItemAt(i).getUri();
                    final String fileName = mGallery.getFileName(this,fileUri);
                    final String filePath = mGallery.getRealPathFromURIGallery(TaskDetailActivity.this,fileUri);
                    fileNameList.add(fileName);
                    filePathList.add(filePath);
                    fileDoneList.add("uploading");
                    imageUploadLRecyclerAdapter.notifyDataSetChanged();
                    mZip.putImagesToZip(filePathList,zipFileName);
                    final StorageReference fileToUpload = mStorage.child("Gallery").child(fileName);
                    final int j = i;

                    fileToUpload.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileDoneList.remove(j);
                            fileDoneList.add(j, "done");
                            imageUploadLRecyclerAdapter.notifyDataSetChanged();
                        }
                    });

                }
            } else if (data.getData() != null) {
                Toast.makeText(TaskDetailActivity.this, "Selected Single File", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void bindingViews() {
        imageView = (ImageView) findViewById(R.id.gallaryImage);
        recyclerImageUpload = (RecyclerView) findViewById(R.id.recycler_view);
        btnCamera = (ImageView) findViewById(R.id.button_camera);
        etTaskName = (EditText) findViewById(R.id.et_task_name);
        etClass = (EditText) findViewById(R.id.et_task_class);
        etDescription = (EditText) findViewById(R.id.et_task_desc);
        etAddress = (EditText) findViewById(R.id.et_task_address);
        etSuburb = (EditText) findViewById(R.id.et_task_suburb);
    }

}

