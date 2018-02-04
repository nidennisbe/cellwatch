package com.example.niden.cellwatchsharing.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by niden on 03-Feb-18.
 */

public class ValidationUtils {
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
