package com.groupl.project.pier;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

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

    static public void setPreferenceObject(Context context, String optionName, UserStatement userObject) {
        SharedPreferences prefs = context.getSharedPreferences("Preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userObject);
        editor.putString(optionName, json);
        editor.apply();
    }

    static public UserStatement getPreferenceObject(Context context, String optionName) {
        SharedPreferences prefs = context.getSharedPreferences("Preference", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(optionName, "");
        UserStatement obj = gson.fromJson(json, UserStatement.class);
        return obj;
    }

    static public String getPreference(Context context, String optionName) {
        SharedPreferences prefs = context.getSharedPreferences("Preference", Context.MODE_PRIVATE);
        return prefs.getString(optionName, "N/A");
    }
}
