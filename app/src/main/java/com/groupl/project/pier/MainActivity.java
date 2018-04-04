package com.groupl.project.pier;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;

import android.Manifest;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
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
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class MainActivity extends AppCompatActivity {

    public static PinpointManager pinpointManager;

    //******* GET DATA BACK FORM PREFERENCE ********
//    String groceries = preference.getPreference(this,"groceries");
//    String general = preference.getPreference(this,"general");
//    String rent = preference.getPreference(this,"rent");
//    String eatingOut = preference.getPreference(this,"eatingOut");
//    String untagged = preference.getPreference(this,"untagged");
//    String bills = preference.getPreference(this,"bills");
//    String transport = preference.getPreference(this,"transport");


    private String TAG = "Main Activity";

    PieChart pieChart;
    //raju
    private DrawerLayout myDrawerLaout;
    private ActionBarDrawerToggle myToggle;
    NavigationView navigation;
    List<String[]> list = new ArrayList<String[]>();
    int groceries = 0, rent = 0, transport = 0, bills = 0, untagged = 0, eatingOut = 0, general = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String downloade = preference.getPreference(MainActivity.this,"alreadyDownloaded");
        if (downloade.equals("true")){

        }
        else {
            //*************** CHECK FILE *****************
            checkFile();
        }

        // ------- ASK PERMISSION TO EDIT FILES -------------------
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        // ------- ASK PERMISSION TO EDIT FILES -------------------
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                2);


        AWSConfiguration a = new AWSConfiguration(this);
        CognitoUserPool userPool = new CognitoUserPool(this, a);

        CognitoUser user = userPool.getCurrentUser();
        preference.setPreference(this, "username", user.getUserId());
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

        //pieChart.setHoleColor(Color.BLUE);
        pieChart.setCenterTextColor(R.color.black);
        pieChart.setEntryLabelColor(R.color.black);
        pieChart.setEntryLabelTextSize(15);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        //pieChart.setTransparentCircleAlpha(0);
        //pieChart.setCenterText("Super Cool Chart");
        //pieChart.setCenterTextSize(0);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        pieChart.getLegend().setEnabled(false);
        //pieChart.setNoDataText("Please upload a bank statement");

        //check if the data is downloaded
        if (!FileUpload.getPreference(this, "dataDownloaded")) {
            //if (false){
            Log.i(TAG, "onCreate: this fired");
            pieChart.setCenterText("Please upload a bank statement");
            pieChart.setUsePercentValues(false);
            pieChart.setDrawEntryLabels(false);
            pieChart.setRotationEnabled(false);
            addEmptyData(pieChart);
        }

        else {
            float[] yData = new float[7];
            yData[0] = hasData(preference.getPreference(this, "rent"));
            yData[1] = hasData(preference.getPreference(this, "bills"));
            yData[2] = hasData(preference.getPreference(this, "transport"));
            yData[3] = hasData(preference.getPreference(this, "untagged"));
            yData[4] = hasData(preference.getPreference(this, "eatingOut"));
            yData[5] = hasData(preference.getPreference(this, "general"));
            yData[6] = hasData(preference.getPreference(this, "groceries"));

            String[] xData = {"Rent", "Bills", "Transport", "Untagged", "Eating Out", "General", "Groceries"};
            addDataSet(pieChart, yData, xData);
            pieChart.setUsePercentValues(false);
            pieChart.setDrawEntryLabels(true);
            pieChart.setRotationEnabled(true);
            pieChart.setCenterText("Month total: £"+preference.getPreference(this, "monthTotal"));
        }

        //pieChart.setDrawSliceText(false);
        pieChart.invalidate();
        pieChart.animateX(700);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
//                Log.d(TAG, "onValueSelected: Value select from chart.");
//                Log.d(TAG, "onValueSelected: " + e.toString());
//                Log.d(TAG, "onValueSelected: " + h.toString());

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
        View headerView = navigation.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.header_username);
        username.setText(preference.getPreference(this, "username").toUpperCase());
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG, "onNavigationItemSelected: ran");
                switch (item.getItemId()) {
                    case R.id.signOut:
                        Intent SignOut = new Intent(MainActivity.this, SignOutActivity.class);
                        Log.i(TAG, "onNavigationItemSelected: signOut");
                        startActivity(SignOut);
                        break;
                    case R.id.setting:
                        Intent setting = new Intent(MainActivity.this, settingPage.class);
                        startActivity(setting);
                        Log.i(TAG, "onNavigationItemSelected: settings");
                        break;
                    case R.id.about:
                        Intent about = new Intent(MainActivity.this, aboutUS.class);
                        Log.i(TAG, "onNavigationItemSelected: about");
                        startActivity(about);
                        break;
                    case R.id.upload:
                        Intent upload = new Intent(MainActivity.this, FileUpload.class);
                        Log.i(TAG, "onNavigationItemSelected: upload");
                        startActivity(upload);
                }
                Log.i(TAG, "onNavigationItemSelected: false");
                return false;
            }

        });
        //set username


        //------------------------------code for home page which displays the summary of the spendings----------------

        ListView mListView = (ListView) findViewById(R.id.listViewForHomePage);
        String[] money = {"£120", "£1470", "£235", "£130", "£200", "£49", "£68"};
        HomePageListAdapter.valueOfRent = money[1];
        HomePageListAdapter.rentIcon = "drawable://" + R.drawable.groceries;
        HomePageListItem l1 = new HomePageListItem("Groceries", "£" + preference.getPreference(this, "groceries"), "drawable://" + R.drawable.groceries);
        HomePageListItem l2 = new HomePageListItem("Rent", "£" + preference.getPreference(this, "rent"), "drawable://" + R.drawable.rent);
        HomePageListItem l3 = new HomePageListItem("Transport", "£" + preference.getPreference(this, "transport"), "drawable://" + R.drawable.transportation);
        HomePageListItem l4 = new HomePageListItem("Bills", "£" + preference.getPreference(this, "bills"), "drawable://" + R.drawable.bills);
        HomePageListItem l5 = new HomePageListItem("Untagged", "£" + preference.getPreference(this, "untagged"), "drawable://" + R.drawable.shopping);
        HomePageListItem l7 = new HomePageListItem("Eating Out", "£" + preference.getPreference(this, "eatingOut"), "drawable://" + R.drawable.food);
        HomePageListItem l8 = new HomePageListItem("General", "£" + preference.getPreference(this, "general"), "drawable://" + R.drawable.general);

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
        myToggle = new ActionBarDrawerToggle(this, myDrawerLaout, R.string.Open, R.string.Close);
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
        Log.i(TAG, "onOptionsItemSelected: " + idStr);
        if (myToggle.onOptionsItemSelected(item)) {
            return false;
        }
        Log.i(TAG, "onOptionsItemSelected: test");
        return super.onOptionsItemSelected(item);
    }

    private float hasData(String preference) {
        float value = 0;
        if (!preference.equals("N/A")) {
            value = Float.parseFloat(preference);
        }
        return value;
    }

    private void addEmptyData(PieChart chart) {
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();

        yEntries.add(new PieEntry(75, ""));
        yEntries.add(new PieEntry(75, ""));
        xEntries.add("");
        xEntries.add("");

        PieDataSet pieDataSet = new PieDataSet(yEntries, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(0);

        ArrayList<Integer> colors = new ArrayList<>();
//        colors.add(getResources().getColor(R.color.b6));
        colors.add(getResources().getColor(R.color.b2));

        //colors.add(getResources().getColor(R.color.coral));

        pieDataSet.setColors(colors);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();


    }

    private void addDataSet(PieChart chart, float[] yData, String[] xData) {
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntries.add(new PieEntry(yData[i], xData[i]));
        }

        for (int i = 0; i < xData.length; i++) {
            xEntries.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntries, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        //pieDataSet.setDrawValues(false); //this turns the percents off


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
        //Legend legend = pieChart.getLegend();
        //legend.setForm(Legend.LegendForm.CIRCLE);
        //legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    // ---------------------- CHECK FILE ---------------------------
    void checkFile() {
        // ------- ASK PERMISSION TO EDIT FILES -------------------
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        final String userName = preference.getPreference(this, "username");
        String folderName = "PierData";
        // CREATE FOLDER TO STORE THE CSV
        File dir = new File(Environment.getExternalStorageDirectory(), folderName);
        if (!dir.exists()) {
            dir.mkdirs();
            Log.d("Directory", "created");
        } else {
            Log.d("Folder ->", "not created");
        }
        // FILE TO STORE THE CSV INFO
        final File fileDown = new File(dir, "infoFile.csv");
        new Thread(new Runnable() {
            @Override
            public void run() {

                AmazonS3 S3_CLIENT = new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider());
                S3_CLIENT.setRegion(com.amazonaws.regions.Region.getRegion(Regions.EU_WEST_2));
                // CHECK IF FILE EXIST
                boolean check = S3_CLIENT.doesObjectExist("/pierandroid-userfiles-mobilehub-318679301/public/" + userName, "last_six_months.csv");
                Log.d("CHECK_IF_EXIST", " -> " + check);

                // IF EXIST DOWNLOAD
                if (check) {
                    TransferUtility transferUtility =
                            TransferUtility.builder()
                                    .context(getApplicationContext())
                                    .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                    .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                                    .build();
                    TransferObserver downloadObserver = transferUtility.download("/pierandroid-userfiles-mobilehub-318679301/public/" + userName, "last_six_months.csv", fileDown);

                    Log.d("FilePath", fileDown.getAbsolutePath());

                    // Attach a listener to the observer to get notified of the
                    // updates in the state and the progress
                    downloadObserver.setTransferListener(new TransferListener() {

                        @Override
                        public void onStateChanged(int id, TransferState state) {
                            if (TransferState.COMPLETED == state) {
                                Toast.makeText(MainActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
                                parseCSV(fileDown.getAbsolutePath());
                            }
                        }

                        @Override
                        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                            float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                            int percentDone = (int) percentDonef;

                            Log.d("Download ->", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
                        }

                        @Override
                        public void onError(int id, Exception ex) {
                            Log.d("ErrorDownload", "error id:" + id + "error->" + ex);
                        }

                    });
                } else {
                    // if file doesent exist check again
                    //commented else check file out, danger of infinate loop
                    // checkFile();
                }

            }
        }).start();


    }

    public void parseCSV(String url) {
        String next[] = {};

        try {
            //************ PARSE CVS TO ARRAYLIST *****************
            CSVReader reader = new CSVReader(new FileReader(url));// file to parse
            for (; ; ) {
                next = reader.readNext();
                if (next != null) {
                    list.add(next);
                } else {
                    break;
                }
            }

            // ******************* SAVE TO PREFERENCE ************
            for (int i = 1; i < list.size(); i++) {
//                Log.d("Day",list.get(list.size()-1)[0]);
//                Log.d("Month",list.get(list.size()-1)[1]);
//                Log.d("Year",list.get(list.size()-1)[2]);
//                Log.d("Desc",list.get(list.size()-1)[3]);
//                Log.d("Category",list.get(list.size()-1)[4]);
//                Log.d("Value",list.get(list.size()-1)[5]);
//                Log.d("Balance",list.get(list.size()-1)[6]);


                //add data to the database

                // if its the last month of the last year
                if ((list.get(i)[2]).equals(list.get(list.size() - 1)[2]) && (list.get(i)[1]).equals(list.get(list.size() - 1)[1])) {
                    if ((list.get(i)[4]).toLowerCase().equals("groceries")) {
                        groceries += Integer.parseInt(list.get(i)[5]);
                    }
                    if ((list.get(i)[4]).toLowerCase().equals("general")) {
                        general += Integer.parseInt(list.get(i)[5]);
                    }
                    if ((list.get(i)[4]).toLowerCase().equals("eating out")) {
                        eatingOut += Integer.parseInt(list.get(i)[5]);
                    }
                    if ((list.get(i)[4]).toLowerCase().equals("transport")) {
                        transport += Integer.parseInt(list.get(i)[5]);
                    }
                    if ((list.get(i)[4]).toLowerCase().equals("rent")) {
                        rent += Integer.parseInt(list.get(i)[5]);
                    }
                    if ((list.get(i)[4]).toLowerCase().equals("bills")) {
                        bills += Integer.parseInt(list.get(i)[5]);
                    }
                    if ((list.get(i)[4]).toLowerCase().equals("untagged")) {
                        untagged += Integer.parseInt(list.get(i)[5]);
                    }

                }

                Log.d("Day", list.get(i)[0]);
                Log.d("Month", list.get(i)[1]);
                Log.d("Year", list.get(i)[2]);
                Log.d("Desc", list.get(i)[3]);
                Log.d("Category", list.get(i)[4]);
                Log.d("Value", list.get(i)[5]);
                Log.d("Balance", list.get(i)[6]);
                //         AddData(list.get(i)[0],list.get(i)[1],list.get(i)[2],list.get(i)[3],list.get(i)[4],list.get(i)[5],list.get(i)[6]);

            }
            preference.setPreference(this, "groceries", String.valueOf(groceries));
            preference.setPreference(this, "general", String.valueOf(general));
            preference.setPreference(this, "eatingOut", String.valueOf(eatingOut));
            preference.setPreference(this, "transport", String.valueOf(transport));
            preference.setPreference(this, "rent", String.valueOf(rent));
            preference.setPreference(this, "bills", String.valueOf(bills));
            preference.setPreference(this, "untagged", String.valueOf(untagged));

            // *************** CREATE SIMPLE DATABASE ***********

            try {
                // create a tabase if not exist, if does make it accessable
                SQLiteDatabase pierDatabase = MainActivity.this.openOrCreateDatabase("Statement", MODE_PRIVATE, null);
                // create table
                pierDatabase.execSQL("CREATE TABLE IF NOT EXISTS statement (day VARCHAR, month VARCHAR, year VARCHAR, description VARCHAR, category VARCHAR, value VARCHAR, balance VARCHAR)");
                // cleare data from table only for demo purpose
                pierDatabase.execSQL("DELETE FROM  statement");
                for (int i = 1; i < list.size(); i++) {
                    //if (list.get(i)[1].equals("3") && list.get(i)[2].equals("2018")) {
                    String desc = list.get(i)[3];
                    if (desc.toLowerCase().equals("scott's restaurant")) {
                        desc = "Scotts Restaurant";
                    }
                    // add data to the database
                    pierDatabase.execSQL("INSERT INTO statement (day,month,year,description,category,value,balance) VALUES ('" + list.get(i)[0] + "','" + list.get(i)[1] + "','" + list.get(i)[2] + "','" + desc + "','" + list.get(i)[4] + "','" + list.get(i)[5] + "','" + list.get(i)[6] + "')");
                    //}
                }


                    //****************** RESTART APP ***********************
                    preference.setPreference(MainActivity.this,"alreadyDownloaded", "true");
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);


            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

