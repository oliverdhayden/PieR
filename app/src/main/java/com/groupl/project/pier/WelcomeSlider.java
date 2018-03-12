package com.groupl.project.pier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeSlider extends AppCompatActivity {
    private ViewPager viewPager;
    private SlideAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if(getPreference(this,"firsttime")) {
            if(true){
            setContentView(R.layout.welcome_screen0);
        }
        else {

            Intent intent = new Intent(WelcomeSlider.this, MainActivity.class);
            startActivity(intent);
            setPreference(false, "firsttime",this);
            finish();
        }
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
    public void skipTour(View view) {
        Intent intent = new Intent(WelcomeSlider.this, Login.class);
        startActivity(intent);
        setPreference(false, "firsttime",this);
        finish();
    }

    public void startTour(View view) {
        setContentView(R.layout.activity_welcome_slider);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        myadapter = new SlideAdapter(this);
        viewPager.setAdapter(myadapter);
    }
}
