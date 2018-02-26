package com.groupl.project.pier;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class settingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        CompoundButton.OnCheckedChangeListener multiSwitch = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch (compoundButton.getId()){
                    case R.id.option1:
                        setPreference(b,"option1");
                        break;
                    case R.id.option2:
                        setPreference(b,"option2");
                        break;
                    case R.id.option3:
                        setPreference(b,"option3");
                        break;
                    case R.id.option4:
                        setPreference(b,"option4");
                        break;
                }
            }
        };
        ((Switch) findViewById(R.id.option1)).setOnCheckedChangeListener(multiSwitch);
        ((Switch) findViewById(R.id.option2)).setOnCheckedChangeListener(multiSwitch);
        ((Switch) findViewById(R.id.option3)).setOnCheckedChangeListener(multiSwitch);
        ((Switch) findViewById(R.id.option4)).setOnCheckedChangeListener(multiSwitch);

        // Active selected Switch
        ((Switch) findViewById(R.id.option1)).setChecked(getPreference(this, "option1"));
        ((Switch) findViewById(R.id.option2)).setChecked(getPreference(this, "option2"));
        ((Switch) findViewById(R.id.option3)).setChecked(getPreference(this, "option3"));
        ((Switch) findViewById(R.id.option4)).setChecked(getPreference(this, "option4"));

    }

    private void setPreference(boolean b, String option) {
        SharedPreferences prefs = this.getSharedPreferences("Preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("Option " + option, b);
        editor.apply();
    }
    static public boolean getPreference(Context context, String option){
        SharedPreferences prefs = context.getSharedPreferences("Preference", MODE_PRIVATE);
        return prefs.getBoolean("Option "+ option, true);
    }

    public void reset(View view) {
        WelcomeSlider.setPreference(true, "firsttime",this);
        Toast.makeText(settingPage.this,"First Time Resetted", Toast.LENGTH_SHORT).show();
    }
}
