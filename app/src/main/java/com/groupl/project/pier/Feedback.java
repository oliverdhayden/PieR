package com.groupl.project.pier;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by ollie on 28/01/2018.
 */

public class Feedback extends AppCompatActivity {
    //raju
    private DrawerLayout myDrawerLaout;
    private ActionBarDrawerToggle myToggle;
    NavigationView navigation;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setAllowReturnTransitionOverlap(false);

//        int navBar = R.id.bottomBar
//        Transition fade = new Fade();
//        fade.excludeTarget(navBar, true);
//        fade.excludeTarget(android.R.id.navigationBarBackground, true);
//        getWindow().setExitTransition(fade);
//        getWindow().setEnterTransition(fade);

        setContentView(R.layout.activity_feedback);


        TextView title = findViewById(R.id.activityTitle3);
        title.setText("This is Activity Three");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent intent0 = new Intent(Feedback.this, MainActivity.class);
                        startActivity(intent0);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_pie:
                        Intent intent1 = new Intent(Feedback.this, FullStatement.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_money:
                        Intent intent2 = new Intent(Feedback.this, Tagging.class);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_settings:
                        break;
                }
                return false;
            }
        });
        //raju
        navigation = (NavigationView) findViewById(R.id.navigation_view);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.login:
                        Intent login = new Intent(Feedback.this,Login.class);
                        startActivity(login);
                        break;
                    case R.id.setting:
                        Intent setting = new Intent(Feedback.this,settingPage.class);
                        startActivity(setting);
                        break;
                    case R.id.about:
                        Intent about = new Intent(Feedback.this,aboutUS.class);
                        startActivity(about);
                        break;
                }
                return false;
            }

        });

        //raju
        myDrawerLaout = (DrawerLayout) findViewById(R.id.drawer);
        myToggle = new ActionBarDrawerToggle(this,myDrawerLaout,R.string.Open,R.string.Close);
        myDrawerLaout.addDrawerListener(myToggle);
        myToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    //raju - opens the menu tab
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String idStr = getResources().getResourceName(id);
        if (myToggle.onOptionsItemSelected(item)){
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
