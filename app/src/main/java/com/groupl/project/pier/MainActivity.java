package com.groupl.project.pier;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static PinpointManager pinpointManager;

    private String TAG = "Main Activity";
    private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
    private String[] xData = {"Mitch", "Jessica" , "Mohammad" , "Kelsey", "Sam", "Robert", "Ashley"};
    PieChart pieChart;

    //for test commit
    //raju
    private DrawerLayout myDrawerLaout;
    private ActionBarDrawerToggle myToggle;
    NavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check login


        setContentView(R.layout.activity_main);

        // Establishes connection to AWS Mobile
        AWSMobileClient.getInstance().initialize(this).execute();

        // AWS-Analytics
        PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                getApplicationContext(),
                AWSMobileClient.getInstance().getCredentialsProvider(),
                AWSMobileClient.getInstance().getConfiguration());
        pinpointManager = new PinpointManager(pinpointConfig);
        // Starts new session with PinPoint
        pinpointManager.getSessionClient().startSession();
        // Stops session and submits default app started event
        pinpointManager.getSessionClient().stopSession();
        pinpointManager.getAnalyticsClient().submitEvents();

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP

        //adding logo to title bar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.actionbar_logo);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        //nav bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_summary:

                        break;

                    case R.id.ic_full_statement:
                        Intent intent1 = new Intent(MainActivity.this, FullStatement.class);
                        Log.i(TAG, "onNavigationItemSelected: 1");
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_tagging:
                        Intent intent2 = new Intent(MainActivity.this, Tagging.class);
                        Log.i(TAG, "onNavigationItemSelected: 2");

                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.ic_feedback:
                        Intent intent3 = new Intent(MainActivity.this, Feedback.class);
                        Log.i(TAG, "onNavigationItemSelected: 3");

                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        break;
                }
                return false;
            }
        });

        //pie chart
        pieChart = (PieChart) findViewById(R.id.idPieChart);

        //pieChart.setDescription(" ");
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        //pieChart.setCenterText("Super Cool Chart");
        //pieChart.setCenterTextSize(0);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        pieChart.getLegend().setEnabled(false);

        addDataSet(pieChart);

        pieChart.animateX(700);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());

//                int pos1 = e.toString().indexOf("(sum): ");
//                String sales = e.toString().substring(pos1 + 7);
//
//                for(int i = 0; i < yData.length; i++){
//                    if(yData[i] == Float.parseFloat(sales)){
//                        pos1 = i;
//                        break;
//                    }
//                }
//                String employee = xData[pos1 + 1];
//                Toast.makeText(MainActivity.this, "Employee " + employee + "\n" + "Sales: $" + sales + "K", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {
                Log.i(TAG, "onNothingSelected: ran");
            }
        });



        //raju
        navigation = (NavigationView) findViewById(R.id.navigation_view);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG, "onNavigationItemSelected: ran");
                switch (item.getItemId()){
                    case R.id.login:
                        Intent login = new Intent(MainActivity.this,Login.class);
                        Log.i(TAG, "onNavigationItemSelected: login");
                        startActivity(login);
                        break;
                    case R.id.setting:
                        Intent setting = new Intent(MainActivity.this,settingPage.class);
                        startActivity(setting);
                        Log.i(TAG, "onNavigationItemSelected: settings");
                        break;
                    case R.id.about:
                        Intent about = new Intent(MainActivity.this,aboutUS.class);
                        Log.i(TAG, "onNavigationItemSelected: about");
                        startActivity(about);
                        break;
                    case R.id.upload:
                        Intent upload = new Intent(MainActivity.this,FileUpload.class);
                        Log.i(TAG, "onNavigationItemSelected: upload");
                        startActivity(upload);
                }
                Log.i(TAG, "onNavigationItemSelected: false");
                return false;
            }

        });
        //------------------------------code for home page which displays the summary of the spendings----------------

        ListView mListView = (ListView)findViewById(R.id.listViewForHomePage);

        HomePageListItem l1 = new HomePageListItem("Groceries","£120", "drawable://" + R.drawable.groceries);
        HomePageListItem l2 = new HomePageListItem("Rent","£1470", "drawable://" + R.drawable.rent);
        HomePageListItem l3 = new HomePageListItem("Transport","£235", "drawable://" + R.drawable.transportation);
        HomePageListItem l4 = new HomePageListItem("Bills","£130", "drawable://" + R.drawable.bills);
        HomePageListItem l5 = new HomePageListItem("Shopping","£200", "drawable://" + R.drawable.shopping);
        HomePageListItem l7 = new HomePageListItem("Eating Out","£49", "drawable://" + R.drawable.food);
        HomePageListItem l8 = new HomePageListItem("General","£68", "drawable://" + R.drawable.general);

        ArrayList<HomePageListItem> breakdownList = new ArrayList<>();
        breakdownList.add(l1);
        breakdownList.add(l2);
        breakdownList.add(l3);
        breakdownList.add(l4);
        breakdownList.add(l5);
        breakdownList.add(l7);
        breakdownList.add(l8);

        HomePageListAdapter adapter = new HomePageListAdapter(this, R.layout.adapter_view_for_home_page, breakdownList);
        mListView.setAdapter(adapter);

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
        Log.i(TAG, "onOptionsItemSelected: ran");
        int id = item.getItemId();
        String idStr = getResources().getResourceName(id);
        Log.i(TAG, "onOptionsItemSelected: "+idStr);
        if (myToggle.onOptionsItemSelected(item)){
            return false;
        }
        Log.i(TAG, "onOptionsItemSelected: test");
        return super.onOptionsItemSelected(item);
    }



    private void addDataSet(PieChart chart){
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();

        for(int i = 0; i< yData.length; i++){
            yEntries.add(new PieEntry(yData[i], i));
        }

        for(int i = 0; i< xData.length; i++){
            xEntries.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntries,"");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
//        colors.add(Color.GRAY);
//        colors.add(Color.BLUE);
//        colors.add(Color.RED);
//        colors.add(Color.GREEN);
//        colors.add(Color.CYAN);
//        colors.add(Color.YELLOW);
//        colors.add(Color.MAGENTA);

        //test colours
//        colors.add(getResources().getColor(R.color.sky_blue));
//        colors.add(getResources().getColor(R.color.light_green));
//        colors.add(getResources().getColor(R.color.arctic_lime));
//        colors.add(getResources().getColor(R.color.marigold));
//        colors.add(getResources().getColor(R.color.steel_blue));
//        colors.add(getResources().getColor(R.color.light_slate_gray));
//        colors.add(getResources().getColor(R.color.fuzzy_wuzzy));

        //google pie chart colours
        colors.add(getResources().getColor(R.color.b4));
        colors.add(getResources().getColor(R.color.b2));
        colors.add(getResources().getColor(R.color.b3));
        colors.add(getResources().getColor(R.color.b1));
        colors.add(getResources().getColor(R.color.b5));
        colors.add(getResources().getColor(R.color.b6));
        colors.add(getResources().getColor(R.color.b7));
        colors.add(getResources().getColor(R.color.indian_red));
        colors.add(getResources().getColor(R.color.coral));
        colors.add(getResources().getColor(R.color.sandy_brown));
        colors.add(getResources().getColor(R.color.yellow_green));

        //custom colours from jo
//        colors.add(getResources().getColor(R.color.burnt_sienna));
//        colors.add(getResources().getColor(R.color.fiord));
//        colors.add(getResources().getColor(R.color.porsche));
//        colors.add(getResources().getColor(R.color.pot_pourri));
//        colors.add(getResources().getColor(R.color.shadow_green));
//        colors.add(getResources().getColor(R.color.porsche_2));
//        colors.add(getResources().getColor(R.color.tobacco_brown));


        pieDataSet.setColors(colors);

        //add legend to chart
//        Legend legend = pieChart.getLegend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
