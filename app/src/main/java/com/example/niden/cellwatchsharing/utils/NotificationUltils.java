package com.example.niden.cellwatchsharing.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.example.niden.cellwatchsharing.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by niden on 06-Feb-18.
 */

public class NotificationUltils {
    public static void showAlertNotifcation(Context context){
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("NEW TASK ADDED");
        builder.setContentText("Please check your TASK tab");
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
