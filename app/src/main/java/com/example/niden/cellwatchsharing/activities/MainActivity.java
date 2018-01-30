package com.example.niden.cellwatchsharing.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;


import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.fragments.TaskFragment;
import com.example.niden.cellwatchsharing.utils.DialogsUtils;
import com.example.niden.cellwatchsharing.fragments.CreateTaskFragment;
import com.example.niden.cellwatchsharing.fragments.TechniciansFragment;
import com.example.niden.cellwatchsharing.fragments.GallaryFragment;
import com.example.niden.cellwatchsharing.fragments.ProfileFragment;
import com.example.niden.cellwatchsharing.utils.ToastUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import static com.example.niden.cellwatchsharing.controllers.Account.firebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 23;
    private static final int SELECT_PICTURE = 100;
    public static Activity activity;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    AlertDialog.Builder myAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("users");
        scoresRef.keepSynced(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity = this;
        FragmentManager fragmentManager =getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new ProfileFragment()).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
         fragmentManager =getFragmentManager();
        if (id == R.id.nav_profile) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new ProfileFragment()).commit();
        } else if (id == R.id.nav_gallery) {
           fragmentManager.beginTransaction().replace(R.id.content_frame,new GallaryFragment()).commit();
        } else if (id == R.id.nav_circles) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new TechniciansFragment()).commit();
        } else if (id == R.id.nav_task) {
                fragmentManager.beginTransaction().replace(R.id.content_frame,new TaskFragment()).commit();
        } else if (id == R.id.nav_anouncement) {
           fragmentManager.beginTransaction().replace(R.id.content_frame,new CreateTaskFragment()).commit();
        } else if (id == R.id.nav_about) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new TechniciansFragment()).commit();
        }
        else if (id ==R.id.content_frame){
            fragmentManager.beginTransaction().replace(R.id.content_frame,new ProfileFragment()).commit();
        }
        else if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
// this listener will be called when there is change in Task account session
            FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                }
            };
            ToastUtils.displayMessageToast(MainActivity.this,"Logout Successfully");
            // account auth state is changed - account is null
            // launch login activity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            this.finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }// End of SideNavigation




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            myAlertDialog= DialogsUtils.showAlertDialog(MainActivity.this,"Quit app","Do you want to exit now?");
        }


    }//End of BackButtonPressed


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void hideItem(MainActivity mainActivity)
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_anouncement).setVisible(false);
        nav_Menu.findItem(R.id.nav_task).setVisible(false);
    }


}
