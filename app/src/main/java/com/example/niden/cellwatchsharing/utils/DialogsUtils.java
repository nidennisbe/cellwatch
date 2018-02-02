package com.example.niden.cellwatchsharing.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.example.niden.cellwatchsharing.R;

/**
 * Created by niden on 19-Nov-17.
 */

public class DialogsUtils {


    public static ProgressDialog showProgressDialog(Context context, String message) {
        ProgressDialog mDialog = new ProgressDialog(context);
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
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
              //  MainActivity.activity.finish();
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(startMain);
            }
        });
        mAlertDialog.setNegativeButton("Cancel",null);
        mAlertDialog.show();
        return mAlertDialog;
    }//END OF AlertDialog


    public static void showAlertDialogDismiss(final Context context, String title, String message){
        AlertDialog.Builder mAlertDialog= new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert);
        mAlertDialog.setTitle(title);
        mAlertDialog.setMessage(message);
        mAlertDialog.setCancelable(false);
        mAlertDialog.setNegativeButton("Okay",null);
        mAlertDialog.show();
    }//END OF AlertDialog
}
