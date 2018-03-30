package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.MainActivity;
import com.example.niden.cellwatchsharing.adapters.SpinnerTaskTypeAdapter;
import com.example.niden.cellwatchsharing.adapters.SpinnerTechnicianAdapter;
import com.example.niden.cellwatchsharing.controllers.Task;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.database.TaskEntityDatabase;
import com.example.niden.cellwatchsharing.database.TaskTypeEntityDatabase;
import com.example.niden.cellwatchsharing.utils.DatePickerUtils;
import com.example.niden.cellwatchsharing.utils.KeyboardUtils;
import com.example.niden.cellwatchsharing.utils.ToastUtils;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.niden.cellwatchsharing.database.DataQuery.QUERY_ONLY_TECHNICIAN;
import static com.example.niden.cellwatchsharing.database.DataQuery.QUERY_TASK_TYPE;
import static com.example.niden.cellwatchsharing.utils.ShakerAnimationUtils.shakeError;


/**
 * Created by niden on 16-Nov-17.
 * Admin side for inserting task
 */

public class CreateTaskForTechnicianFragment extends Fragment {

    private Activity referenceActivity;
    private EditText txTaskName, txDescription, txAddress, txSuburb, txClass;
    private TextInputLayout taskNameWrapper, descriptionWrapper, addressWrapper, suburbWrapper, classWrapper;
    public static FirebaseDatabase database;
    private Task mTask = new Task();
    private Button mBtnStartDate, mBtnEndDate;
    private Spinner spinner;
    private View parentHolder;
    private LinearLayout parentLayout;
    int duration = Snackbar.LENGTH_LONG;
    SpinnerTaskTypeAdapter buildSpinnerTaskTypeAdapter;
    FragmentManager fragmentManager;
    Vibrator vibrator ;
    public final int timeOfVibrate = 400;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        referenceActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_create_task_for_technician_layout, container, false);
        setHasOptionsMenu(true);
        getActivity().setTitle("New Task");
        bindingViews();
        vibrator=(Vibrator) referenceActivity.getSystemService(referenceActivity.VIBRATOR_SERVICE);
        fragmentManager =getFragmentManager();
        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftKeyboard(v, referenceActivity);
            }
        });
        //SETUP SPINNER FOR SELECTING TYPE OF TASK
        buildSpinnerTaskTypeAdapter = new SpinnerTaskTypeAdapter(referenceActivity, TaskTypeEntityDatabase.class, R.layout.item_type_task, QUERY_TASK_TYPE);
        spinner.setAdapter(buildSpinnerTaskTypeAdapter);


        mBtnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerUtils.openDatePicker(referenceActivity, mBtnStartDate);
            }
        });
        mBtnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerUtils.openEndDatePicker(referenceActivity,mBtnEndDate);

            }
        });
        return parentHolder;
    }

    private void bindingViews() {
        mBtnStartDate = (Button) parentHolder.findViewById(R.id.btnStartDate);
        mBtnEndDate = (Button) parentHolder.findViewById(R.id.btnEndDate);
        txTaskName = (EditText) parentHolder.findViewById(R.id.editTextTaskName);
        txAddress = (EditText) parentHolder.findViewById(R.id.editTextAddress);
        txDescription = (EditText) parentHolder.findViewById(R.id.editTextDescription);
        txSuburb = (EditText) parentHolder.findViewById(R.id.editTextSuburb);
        txClass = (EditText) parentHolder.findViewById(R.id.editTextClass);
        parentLayout = (LinearLayout) parentHolder.findViewById(R.id.layout_parent);
        taskNameWrapper = (TextInputLayout) parentLayout.findViewById(R.id.task_name_wrapper);
        descriptionWrapper = (TextInputLayout) parentLayout.findViewById(R.id.description_wrapper);
        addressWrapper = (TextInputLayout) parentLayout.findViewById(R.id.address_wrapper);
        suburbWrapper = (TextInputLayout) parentLayout.findViewById(R.id.suburb_wrapper);
        classWrapper = (TextInputLayout) parentLayout.findViewById(R.id.class_wrapper);
        spinner = (Spinner) parentHolder.findViewById(R.id.tech_frag_spinnerType);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.create_task_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        fieldsValidation();

        return super.onOptionsItemSelected(item);
    }

    private void fieldsValidation() {
        String strTaskName = txTaskName.getText().toString();
        String strDescription = txDescription.getText().toString();
        String strAddress = txAddress.getText().toString();
        String strSuburb = txSuburb.getText().toString();
        String strClass = txClass.getText().toString();
        String strStartDate = mBtnStartDate.getText().toString();
        String strStartEnd = mBtnEndDate.getText().toString();
        //Validation
        if (TextUtils.isEmpty(strTaskName)) {
            taskNameWrapper.setError("Please field in task name");
            taskNameWrapper.startAnimation(shakeError());

        } else {
            taskNameWrapper.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(strDescription)) {
            descriptionWrapper.setError("Please field in description");
            descriptionWrapper.startAnimation(shakeError());
            vibrator.vibrate(timeOfVibrate);
        } else {
            descriptionWrapper.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(strAddress)) {
            addressWrapper.setError("Please field in address");
            addressWrapper.startAnimation(shakeError());
            vibrator.vibrate(timeOfVibrate);
        } else {
            addressWrapper.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(strSuburb)) {
            suburbWrapper.setError("Please field in suburb");
            suburbWrapper.startAnimation(shakeError());
            vibrator.vibrate(timeOfVibrate);
        } else {
            suburbWrapper.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(strClass)) {
            classWrapper.setError("Please field in class");
            classWrapper.startAnimation(shakeError());
            vibrator.vibrate(timeOfVibrate);
        } else {
            classWrapper.setErrorEnabled(false);
        }
        if (!TextUtils.isEmpty(strTaskName) && !TextUtils.isEmpty(strDescription) && !TextUtils.isEmpty(strAddress) && !TextUtils.isEmpty(strAddress) &&
                !TextUtils.isEmpty(strAddress) && !TextUtils.isEmpty(strSuburb) && !TextUtils.isEmpty(strClass)) {
            mTask.insertTaskForTheirOwn(txTaskName, txClass, txDescription, txAddress, txSuburb, spinner,mBtnStartDate,mBtnEndDate);
            ToastUtils.showSnackbar(parentLayout, getString(R.string.txt_submit_task), duration);
            txTaskName.setText("");
            txClass.setText("");
            txDescription.setText("");
            txAddress.setText("");
            fragmentManager.beginTransaction().replace(R.id.content_frame,new TaskFragment()).commit();
        }
    }


}
