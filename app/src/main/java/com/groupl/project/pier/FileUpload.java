
package com.groupl.project.pier;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.*;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
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

import  	android.Manifest;
import  	android.content.pm.PackageManager;
import  	android.support.v4.app.ActivityCompat;
//todo
//add error retry upload button
//on sucsesfful upload change remove upload functionallyty from button to stop multiple uploads,
//add return to home button on complete
//add file name chosen + change file button.
public class FileUpload extends AppCompatActivity {

    String TAG = "FileUpload";
    String PathHolder = "newTest.jpg";
    String identityID = "this failed";
    String filePath;
    ProgressDialog dialog;



    int percentDone = 0;
    ProgressBar progressBar;
    TextView progressText;
    Button buttonUpload;


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

        buttonUpload = findViewById(R.id.btn_upload);

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressBarText);


        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                switch(requestCode){
                    case 7:
                        if(resultCode==RESULT_OK){
                            //gets String Path of selected file
                            PathUri = data.getData();
                            filePath = FilePathUtil.getPath(getApplicationContext(), PathUri);
                            Log.i(TAG, "Path:" + filePath);
                            File file = new File(filePath);
                            //check the extention is correct
                            String extension = FileExtentionUtil.getExtensionOfFile(file);
                            String csv = "csv";
                            //if incorrect extension restart the file manager
                            if(!extension.equals(csv)){
                                Toast.makeText(this, "The chosen file has the extension "+extension + " which is not a csv file, please choose another file." , Toast.LENGTH_LONG).show();
                                intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("*/*");
                                startActivityForResult(intent, 7);
                            }
                            percentDone = 0;
                        }
                        break;
                }
            }


    public void credentialsProvider() {
        AWSConfiguration a = new AWSConfiguration(this);
        CognitoUserPool userPool = new CognitoUserPool(this,a);
        CognitoUser user = userPool.getCurrentUser();

        // Implement callback handler for getting details
        GetDetailsHandler getDetailsHandler = new GetDetailsHandler() {
            @Override
            public void onSuccess(CognitoUserDetails cognitoUserDetails) {
                // The user detail are in cognitoUserDetails
                Map userAtts = new HashMap();
                userAtts = cognitoUserDetails.getAttributes().getAttributes();
                identityID = userAtts.get("sub").toString();
                System.out.println(identityID);
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
//        dialog = new ProgressDialog(this);
//        dialog.setMessage("Uploading File");
//        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        dialog.setMax(100);
//        dialog.show();
        TransferObserver uploadObserver =
                transferUtility.upload(
                        "pierandroid-userfiles-mobilehub-318679301/public/"+identityID,
                        fileName,
                        file);
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    Toast.makeText(FileUpload.this, "Upload Completed" , Toast.LENGTH_LONG).show();
                    //dialog.hide();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                percentDone = (int)percentDonef;

                //dialog.setProgress(percentDone);
                progressBar.setProgress(percentDone);
                progressText.setText(percentDone+"/"+progressBar.getMax());

                //System.out.println(dialog.getProgress());
                Log.i(TAG, "onProgressChanged:" + "ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
                Log.d(TAG, "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {

                System.out.println(id+ " "+ex);
                Toast.makeText(FileUpload.this, "error uploading" , Toast.LENGTH_LONG).show();


            }

        });

        // If your upload does not trigger the onStateChanged method inside your
        // TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            System.out.println("COMPLETE");
            Toast.makeText(this, "upload complete", Toast.LENGTH_LONG).show();
        }
    }

}

