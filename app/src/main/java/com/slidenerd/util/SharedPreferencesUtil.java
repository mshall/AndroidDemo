package com.slidenerd.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by elsaidel on 10/26/2016.
 */

public class SharedPreferencesUtil {


    public static void saveBooleanToSharedPreferences(Context context, String variableName, String variableToBeSaved) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SlideNerdSharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(variableName, variableToBeSaved);
        editor.apply();
    }

    public static String getFromSharedPreferences(Context context, String variableName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SlideNerdSharedPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString(variableName, defaultValue);
    }
}
