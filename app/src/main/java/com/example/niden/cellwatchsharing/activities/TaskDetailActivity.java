package com.example.niden.cellwatchsharing.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.niden.cellwatchsharing.utils.GallaryUtils;
import com.google.firebase.storage.StorageReference;


import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.niden.cellwatchsharing.adapters.RecyclerTechniciansAdapter.ID_KEY;


public class TaskDetailActivity extends AppCompatActivity {

    static final int RESULT_LOAD_IMAGE = 1;
    RecyclerView recyclerImageUpload;
    ImageView btnCamera,imageViewZip;
    Button btnDone;
    private EditText etTaskName, etClass, etDescription, etAddress, etSuburb;
    private ImageUploadLRecyclerAdapter imageUploadLRecyclerAdapter;
    private StorageReference mStorage;
    public ArrayList<String> fileNameList;
    public ArrayList<String> fileDoneList;
    public ArrayList<String> filePathList;
    //Establish classes
    Zip mZip = new Zip();
    Task mTask = new Task();
    Gallary mGallery = new Gallary();
    public Uri zipUri;
    public  File file;
    File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),
            "CellWatchZip");
    String timeStampOfZipFile = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    String zippath = mediaStorageDir.getAbsolutePath() + "/" + timeStampOfZipFile + ".zip";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
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

    @Override
    protected void onStart() {
        super.onStart();
        btnCamera.setVisibility(View.VISIBLE);
        imageViewZip.setVisibility(View.INVISIBLE);
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
                mZip.putImagesToZip(zippath,filePathList);
                mZip.uploadZipFile(this,i,zippath,zipUri,fileDoneList,imageUploadLRecyclerAdapter,btnCamera,imageViewZip);



            }
        } else if (data.getData() != null) {
            Toast.makeText(TaskDetailActivity.this, "Selected Single File", Toast.LENGTH_SHORT).show();
        }
    }

}



