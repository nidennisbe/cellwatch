package com.example.niden.cellwatchsharing.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.classes.User;
import com.example.niden.cellwatchsharing.fragments.TaskFragment;
import com.example.niden.cellwatchsharing.utils.DialogsUtils;
import com.example.niden.cellwatchsharing.fragments.CreateTaskFragment;
import com.example.niden.cellwatchsharing.fragments.TechniciansFragment;
import com.example.niden.cellwatchsharing.fragments.GallaryFragment;
import com.example.niden.cellwatchsharing.fragments.ProfileFragment;
import com.example.niden.cellwatchsharing.utils.IntentUtils;
import com.example.niden.cellwatchsharing.utils.ToastUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import static com.example.niden.cellwatchsharing.classes.User.firebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public TextView displayEmail;
    AlertDialog.Builder myAlertDialog;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 23;
    private static final int SELECT_PICTURE = 100;
    public static Activity activity;
    User mUser = new User();
    NavigationView navigationView;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        // Inflate the menu; this adds items to the action bar if it is present.
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
// this listener will be called when there is change in firebase user session
            FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                }
            };
            ToastUtils.displayMessageToast(MainActivity.this,"Logout Successfully");
            // user auth state is changed - user is null
            // launch login activity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }// End of SideNavigation




    public void openGallary(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }//End of OpenGallary

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            myAlertDialog= DialogsUtils.showAlertDialog(MainActivity.this,"Quit app","Do you want to exit now?");
        }


    }//End of BackButtonPressed




    public void hideItem(MainActivity mainActivity)
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_anouncement).setVisible(false);
        nav_Menu.findItem(R.id.nav_task).setVisible(false);
    }


}
