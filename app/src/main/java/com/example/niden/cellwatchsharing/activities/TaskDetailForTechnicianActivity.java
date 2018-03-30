package com.example.niden.cellwatchsharing.activities;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.adapters.ImageUploadLRecyclerAdapter;
import com.example.niden.cellwatchsharing.controllers.Gallary;
import com.example.niden.cellwatchsharing.controllers.Task;
import com.example.niden.cellwatchsharing.controllers.Zip;
import com.example.niden.cellwatchsharing.fragments.ProfileFragment;
import com.example.niden.cellwatchsharing.fragments.TaskFragment;
import com.example.niden.cellwatchsharing.fragments.TechniciansFragment;
import com.example.niden.cellwatchsharing.utils.DialogsUtils;
import com.example.niden.cellwatchsharing.utils.GallaryUtils;
import com.example.niden.cellwatchsharing.utils.InternetConnUtils;
import com.example.niden.cellwatchsharing.utils.KeyboardUtils;


import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.niden.cellwatchsharing.adapters.RecyclerTechniciansAdapter.ID_KEY;
import static com.example.niden.cellwatchsharing.utils.DialogsUtils.showAlertDialogDismiss;


public class TaskDetailForTechnicianActivity extends AppCompatActivity {


    static final int RESULT_LOAD_IMAGE = 30;
    RecyclerView recyclerImageUpload;
    ImageView btnCamera, imageViewZip;
    Button btnDone;
    private EditText etTaskName, etClass, etDescription, etAddress, etSuburb, etComment, etStartDate, etEndDate, etTaskType;
    private Spinner spinnerTaskStatus;
    private ImageUploadLRecyclerAdapter imageUploadLRecyclerAdapter;
    public ArrayList<String> fileNameList;
    public ArrayList<String> fileDoneList;
    public ArrayList<String> filePathList;
    List<String> spinnerArrayStatus = new ArrayList<>();
    //Establish classes
    String taskKey;
    Zip mZip = new Zip();
    Task mTask = new Task();
    Gallary mGallery = new Gallary();
    public Uri zipUri;
    //public  File file;
    public File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),
            "/CellWatchZip");
    String timeStampOfZipFile = new SimpleDateFormat("yyyyMMDD HH:mm:ss").format(Calendar.getInstance().getTime());
    public String zippath = mediaStorageDir.getAbsolutePath() + "/" + timeStampOfZipFile + ".zip";
    //File file = new File(path, "DemoPicture.jpg");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_for_technician);
        setTitle(getString(R.string.toolbar_task_detail));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_second);
        setSupportActionBar(toolbar);

        bindingViews();
        spinnerArrayStatus.add("Choose status for the task");
        spinnerArrayStatus.add("Pending");
        spinnerArrayStatus.add("Started");
        spinnerArrayStatus.add("Finished");
        spinnerArrayStatus.add("Uncompleted");

        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();
        filePathList = new ArrayList<>();
        imageUploadLRecyclerAdapter = new ImageUploadLRecyclerAdapter(fileNameList, fileDoneList);

        //RecyclerView
        recyclerImageUpload.setLayoutManager(new LinearLayoutManager(this));
        recyclerImageUpload.setHasFixedSize(true);
        recyclerImageUpload.setAdapter(imageUploadLRecyclerAdapter);
        taskKey = getIntent().getStringExtra(ID_KEY);
        mTask.displayTaskDetailForTechnician(taskKey, etTaskName, etClass, etDescription, etAddress, etSuburb, etComment, etStartDate, etEndDate, spinnerTaskStatus,etTaskType);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.item_spinner_status,
                spinnerArrayStatus);
        spinnerTaskStatus.setAdapter(adapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDetailForTechnicianActivity.this.finish();
                Intent actMain = new Intent(getApplicationContext(), MainActivity.class);
                String fragmnet = "nav_task";
                actMain.putExtra("fragment", fragmnet);
                startActivity(actMain);
            }
        });


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GallaryUtils.openGallary(TaskDetailForTechnicianActivity.this, RESULT_LOAD_IMAGE);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.updateTask(taskKey, etComment, spinnerTaskStatus);
                Intent actMain = new Intent(getApplicationContext(), MainActivity.class);
                TaskDetailForTechnicianActivity.this.finish();
                String fragmnet = "nav_task";
                actMain.putExtra("fragment", fragmnet);
                startActivity(actMain);
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
        etStartDate = (EditText) findViewById(R.id.et_start_date);
        etEndDate = (EditText) findViewById(R.id.et_end_date);
        imageViewZip = (ImageView) findViewById(R.id.imgview_zip);
        etComment = (EditText) findViewById(R.id.task_detail_technician_et_comment);
        spinnerTaskStatus = (Spinner) findViewById(R.id.spinner_task_status4);
        etTaskType= (EditText)findViewById(R.id.task_detail_et_task_type);
    }

    private void setup(Intent data) throws IOException, ZipException {
        if (data.getClipData() != null) {
            int totalItemsSelected = data.getClipData().getItemCount();
            for (int i = 0; i < totalItemsSelected; i++) {
                final Uri fileUri = data.getClipData().getItemAt(i).getUri();
                final String fileName = mGallery.getFileName(this, fileUri);
                final String filePath = mGallery.getRealPathFromURIGallery(this, fileUri);
                isExternalStorageWritable();
                isExternalStorageReadable();

                if (InternetConnUtils.isOnline(this)) {
                    fileNameList.add(fileName);
                    filePathList.add(filePath);
                    fileDoneList.add("uploading");
                    imageUploadLRecyclerAdapter.notifyDataSetChanged();
                    mZip.putImagesToZip(zippath, filePathList);
                    try {
                        mZip.uploadZipFile(this, i, zippath, fileDoneList, imageUploadLRecyclerAdapter, btnCamera, imageViewZip, taskKey);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showAlertDialogDismiss(this, getString(R.string.internet_connection), getString(R.string.upload_failed));
                }
            }
        } else if (data.getData() != null) {
            Toast.makeText(TaskDetailForTechnicianActivity.this, "Please choose at least TWO IMAGES by holding a few second on any images", Toast.LENGTH_LONG).show();
        }
    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


}



