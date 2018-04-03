package com.groupl.project.pier;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


import com.amazonaws.http.HttpClient;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.SAXParserFactory;

import au.com.bytecode.opencsv.CSVReader;

public class settingPage extends AppCompatActivity {

    String TAG = "settingPage";
    List<String[]> list = new ArrayList<String[]>();
    int groceries =0, rent = 0, transport = 0, bills = 0, shopping = 0, eatingOut = 0, general =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        // ************ RUN METHOD TO CHECK FILE *********************
        checkFile();







        CompoundButton.OnCheckedChangeListener multiSwitch = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch (compoundButton.getId()) {
                    case R.id.option1:
                        setPreference(b, "option1");
                        break;
                    case R.id.option2:
                        setPreference(b, "option2");
                        break;
                    case R.id.option3:
                        setPreference(b, "option3");
                        break;
                    case R.id.option4:
                        setPreference(b, "option4");
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

    static public boolean getPreference(Context context, String option) {
        SharedPreferences prefs = context.getSharedPreferences("Preference", MODE_PRIVATE);
        return prefs.getBoolean("Option " + option, true);
    }

    public void reset(View view) {
        WelcomeSlider.setPreference(true, "firsttime", this);
        Toast.makeText(settingPage.this, "First Time Resetted", Toast.LENGTH_SHORT).show();
    }

    public void testFileAccess(View view)throws Exception{
//        File csv = new File(Environment.getExternalStorageDirectory(),"PierData/infoFile.csv");
//        Scanner scanner = new Scanner(csv);
//        for(int i = 0; i<10; i++){
//            if(scanner.hasNext()){
//                Log.i(TAG, "testFileAccess: "+scanner.next() );
//            }
//        }
        UserStatement user = new UserStatement();
        preference.setPreferenceObject(this,"userStatement",user);
    }

    public void testGetSharedPrefObj(View view)throws Exception{
        UserStatement user = preference.getPreferenceObject(this,"userStatement");
        Log.i(TAG, "testGetSharedPrefObj: " +user.name);
    }



    // ---------------------- CHECK FILE ---------------------------
    void checkFile() {
        // ------- ASK PERMISSION TO EDIT FILES -------------------
        ActivityCompat.requestPermissions(settingPage.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        final String userName = preference.getPreference(this, "username");
        String folderName = "PierData";
        // CREATE FOLDER TO STORE THE CSV
        File dir = new File(Environment.getExternalStorageDirectory(), folderName);
        if (!dir.exists()){
            dir.mkdirs();
            Log.d("Directory", "created");
        } else
        {
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
                boolean check = S3_CLIENT.doesObjectExist("/pierandroid-userfiles-mobilehub-318679301/public/"+userName,"global_statement.csv");
                Log.d("CHECK_IF_EXIST"," -> "+ check);

                // IF EXIST DOWNLOAD
                if (check){
                    TransferUtility transferUtility =
                            TransferUtility.builder()
                                    .context(getApplicationContext())
                                    .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                    .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                                    .build();
                    TransferObserver downloadObserver = transferUtility.download("/pierandroid-userfiles-mobilehub-318679301/public/"+userName,"global_statement.csv", fileDown);

                    Log.d("FilePath",fileDown.getAbsolutePath());

                    // Attach a listener to the observer to get notified of the
                    // updates in the state and the progress
                    downloadObserver.setTransferListener(new TransferListener() {

                        @Override
                        public void onStateChanged(int id, TransferState state) {
                            if (TransferState.COMPLETED == state) {
                                Toast.makeText(settingPage.this,"Download Completed", Toast.LENGTH_SHORT).show();
                                parseCSV(fileDown.getAbsolutePath());
                            }
                        }

                        @Override
                        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                            float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                            int percentDone = (int)percentDonef;

                            Log.d("Download ->", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
                        }

                        @Override
                        public void onError(int id, Exception ex) {
                            Log.d("ErrorDownload", "error id:" + id + "error->"+ex );
                        }

                    });
                }else {
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
            for(;;) {
                next = reader.readNext();
                if(next != null) {
                    list.add(next);
                } else {
                    break;
                }
            }

            // ******************* SAVE TO PREFERENCE ************
            for (int i =1; i < list.size(); i++){
//                Log.d("Day",list.get(list.size()-1)[0]);
//                Log.d("Month",list.get(list.size()-1)[1]);
//                Log.d("Year",list.get(list.size()-1)[2]);
//                Log.d("Desc",list.get(list.size()-1)[3]);
//                Log.d("Category",list.get(list.size()-1)[4]);
//                Log.d("Value",list.get(list.size()-1)[5]);
//                Log.d("Balance",list.get(list.size()-1)[6]);



                //add data to the database

                // if its the last month of the last year
                if((list.get(i)[2]).equals(list.get(list.size()-1)[2]) && (list.get(i)[1]).equals(list.get(list.size()-1)[1])) {
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
                    if ((list.get(i)[4]).toLowerCase().equals("shopping")) {
                        shopping += Integer.parseInt(list.get(i)[5]);
                    }

                }

                Log.d("Day",list.get(i)[0]);
                Log.d("Month",list.get(i)[1]);
                Log.d("Year",list.get(i)[2]);
                Log.d("Desc",list.get(i)[3]);
                Log.d("Category",list.get(i)[4]);
                Log.d("Value",list.get(i)[5]);
                Log.d("Balance",list.get(i)[6]);
       //         AddData(list.get(i)[0],list.get(i)[1],list.get(i)[2],list.get(i)[3],list.get(i)[4],list.get(i)[5],list.get(i)[6]);

            }
            preference.setPreference(this,"groceries",String.valueOf(groceries));
            preference.setPreference(this,"general",String.valueOf(general));
            preference.setPreference(this,"eatingOut",String.valueOf(eatingOut));
            preference.setPreference(this,"transport",String.valueOf(transport));
            preference.setPreference(this,"rent",String.valueOf(rent));
            preference.setPreference(this,"bills",String.valueOf(bills));
            preference.setPreference(this,"shopping",String.valueOf(shopping));

            // *************** CREATE SIMPLE DATABASE ***********

            try {
                // create a tabase if not exist, if does make it accessable
                SQLiteDatabase pierDatabase = settingPage.this.openOrCreateDatabase("Statement",MODE_PRIVATE,null);
                // create table
                pierDatabase.execSQL("CREATE TABLE IF NOT EXISTS statement (day VARCHAR, month VARCHAR, year VARCHAR, description VARCHAR, category VARCHAR, value VARCHAR, balance VARCHAR)");
                // cleare data from table only for demo purpose
                pierDatabase.execSQL("DELETE FROM  statement");
                for (int i = 1; i < list.size(); i++) {
                    if (list.get(i)[1].equals("3") && list.get(i)[2].equals("2018")) {
                        String desc = list.get(i)[3];
                        if (desc.toLowerCase().equals("scott's restaurant")){
                            desc = "Scotts Restaurant";
                        }
                        Log.i("Month",list.get(i)[1]);
                        // add data to the database
                        pierDatabase.execSQL("INSERT INTO statement (day,month,year,description,category,value,balance) VALUES ('"+list.get(i)[0]+"','"+list.get(i)[1]+"','"+list.get(i)[2]+"','"+desc+"','"+list.get(i)[4]+"','"+list.get(i)[5]+"','"+list.get(i)[6]+"')");
                    }
                }
                Log.i("Database", "all data added");

                Cursor cursor = pierDatabase.rawQuery("SELECT * FROM statement", null);

                int dayIndex = cursor.getColumnIndex("day");


                cursor.moveToFirst();
                while (cursor!=null){
                    Log.i("day", cursor.getString(dayIndex));
                    cursor.moveToNext();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("Array size",String.valueOf(list.size()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
