package com.example.niden.cellwatchsharing.helper;


import android.content.Context;
import android.widget.Toast;

public class Helper {

    public static final String NAME = "Name";
    public static final String BIO = "bio";
    public static final String EXPIRATION_DATE = "Expiration_date";

    public static final String EMAIL = "Email";

    public static final String BIRTHDAY = "Birthday";

    public static final String CONTACT = "Contact";

    public static final String HOBBY_INTEREST = "Hobby & Interest";

    public static final String PROFILE_URL = "Profile_Url";

    public static final int SELECT_PICTURE = 2000;

    public static boolean isValidEmail(String email){
        if(email.contains("@")){
            return true;
        }
        return false;
    }

    public static void displayMessageToast(Context context, String displayMessage){
        Toast.makeText(context, displayMessage, Toast.LENGTH_LONG).show();
    }
}
