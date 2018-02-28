package com.groupl.project.pier;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.amazonaws.services.s3.AmazonS3;

import android.database.Cursor;
import android.provider.MediaStore;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import android.database.Cursor;
// Following imports establish connection to AWS Mobile Services
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.s3.transferutility.*;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.services.s3.AmazonS3Client;
// Following imports handle uploading a file to S3 Bucket
import android.app.Activity;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.services.s3.AmazonS3Client;
import android.widget.ImageView;

import java.io.File;

public class FileUpload extends AppCompatActivity {

    ProgressBar pb;
    Button btn_upload;
    TextView _status;
    String PathHolder = "newTest.jpg";

//    AmazonS3Client s3;
//    BasicAWSCredentials credentials;
//    TransferUtility transferUtility;
//    TransferObserver observer;

//    String key = "FK5382F0HJ409J2309";
//    String secret = "FAJ9E280F39FA0FUA90FSP/ACN3820F";
//    String path = "someFilePath.png";
    Intent intent = null;
    AmazonS3 s3;
    private ImageView imageView;
    Uri PathUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        btn_upload = (Button) findViewById(R.id.btn_upload);
        _status = (TextView) findViewById(R.id.txt_progress);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData(PathHolder);
                System.out.println("hello");
            }
        });
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
                            PathUri = data.getData();
                            imageView.setImageURI(PathUri);
                            PathHolder = getRealPathFromURI((PathUri));
                            PathUri = data.getData();
                            imageView.setImageURI(PathUri);
                            //Toast.makeText(FileUpload.this, PathHolder , Toast.LENGTH_LONG).show();
//                            intent = new Intent(MainActivity.class);
//                            Intent intent1 = new Intent(intent.this, MainActivity.class);
//                            startActivity(intent);
                           //uploadData(PathHolder); //COMMENT THIS TO STOP CRASHING
                        }
                        break;
                }
            }
    private String getRealPathFromURI(Uri contentURI) {
        String thePath = "no-path-found";
        String[] filePathColumn = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(contentURI, filePathColumn, null, null, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            thePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return  thePath;
    }
    public void credentialsProvider(){

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "eu-west-2_L2N7BBzdI", // Identity pool ID
                Regions.EU_WEST_2 // Region
        );
        setAmazonS3Client(credentialsProvider);
    }
    public void setAmazonS3Client(CognitoCachingCredentialsProvider credentialsProvider){
        // Create an S3 client
        s3 = new AmazonS3Client(credentialsProvider);
        // Set the region of your S3 bucket
        s3.setRegion(Region.getRegion(Regions.EU_WEST_2));
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

        File file = new File(getRealPathFromURI(PathUri));
        System.out.println(file);
        System.out.println(file.canRead());
        System.out.println(file == null );
        System.out.println(file.isDirectory());
        System.out.println(!file.exists());
        if (file == null) {
            Toast.makeText(this, "Could not find the filepath of  the selected file", Toast.LENGTH_LONG).show();
            // to make sure that file is not emapty or null
            return;
        }
        TransferObserver uploadObserver =
                transferUtility.upload(
                        "pierandroid-userfiles-mobilehub-318679301",
                        PathHolder,
                        new File(getRealPathFromURI(PathUri)));
            //        "s3Folder/s3Key.txt",

        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a import android.database.Cursor;completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("MainActivity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

        // If your upload does not trigger the onStateChanged method inside your
        // TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }
    }

}