package com.example.niden.cellwatchsharing.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.adapters.AlarmReceiver;
import com.example.niden.cellwatchsharing.controllers.Account;
import com.example.niden.cellwatchsharing.serivces.LocationBackgroundService;
import com.example.niden.cellwatchsharing.utils.DialogsUtils;
import com.example.niden.cellwatchsharing.utils.InternetConnUtils;
import com.example.niden.cellwatchsharing.utils.KeyboardUtils;
import com.example.niden.cellwatchsharing.utils.ToastUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            ToastUtils.showSnackbar(linearLayout, getString(R.string.validation_email), Snackbar.LENGTH_LONG);
            return;
        } else if (TextUtils.isEmpty(password)) {
            ToastUtils.showSnackbar(linearLayout, getString(R.string.validation_password), Snackbar.LENGTH_LONG);
            return;
        }
        else if (!isEmailValid(email)){
            ToastUtils.showSnackbar(linearLayout, getString(R.string.txt_invalid_email_format), Snackbar.LENGTH_LONG);
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
            DialogsUtils.showAlertDialogDismiss(mActivity, getString(R.string.internet_connection), getString(R.string.alert_internet_connection));
        }
    }

  /*  private void setReminder(Class<?> cls)
    {
        Calendar calendar = Calendar.getInstance();

        Calendar setcalendar = Calendar.getInstance();
        setcalendar.set(Calendar.HOUR_OF_DAY, 21);
        setcalendar.set(Calendar.MINUTE, 32);


        if(setcalendar.before(calendar))
            setcalendar.add(Calendar.DATE,1);

        // Enable a receiver

       ComponentName receiver = new ComponentName(mActivity, cls);
        PackageManager pm = mActivity.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);


        Intent intent1 = new Intent(mActivity, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mActivity, DAILY_REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) mActivity.getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }*/




}

