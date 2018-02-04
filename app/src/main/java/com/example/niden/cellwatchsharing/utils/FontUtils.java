package com.example.niden.cellwatchsharing.utils;

import com.example.niden.cellwatchsharing.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by niden on 05-Feb-18.
 */

public class FontUtils {
    public static void setUpFont(){

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/lato_hairline.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
