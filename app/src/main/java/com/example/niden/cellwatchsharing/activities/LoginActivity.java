package com.example.niden.cellwatchsharing.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.controllers.Account;
import com.example.niden.cellwatchsharing.utils.DialogsUtils;
import com.example.niden.cellwatchsharing.utils.InternetConnUtils;
import com.example.niden.cellwatchsharing.utils.KeyboardUtils;

import static com.example.niden.cellwatchsharing.utils.DialogsUtils.showAlertDialogDismiss;
import static com.example.niden.cellwatchsharing.utils.ValidationUtils.isEmailValid;


/**
 * Created by niden on 16-Nov-17.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    LinearLayout linearLayout;
    Button  btnLogin, btnReset;
    ProgressDialog myDialog;
    Account mAccount = new Account();
    Activity mActivity;
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    public static final int DAILY_REMINDER_REQUEST_CODE=100;
    public static final String TAG="NotificationScheduler";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mActivity=this;
        mAccount.getFirebaseAuth();
        mAccount.checkUserLogin(LoginActivity.this);
        bindingViews();


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftKeyboard(v, mActivity);
            }
        });




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldsValidation(v);
                //setReminder(AlarmReceiver.class);
            }
        });
    }

    private void bindingViews() {
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btn_login);
       // btnReset = (Button) findViewById(R.id.btn_reset_password);
        linearLayout = (LinearLayout) findViewById(R.id.layout_parent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAccount.isUserCurrentlyLogin(mActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAccount.checkUserLogin(mActivity);

    }

    private void fieldsValidation(View v) {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        //Validation
        if (TextUtils.isEmpty(email)) {
            showAlertDialogDismiss(mActivity, "Validatoin", getString(R.string.validation_email));
          //  ToastUtils.showSnackbar(linearLayout, getString(R.string.validation_email), Snackbar.LENGTH_LONG);
            return;
        } else if (TextUtils.isEmpty(password)) {
            //ToastUtils.showSnackbar(linearLayout, getString(R.string.validation_password), Snackbar.LENGTH_LONG);
            showAlertDialogDismiss(mActivity, "Validatoin", getString(R.string.validation_password));
            return;
        }
        else if (!isEmailValid(email)){
            showAlertDialogDismiss(mActivity, "Validatoin", getString(R.string.txt_invalid_email_format));
           // ToastUtils.showSnackbar(linearLayout, getString(R.string.txt_invalid_email_format), Snackbar.LENGTH_LONG);
            return;
        }
        //End of Validation
        checkConnection(v, email, password);
    }

    private void checkConnection(View v, String email, String password) {
        if (InternetConnUtils.isOnline(mActivity)) {
            KeyboardUtils.hideSoftKeyboard(v, mActivity);
            myDialog = DialogsUtils.showProgressDialog(mActivity, getString(R.string.sign_in_process));
            mAccount.loginAUser(linearLayout, mActivity, email, password, myDialog);
        } else {
            showAlertDialogDismiss(mActivity, getString(R.string.internet_connection), getString(R.string.alert_internet_connection));
        }
    }
}

