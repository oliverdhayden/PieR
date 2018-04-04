
package com.groupl.project.pier;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;

import android.support.v4.content.ContextCompat;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.services.s3.AmazonS3Client;
// Following imports establish connection to AWS Mobile Services
import com.amazonaws.mobile.client.AWSMobileClient;
// Following imports handle uploading a file to S3 Bucket


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;


import java.io.File;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import au.com.bytecode.opencsv.CSVReader;

//todo
//add error retry upload button
//on sucsesfful upload change remove upload functionallyty from button to stop multiple uploads,
//add return to home button on complete
//add file name chosen + change file button.
public class FileUpload extends AppCompatActivity {

    public static boolean uploadButtonWasPressed = false;
    String TAG = "FileUpload";
    String PathHolder = "newTest.jpg";
    String identityID = "this failed";
    String filePath;
    ProgressDialog dialog;


    int percentDone = 0;
    ProgressBar progressBar;
    TextView progressText;
    Button buttonUpload;


    List<String[]> list = new ArrayList<String[]>();
    int groceries = 0, rent = 0, transport = 0, bills = 0, shopping = 0, eatingOut = 0, general = 0;


//    AmazonS3Client s3;
//    BasicAWSCredentials credentials;
//    TransferUtility transferUtility;
//    TransferObserver observer;

    //    String key = "FK5382F0HJ409J2309";
//    String secret = "FAJ9E280F39FA0FUA90FSP/ACN3820F";
//    String path = "someFilePath.png";
    Intent intent = null;
    AmazonS3 s3;
    Uri PathUri;
    File file;

    public void setPreference( boolean b, String option) {
        SharedPreferences prefs = this.getSharedPreferences("Preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("Option " + option, b);
        editor.apply();
    }

    public void requestPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            System.out.println("hello");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        // ------- ASK PERMISSION TO EDIT FILES -------------------
        ActivityCompat.requestPermissions(FileUpload.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        buttonUpload = findViewById(R.id.btn_upload);

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressBarText);


        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadButtonWasPressed = true;
                uploadData(filePath);
            }
        });
        requestPermissions();
        credentialsProvider();
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 7);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    //gets String Path of selected file
                    PathUri = data.getData();
                    filePath = FilePathUtil.getPath(getApplicationContext(), PathUri);
                    File file = new File(filePath);
                    //check the extention is correct
                    String extension = FileExtentionUtil.getExtensionOfFile(file);
                    String csv = "csv";
                    //if incorrect extension restart the file manager
                    if (!extension.equals(csv)) {
                        Toast.makeText(this, "The chosen file has the extension " + extension + " which is not a csv file, please choose another file.", Toast.LENGTH_LONG).show();
                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("*/*");
                        startActivityForResult(intent, 7);
                    }
                    // ------------------------ SHOW SELECTED FILE NAME -----------------------------------------------
                    TextView filename = (TextView) findViewById(R.id.filename);
                    filename.setText(file.getName());
                    percentDone = 0;
                }
                break;
        }
    }


    public void credentialsProvider() {
        AWSConfiguration a = new AWSConfiguration(this);
        CognitoUserPool userPool = new CognitoUserPool(this, a);
        CognitoUser user = userPool.getCurrentUser();

        // Implement callback handler for getting details
        GetDetailsHandler getDetailsHandler = new GetDetailsHandler() {
            @Override
            public void onSuccess(CognitoUserDetails cognitoUserDetails) {
                // The user detail are in cognitoUserDetails
                Map userAtts = new HashMap();
                userAtts = cognitoUserDetails.getAttributes().getAttributes();
                //identityID = userAtts.get("sub").toString();
                //System.out.println(identityID);
            }

            @Override
            public void onFailure(Exception exception) {
                System.out.println(exception);
            }
        };

// Fetch the user details
        user.getDetailsInBackground(getDetailsHandler);

    }

    public void uploadData(String path) {
        // Initialize AWSMobileClient if not initialized upon the app startup.
        // AWSMobileClient.getInstance().initialize(this).execute();
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();
        File file = new File(path);
        String fileName = file.getName();
        if (file == null) {
            Toast.makeText(this, "Could not find the filepath of  the selected file", Toast.LENGTH_LONG).show();
            // to make sure that file is not emapty or null
            return;
        }


        // ----------------------- BLOCK UPLOAD IF NOT AN CSV FILE ----------------------------------------
        //check the extention is correct
        String extension = FileExtentionUtil.getExtensionOfFile(file);
        String csv = "csv";
        //if incorrect extension restart the file manager
        if (!extension.equals(csv)) {
            Toast.makeText(this, "Please select an CSV type file!!", Toast.LENGTH_LONG).show();
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, 7);
            return;
        }


//        dialog = new ProgressDialog(this);
//        dialog.setMessage("Uploading File");
//        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        dialog.setMax(100);
//        dialog.show();

        //old bucket name = "pierandroid-userfiles-mobilehub-318679301/public/"+userName
        //new bucket "pierandroid-userfiles-mobilehub-318679301/public/incoming"
        //old key "newest_statement.csv"
        //new key userName+".csv"
        String userName = preference.getPreference(this, "username");
        TransferObserver uploadObserver =
                transferUtility.upload(
                        "pierandroid-userfiles-mobilehub-318679301/public/incoming",
                        userName + ".csv",
                        file);
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    Toast.makeText(FileUpload.this, "Upload Completed", Toast.LENGTH_LONG).show();
                    //dialog.hide();

                    //***************** CHECK FILE ************
                    checkFile();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                percentDone = (int) percentDonef;

                //dialog.setProgress(percentDone);
                progressBar.setProgress(percentDone);
                progressText.setText(percentDone + "/" + progressBar.getMax());

                //System.out.println(dialog.getProgress());
                Log.i(TAG, "onProgressChanged:" + "ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
                Log.d(TAG, "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {

                System.out.println(id + " " + ex);
                Toast.makeText(FileUpload.this, "error uploading", Toast.LENGTH_LONG).show();


            }

        });

        // If your upload does not trigger the onStateChanged method inside your
        // TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            System.out.println("COMPLETE");
            Toast.makeText(this, "upload complete", Toast.LENGTH_LONG).show();
        }
    }

    //this method analize the data from the csv file and returns a string with the value of every spending type separated by a comma
    public static String stringOfTotalSpendings(String data) {
        int countComma = 0;
        int step = 11;
        String paid = "";
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == ',') {
                countComma++;
                if (countComma == step) {
                    while (data.charAt(i + 1) != ',') {
                        paid = paid + data.charAt(i + 1);
                        i++;
                    }
                    step += 6;
                    if (step + 100 < data.length()) {
                        paid = paid + ',';
                    }
                }
            }
        }
        return paid;
    }

    // ---------------------- CHECK FILE ---------------------------
    void checkFile() {
        // ------- ASK PERMISSION TO EDIT FILES -------------------
        ActivityCompat.requestPermissions(FileUpload.this,
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
                                Toast.makeText(FileUpload.this, "Download Completed", Toast.LENGTH_SHORT).show();
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
                    if ((list.get(i)[4]).toLowerCase().equals("shopping")) {
                        shopping += Integer.parseInt(list.get(i)[5]);
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
            setPreference(true,"dataDownloaded");
            preference.setPreference(this, "groceries", String.valueOf(groceries));
            preference.setPreference(this, "general", String.valueOf(general));
            preference.setPreference(this, "eatingOut", String.valueOf(eatingOut));
            preference.setPreference(this, "transport", String.valueOf(transport));
            preference.setPreference(this, "rent", String.valueOf(rent));
            preference.setPreference(this, "bills", String.valueOf(bills));
            preference.setPreference(this, "shopping", String.valueOf(shopping));

            int monthTotal = groceries +general+eatingOut+transport+rent+bills+shopping;
            preference.setPreference(this, "monthTotal", String.valueOf(monthTotal));


            // *************** CREATE SIMPLE DATABASE ***********

            try {
                // create a tabase if not exist, if does make it accessable
                SQLiteDatabase pierDatabase = FileUpload.this.openOrCreateDatabase("Statement", MODE_PRIVATE, null);
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

