package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.adapters.RecyclerTaskAdapter;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static com.example.niden.cellwatchsharing.database.DataQuery.QUERY_ALL_TASK_INDIVIDUAL;

/**
 * Created by niden on 16-Nov-17.
 */

public class TaskFragment extends Fragment {
    public RecyclerTaskAdapter mAdapter;
    public Activity activity = getActivity();
    View myView;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_task_layout, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.listTask);
        this.activity = getActivity();
        getActivity().setTitle(getString(R.string.toobar_tasks));


        mAdapter = new RecyclerTaskAdapter(QUERY_ALL_TASK_INDIVIDUAL, activity, R.layout.item_task);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new SlideInDownAnimator(new OvershootInterpolator(1f)));
        recyclerView.getItemAnimator().setChangeDuration(1000);
        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.setAdapter(mAdapter);
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        return myView;
    }


}
