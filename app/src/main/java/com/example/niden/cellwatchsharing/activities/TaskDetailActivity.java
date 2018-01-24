package com.example.niden.cellwatchsharing.activities;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.TaskEntityDatabase;
import com.example.niden.cellwatchsharing.classes.User;
import com.example.niden.cellwatchsharing.utils.KeyboardUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskDetailActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int PICK_IMAGES = 732;
    private String mCurrentPhotoPath;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ImageView imageView, btnCamera;
    EditText etTaskName, etClass, etDescription, etAddress, etSuburb;
    String strTaskName, strDescription, strAddress, strClass, strSuburb;
    DatabaseReference mMessagesDatabaseReference;
    User user = new User();
    TaskEntityDatabase taskEntityDatabase = new TaskEntityDatabase();
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        KeyboardUtils.hideSoftKeyboard(this);
        setTitle(getString(R.string.toolbar_task_detail));

        imageView = (ImageView) findViewById(R.id.gallaryImage);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnCamera = (ImageView) findViewById(R.id.button_camera);
        etTaskName = (EditText) findViewById(R.id.editTextTaskName);
        etClass = (EditText) findViewById(R.id.editTextClass);
        etDescription = (EditText) findViewById(R.id.editTextDescription);
        etAddress = (EditText) findViewById(R.id.editTextAddress);
        etSuburb = (EditText) findViewById(R.id.editTextSuburb);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(mLayoutManager);


//        mRecyclerView.setAdapter(mAdapter);


        displayTaskDetail(etTaskName, etClass, etDescription, etAddress, etSuburb);


//        gridView.setAdapter(new ImageAdapter(this));
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openCamera();
//                Toast.makeText(getApplicationContext(), "Login failed. Please check your email and password", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType(getString(R.string.image_type));
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGES);
            }
        });
    }


    public void displayTaskDetail(final EditText etTaskName, final EditText etClass, final EditText etDescription, final EditText etAddress, final EditText etSuburb) {
        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(user.getFirebaseAuth().getUid())
                .child("tasks")
        ;

        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    taskEntityDatabase = postSnapshot.getValue(TaskEntityDatabase.class);
                    strTaskName = taskEntityDatabase.getTask_name();
                    strDescription = taskEntityDatabase.getTask_description();
                    strAddress = taskEntityDatabase.getTask_address();
                    strClass = taskEntityDatabase.getTask_class();
                    strSuburb = taskEntityDatabase.getTask_suburb();

                }
                etTaskName.setText(strTaskName);
                etClass.setText(strClass);
                etDescription.setText(strDescription);
                etAddress.setText(strAddress);
                etSuburb.setText(strSuburb);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        this.finish();
        Intent technicianActivityIntent = new Intent(this, TechnicianActivity.class);
        startActivity(technicianActivityIntent);
    }

    public void openCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Save Image To Gallery
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE );
//        File f = new File(mCurrentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        this.sendBroadcast(mediaScanIntent);
//        // Add Image Path To List
//       itemsData.add(mCurrentPhotoPath);
        if (requestCode == PICK_IMAGES) {

            if (resultCode == RESULT_OK) {
                //data.getParcelableArrayExtra(name);
                //If Single image selected then it will fetch from Gallery
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();

                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }

                }

            }

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}

