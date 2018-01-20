package com.example.niden.cellwatchsharing.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.niden.cellwatchsharing.activities.MainActivity;

import java.util.Calendar;

/**
 * Created by niden on 20-Nov-17.
 */

public class DatePickerUtils {
    public static void openDatePicker(Context referenceActivity, DatePickerDialog datePickerDialog, final Button mBtnStartDate,final  Button mBtnEndDate){
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(referenceActivity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        mBtnStartDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                        mBtnEndDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
