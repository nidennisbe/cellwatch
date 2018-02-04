package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.adapters.RecyclerTaskAdapter;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static com.example.niden.cellwatchsharing.database.DataQuery.QUERY_ALL_TASK_INDIVIDUAL;
import static com.example.niden.cellwatchsharing.database.DataQuery.QUERY_TASK_BY_TYPE;

/**
 * Created by niden on 16-Nov-17.
 */

public class TaskFragment extends Fragment {
    public RecyclerTaskAdapter mAdapter;
    public Activity activity = getActivity();
    View myView;
    public static RecyclerView recyclerView;
    public static TextView emptyView;
    Query query = QUERY_ALL_TASK_INDIVIDUAL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_task_layout, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.listTask);
        this.activity = getActivity();
        getActivity().setTitle(getString(R.string.toobar_tasks));

        emptyView = (TextView) myView.findViewById(R.id.empty_view);
        TabLayout tabLayout = (TabLayout) myView.findViewById(R.id.tab);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    query = QUERY_ALL_TASK_INDIVIDUAL;

                } else if (tab.getPosition() == 1) {
                    query = QUERY_TASK_BY_TYPE;
                }
                setupRecyclerView();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mAdapter = new RecyclerTaskAdapter(query, activity, R.layout.item_task);
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
    }


}
