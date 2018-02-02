package com.example.niden.cellwatchsharing.utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.example.niden.cellwatchsharing.R;

/**
 * Created by niden on 21-Nov-17.
 */

public class ToastUtils {
    public static void displayMessageToast(Context context, String displayMessage) {
        Toast.makeText(context, displayMessage, Toast.LENGTH_LONG).show();
    }


    public static void showSnackbar(View view, String message, int duration) {
        final Snackbar snackbar = Snackbar.make(view, message, duration);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(view.getResources().getColor(R.color.cardview_dark_background));


        snackbar.setAction("DISMISS", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
