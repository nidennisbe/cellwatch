package com.example.niden.cellwatchsharing.activities;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.niden.cellwatchsharing.controllers.Account;
import com.example.niden.cellwatchsharing.controllers.Task;
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
    Task mTask = new Task();
    ProgressDialog myDialog;
    Account mAccount = new Account();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAccount.getFirebaseAuth();
        mAccount.checkUserLogin(LoginActivity.this);

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
                    ToastUtils.displayMessageToast(LoginActivity.this,getString(R.string.validation_email));
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.displayMessageToast(LoginActivity.this,getString(R.string.validation_password));
                    return;
                }
                //End of Validation
                if (isOnline()) {
                    //do whatever you want to do
                    myDialog = DialogsUtils.showProgressDialog(LoginActivity.this, getString(R.string.sign_in_process));
                    mAccount.loginAUser(LoginActivity.this, email, password, myDialog);
                }else
                {
                    DialogsUtils.showAlertDialogDismiss(LoginActivity.this,getString(R.string.internet_connection),getString(R.string.alert_internet_connection));
                }



            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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

