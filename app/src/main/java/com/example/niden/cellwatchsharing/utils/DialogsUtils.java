package com.example.niden.cellwatchsharing.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.activities.LoginActivity;
import com.example.niden.cellwatchsharing.activities.MainActivity;

/**
 * Created by niden on 19-Nov-17.
 */

public class DialogsUtils {


    public static ProgressDialog showProgressDialog(Context context, String message) {
        ProgressDialog mDialog = new ProgressDialog(context);
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.show();
        return mDialog;
    }//END OF ProgressDialog

    public static AlertDialog.Builder showAlertDialog(final Context context, String title, String message){
        AlertDialog.Builder mAlertDialog= new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert);
        mAlertDialog.setTitle(title);
        mAlertDialog.setMessage(message);
        mAlertDialog.setCancelable(false);
        mAlertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.activity.finish();
            }
        });
        mAlertDialog.setNegativeButton("Cancel",null);
        mAlertDialog.show();
        return mAlertDialog;
    }//END OF AlertDialog

}
