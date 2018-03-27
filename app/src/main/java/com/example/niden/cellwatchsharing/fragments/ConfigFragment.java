package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.SignUpActivity;
import com.example.niden.cellwatchsharing.adapters.TechnicianRecyclerTaskAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * Created by niden on 16-Nov-17.
 */

public class ConfigFragment extends Fragment {

    public Activity activity = getActivity();
    View myView;
    EditText etTaskType;
    Button btnAddTaskType,imgViewAddTech;

    String strEtTaskType;
    TextInputLayout taskTypeWrapper;
    DatabaseReference mRef;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_config_layout, container, false);
        this.activity = getActivity();
        getActivity().setTitle(getString(R.string.toolbar_config));
        etTaskType = (EditText)myView.findViewById(R.id.config_frag_et_task_type);
        btnAddTaskType = (Button)myView.findViewById(R.id.config_frag_btn_add_type_task);
        taskTypeWrapper = (TextInputLayout)myView.findViewById(R.id.task_type_wrapper);
        imgViewAddTech= (Button) myView.findViewById(R.id.btn_add_new_tech) ;

        btnAddTaskType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEtTaskType = etTaskType.getText().toString();
                if (TextUtils.isEmpty(strEtTaskType)) {
                    taskTypeWrapper.setError("Please field in this field");
                }else{
                    DatabaseReference mRef= FirebaseDatabase.getInstance().getReference().child("taskType");
                    mRef.push().child("type").setValue(strEtTaskType);
                    Toast.makeText(activity, "Added", Toast.LENGTH_SHORT).show();
                    etTaskType.clearAnimation();
                    etTaskType.setText("");
                }
            }
        });


        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }




}
