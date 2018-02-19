package com.groupl.project.pier;

import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.design.widget.BottomNavigationView;
        import android.support.v7.app.AppCompatActivity;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.TextView;

/**
 * Created by ollie on 28/01/2018.
 */

public class ActivityOne extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        int navBar = R.id.bottomBar
//        Transition fade = new Fade();
//        fade.excludeTarget(navBar, true);
//        fade.excludeTarget(android.R.id.navigationBarBackground, true);
//        getWindow().setExitTransition(fade);
//        getWindow().setEnterTransition(fade);

        setContentView(R.layout.activity_one);
        TextView title = findViewById(R.id.activityTitle1);
        title.setText("This is Activity One");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent intent0 = new Intent(ActivityOne.this, MainActivity.class);
                        startActivity(intent0);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_pie:
                        break;

                    case R.id.ic_money:
                        Intent intent2 = new Intent(ActivityOne.this, ActivityTwo.class);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_settings:
                        Intent intent3 = new Intent(ActivityOne.this, ActivityThree.class);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });
    }
}
