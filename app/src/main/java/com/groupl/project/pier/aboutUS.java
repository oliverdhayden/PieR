package com.groupl.project.pier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class aboutUS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ImageButton goBack = (ImageButton) findViewById(R.id.buttonToGoBackFromAboutUsPage);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent0 = new Intent(aboutUS.this, MainActivity.class);
                startActivity(intent0);
            }
        });
    }
}
