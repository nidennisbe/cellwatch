package com.example.niden.cellwatchsharing.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.adapters.ImageUploadLRecyclerAdapter;
import com.example.niden.cellwatchsharing.controllers.Gallary;
import com.example.niden.cellwatchsharing.controllers.Task;
import com.example.niden.cellwatchsharing.controllers.Zip;

import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.niden.cellwatchsharing.adapters.RecyclerTechniciansAdapter.ID_KEY;


public class TaskDetailForAdminActivity extends AppCompatActivity {

    static final int RESULT_LOAD_IMAGE = 1;
    RecyclerView recyclerImageUpload;
    ImageView btnCamera,imageViewZip;
    Button btnDone;
    private EditText etTaskName, etClass, etDescription, etAddress, etSuburb, etComment;
    private ImageUploadLRecyclerAdapter imageUploadLRecyclerAdapter;
    public ArrayList<String> fileNameList;
    public ArrayList<String> fileDoneList;
    public ArrayList<String> filePathList;
    //Establish classes
    String taskKey;
    Zip mZip = new Zip();
    Task mTask = new Task();
    Gallary mGallery = new Gallary();
    public Uri zipUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_for_admin);
        setTitle(getString(R.string.toolbar_task_detail));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_second);
        setSupportActionBar(toolbar);

        bindingViews();

        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();
        filePathList = new ArrayList<>();
        imageUploadLRecyclerAdapter = new ImageUploadLRecyclerAdapter(fileNameList, fileDoneList);

        //RecyclerView
        recyclerImageUpload.setLayoutManager(new LinearLayoutManager(this));
        recyclerImageUpload.setHasFixedSize(true);
        recyclerImageUpload.setAdapter(imageUploadLRecyclerAdapter);
        taskKey = getIntent().getStringExtra(ID_KEY);
        mTask.displayTaskDetailForAdmin(taskKey, etTaskName, etClass, etDescription, etAddress, etSuburb,etComment);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(actMain);
                TaskDetailForAdminActivity.this.finish();
            }
        });

        imageViewZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            try {
                setup(data);
            } catch (IOException | ZipException e) {
                e.printStackTrace();
            }
        }
    }

    private void setup(Intent data) throws IOException, ZipException {
        if (data.getClipData() != null) {
            int totalItemsSelected = data.getClipData().getItemCount();
            for (int i = 0; i < totalItemsSelected; i++) {
                final Uri fileUri = data.getClipData().getItemAt(i).getUri();
                final String fileName = mGallery.getFileName(this,fileUri);
                final String filePath = mGallery.getRealPathFromURIGallery(this,fileUri);
                fileNameList.add(fileName);
                filePathList.add(filePath);
                fileDoneList.add("uploading");
                imageUploadLRecyclerAdapter.notifyDataSetChanged();
            }
        } else if (data.getData() != null) {
            Toast.makeText(TaskDetailForAdminActivity.this, "Selected Single File", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindingViews() {
        btnDone = (Button) findViewById(R.id.btn_done_task_detail);
        recyclerImageUpload = (RecyclerView) findViewById(R.id.recycler_view);
        btnCamera = (ImageView) findViewById(R.id.button_camera);
        etTaskName = (EditText) findViewById(R.id.et_task_name);
        etClass = (EditText) findViewById(R.id.et_task_class);
        etDescription = (EditText) findViewById(R.id.et_task_desc);
        etAddress = (EditText) findViewById(R.id.et_task_address);
        etSuburb = (EditText) findViewById(R.id.et_task_suburb);
        imageViewZip = (ImageView)findViewById(R.id.imgview_zip);
        etComment = (EditText)findViewById(R.id.task_detail_admin_et_comment);
    }

}



