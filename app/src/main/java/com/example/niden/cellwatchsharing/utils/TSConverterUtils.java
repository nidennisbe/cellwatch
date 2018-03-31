package com.example.niden.cellwatchsharing.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by niden on 30-Jan-18.
 */

    public class TSConverterUtils {

    public static String getDateFormat(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        String time = DateFormat.format("HH:mm",cal).toString();
        return date;

    }
    public static String getTimeFormat(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String time = DateFormat.format("hh:mm:AA",cal).toString();
        return time;

    }
}
