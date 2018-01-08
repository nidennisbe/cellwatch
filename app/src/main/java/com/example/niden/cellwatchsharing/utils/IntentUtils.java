package com.example.niden.cellwatchsharing.utils;

import android.content.Context;
import android.content.Intent;

import com.example.niden.cellwatchsharing.activities.MainActivity;

/**
 * Created by niden on 20-Nov-17.
 */

public class IntentUtils {
    public static void openMainActivity(Context context){
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }
}
