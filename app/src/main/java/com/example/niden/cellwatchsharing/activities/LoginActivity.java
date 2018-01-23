package com.example.niden.cellwatchsharing.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.classes.User;
import com.example.niden.cellwatchsharing.database.firebase;
import com.example.niden.cellwatchsharing.utils.DialogsUtils;
import com.example.niden.cellwatchsharing.utils.KeyboardUtils;
import com.example.niden.cellwatchsharing.utils.ToastUtils;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by niden on 16-Nov-17.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText inputEmail, inputPassword;
    private FirebaseAuth mAuth;
    Button btnSignup, btnLogin, btnReset;
    firebase mFirebase = new firebase();
    ProgressDialog myDialog;
    User mUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUser.getFirebaseAuth();
        mUser.checkUserLogin(LoginActivity.this);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftKeyboard(LoginActivity.this);
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                //Validation
                if (TextUtils.isEmpty(email)) {
                    ToastUtils.displayMessageToast(LoginActivity.this,"You must enter you Email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.displayMessageToast(LoginActivity.this,"You must enter your password");
                    return;
                }
                //End of Validation
                if (isOnline()) {
                    //do whatever you want to do
                    myDialog = DialogsUtils.showProgressDialog(LoginActivity.this, "Signing in...");
                    mUser.loginAUser(LoginActivity.this, email, password, myDialog);
                }else
                {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();

                        alertDialog.setTitle("Internet Connection");
                        alertDialog.setMessage("Please check your internet connectivity and try again");
                        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        alertDialog.show();
                    }
                    catch(Exception e)
                    {

                    }
                }



            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
          //  Toast.makeText(LoginActivity.this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}

