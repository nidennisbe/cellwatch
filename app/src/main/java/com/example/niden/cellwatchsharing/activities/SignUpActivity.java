package com.example.niden.cellwatchsharing.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.controllers.Account;
import com.example.niden.cellwatchsharing.utils.DialogsUtils;
import com.example.niden.cellwatchsharing.utils.KeyboardUtils;
import com.example.niden.cellwatchsharing.utils.ToastUtils;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by niden on 16-Nov-17.
 */

public class SignUpActivity extends AppCompatActivity{

    public static FirebaseAuth auth;
    private EditText inputEmail, inputPassword;
    Button btnSignIn, btnSignUp, btnResetPassword;
    ProgressDialog myDialog;
    Account mAccount = new Account();
    CoordinatorLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAccount.getFirebaseAuth();
        mAccount.checkUserLogin(SignUpActivity.this);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        parentLayout = (CoordinatorLayout) findViewById(R.id.layout_parent);

        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftKeyboard(v,SignUpActivity.this);
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftKeyboard(v,SignUpActivity.this);
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    ToastUtils.displayMessageToast(SignUpActivity.this,getString(R.string.validation_email));
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.displayMessageToast(SignUpActivity.this,getString(R.string.validation_password));
                    return;
                }
                if (password.length() < 6) {
                    ToastUtils.displayMessageToast(SignUpActivity.this,getString(R.string.alert_short_password));
                    return;
                }else{
                    myDialog= DialogsUtils.showProgressDialog(SignUpActivity.this,getString(R.string.sign_up_process));
                }
                mAccount.createNewUser(SignUpActivity.this,email,password,myDialog);
            }
        });
    }



}
