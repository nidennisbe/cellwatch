package com.example.niden.cellwatchsharing.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by niden on 21-Nov-17.
 */

public class ToastUtils {
    public static void displayMessageToast(Context context, String displayMessage){
        Toast.makeText(context, displayMessage, Toast.LENGTH_LONG).show();
    }
}
