package com.example.niden.cellwatchsharing.utils;

import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by niden on 26-Mar-18.
 */

public class ShakerAnimationUtils {
    public static TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 5, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }
}
