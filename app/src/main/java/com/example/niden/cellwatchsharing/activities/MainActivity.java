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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;


import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.FirebaseUserEntity;
import com.example.niden.cellwatchsharing.fragments.TaskFragment;
import com.example.niden.cellwatchsharing.utils.DialogsUtils;
import com.example.niden.cellwatchsharing.fragments.CreateTaskFragment;
import com.example.niden.cellwatchsharing.fragments.TechniciansFragment;
import com.example.niden.cellwatchsharing.fragments.GallaryFragment;
import com.example.niden.cellwatchsharing.fragments.ProfileFragment;
import com.example.niden.cellwatchsharing.utils.ToastUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.niden.cellwatchsharing.database.DataQuery.QUERY_TECHNICIAN;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 23;
    public static final String ADMIN = "admin";
    public static final String TECHNICIAN = "technician";
    public static Activity activity;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    AlertDialog.Builder myAlertDialog;
    String TAG ="11";
    private FirebaseAuth.AuthStateListener mAuthListener;
    public  FirebaseAuth firebaseAuth;
    DatabaseReference scoresRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        firebaseAuth=FirebaseAuth.getInstance();
        checkUserType();
        QUERY_TECHNICIAN.keepSynced(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager fragmentManager =getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new ProfileFragment()).commit();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
            FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                }
            };
            ToastUtils.displayMessageToast(activity,"Logout Successfully");
            // account auth state is changed - account is null
            // launch login activity
            startActivity(new Intent(activity, LoginActivity.class));
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
            myAlertDialog= DialogsUtils.showAlertDialog(activity,getString(R.string.exit_app_dialog_title),getString(R.string.exit_app_desc));
        }
    }//End of BackButtonPressed




    public void adminNavItem()
    {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_task).setVisible(false);
        nav_Menu.findItem(R.id.nav_profile).setVisible(false);
    }

    public void technicianNavItem()
    {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_circles).setVisible(false);
        nav_Menu.findItem(R.id.nav_anouncement).setVisible(false);
    }

    private void checkUserType() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) { // UserProfile is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + firebaseUser.getUid());
                    FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                FirebaseUserEntity user = dataSnapshot.getValue(FirebaseUserEntity.class);
                                String type = user.getUser_type();
                                switch (type) {
                                    case TECHNICIAN:
                                        technicianNavItem();
                                        Toast.makeText(activity, "TECHNICIAN VIEW", Toast.LENGTH_SHORT).show();
                                        break;
                                    case ADMIN:
                                        adminNavItem();
                                        fragmentManager =getFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.content_frame,new TechniciansFragment()).commit();
                                        Toast.makeText(activity, "ADMIN VIEW", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else { // UserProfile is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Activity Lifecycles
    @Override
    protected void onStart() {
        super.onStart();
        QUERY_TECHNICIAN.keepSynced(true);
        firebaseAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(mAuthListener);
    }
    //End Activity Lifecycles
}
