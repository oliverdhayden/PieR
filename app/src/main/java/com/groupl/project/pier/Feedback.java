package com.groupl.project.pier;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ollie on 28/01/2018.
 */

public class Feedback extends AppCompatActivity {
    public static String PACKAGE_NAME;
    private String greenMessage;
    private String redMessage;
    private String yellowMessage;
    //raju
    private DrawerLayout myDrawerLaout;
    private ActionBarDrawerToggle myToggle;
    NavigationView navigation;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PACKAGE_NAME = getApplicationContext().getPackageName();

        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setAllowReturnTransitionOverlap(false);

//        int navBar = R.id.bottomBar
//        Transition fade = new Fade();
//        fade.excludeTarget(navBar, true);
//        fade.excludeTarget(android.R.id.navigationBarBackground, true);
//        getWindow().setExitTransition(fade);
//        getWindow().setEnterTransition(fade);

        setContentView(R.layout.activity_feedback);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.actionbar_logo);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);


        //---------LIST VIEW CODE---------
        greenMessage = "Spending not too much money";
        yellowMessage = "Be carefull on how much money you spend";
        redMessage = "Spending way too much money";
        FeedbackTagListItem l1 = new FeedbackTagListItem("green_circle","deactive_red_circle", "deactive_yellow_circle", "green_circle", "Groceries", greenMessage);
        FeedbackTagListItem l2 = new FeedbackTagListItem("yellow_circle","deactive_red_circle", "yellow_circle", "deactive_green_circle", "Rent", yellowMessage);
        FeedbackTagListItem l3 = new FeedbackTagListItem("green_circle","deactive_red_circle", "deactive_yellow_circle", "green_circle", "Bills", greenMessage);
        FeedbackTagListItem l4 = new FeedbackTagListItem("green_circle","deactive_red_circle", "deactive_yellow_circle", "green_circle", "Shopping", greenMessage);
        FeedbackTagListItem l5 = new FeedbackTagListItem("green_circle","deactive_red_circle", "deactive_yellow_circle", "green_circle", "Transport", greenMessage);
        FeedbackTagListItem l6 = new FeedbackTagListItem("green_circle","deactive_red_circle", "deactive_yellow_circle", "green_circle", "Eating Out", greenMessage);
        FeedbackTagListItem l7 = new FeedbackTagListItem("green_circle","deactive_red_circle", "deactive_yellow_circle", "green_circle", "General", greenMessage);

        ArrayList<FeedbackTagListItem> breakdownList = new ArrayList<>();
        breakdownList.add(l1);
        breakdownList.add(l2);
        breakdownList.add(l3);
        breakdownList.add(l4);
        breakdownList.add(l5);
        breakdownList.add(l7);

        ListView mListView = (ListView)findViewById(R.id.listViewForFeedbackPage);
        FeedbackListItemAdapter adapter= new FeedbackListItemAdapter(this, R.layout.adapter_view_for_feedback_layout, breakdownList);
        mListView.setAdapter(adapter);

        //---------LIST VIEW CODE END--------

        //TextView title = findViewById(R.id.activityTitle3);
       // title.setText("This is Activity Three");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_summary:
                        Intent intent0 = new Intent(Feedback.this, MainActivity.class);
                        startActivity(intent0);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_full_statement:
                        Intent intent1 = new Intent(Feedback.this, FullStatement.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_tagging:
                        Intent intent2 = new Intent(Feedback.this, Tagging.class);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_feedback:
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
