package com.example.niden.cellwatchsharing.utils;

import android.app.Activity;
import android.content.Intent;



/**
 * Created by niden on 25-Jan-18.
 */

public class GallaryUtils {

    public static void openGallary(Activity activity,int RESULT_LOAD_IMAGE) {


    Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setAction(Intent.ACTION_GET_CONTENT);

    activity.startActivityForResult(Intent.createChooser(intent,"Select Picture"),RESULT_LOAD_IMAGE);
}
}
