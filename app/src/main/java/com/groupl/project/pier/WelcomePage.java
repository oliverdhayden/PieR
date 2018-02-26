package com.groupl.project.pier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static com.groupl.project.pier.WelcomePage.setPreference;

public class WelcomePage extends AppCompatActivity {
    String TAG = "WelcomePage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getPreference(this,"firsttime")) {
            setContentView(R.layout.welcome_screen0);
        }
        else {
            Intent intent = new Intent(WelcomePage.this, MainActivity.class);
            startActivity(intent);
            setPreference(false, "firsttime",this);
        }


    }


    public void skipTour(View view) {
        Intent intent = new Intent(WelcomePage.this, MainActivity.class);
        startActivity(intent);
        setPreference(false, "firsttime",this);
    }

    static public void setPreference(boolean b, String option,Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("Option " + option, b);
        editor.apply();
    }
    static public boolean getPreference(Context context, String option){
        SharedPreferences prefs = context.getSharedPreferences("Preference", MODE_PRIVATE);
        return prefs.getBoolean("Option "+ option, true);
    }

    public void startTour(View view) {
        Intent intent = new Intent(WelcomePage.this, WelcomeSlider.class);
        startActivity(intent);
        setPreference(false, "firsttime",this);
    }
}
