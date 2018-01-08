package com.example.niden.cellwatchsharing.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.niden.cellwatchsharing.R;

/**
 * Created by niden on 16-Nov-17.
 */

public class TaskFragment extends Fragment {
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_task_layout,container,false);
        return myView;

    }
}
