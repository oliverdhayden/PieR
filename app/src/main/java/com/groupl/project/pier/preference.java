package com.groupl.project.pier;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kremi on 13/03/2018.
 */

public class preference {
    static public void setPreference(Context context, String optionName, String optionValue) {
        SharedPreferences prefs = context.getSharedPreferences("Preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(optionName, optionValue);
        editor.apply();
    }
    static public String getPreference(Context context, String optionName){
        SharedPreferences prefs = context.getSharedPreferences("Preference", Context.MODE_PRIVATE);
        return prefs.getString(optionName, "Not logged in");
    }
}
