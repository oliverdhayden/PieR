
package com.groupl.project.pier;
import android.os.Build;
import android.os.Environment;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.amazonaws.services.s3.AmazonS3;
import android.support.v4.content.ContextCompat;
import android.content.Context;
import android.database.Cursor;
import android.content.CursorLoader;
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
import java.io.InputStream;
import java.net.URISyntaxException;

import  	android.Manifest;
import  	android.content.pm.PackageManager;
import  	android.support.v4.content.ContextCompat;
import  	android.support.v4.app.ActivityCompat;
public class FileUpload extends AppCompatActivity {

    String TAG = "FileUpload";
    ProgressBar pb;
    Button btn_upload;
    TextView _status;
    String PathHolder = "newTest.jpg";

    String selectedImagePath;

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
        pb = (ProgressBar) findViewById(R.id.progressBar);

        btn_upload = (Button) findViewById(R.id.btn_upload);
        _status = (TextView) findViewById(R.id.txt_progress);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData(PathHolder);
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
                            String filePath = data.getData().getPath();

                            Uri selectedImageUri = data.getData();

                            //MEDIA GALLERY
                            selectedImagePath = ImageFilePath.getPath(getApplicationContext(), selectedImageUri);
                            Log.i("Image File Path", "path = "+selectedImagePath);
                            Toast.makeText(FileUpload.this, "path = " + selectedImagePath , Toast.LENGTH_LONG).show();


//                            String path = getContentResolver().getType(PathUri);
                              //String path = PathUri.toString();
                              //file = new File(PathUri.toString());
                              //PathHolder = file.getAbsolutePath();
                            //String path = getRealPathFromURI(PathUri);
                            //Toast.makeText(FileUpload.this, "path = " +path , Toast.LENGTH_LONG).show();


                            imageView.setImageURI(PathUri);

                            PathHolder = PathUri.toString();
                            PathUri = data.getData();
                            imageView.setImageURI(PathUri);
                            Toast.makeText(FileUpload.this, PathHolder , Toast.LENGTH_LONG).show();
//                            intent = new Intent(MainActivity.class);
//                            Intent intent1 = new Intent(intent.this, MainActivity.class);
//                            startActivity(intent);
                           //uploadData(PathHolder); //COMMENT THIS TO STOP CRASHING
                        }
                        break;
                }
            }



//    private String getRealPathFromURI(Uri contentUri) {
//        String[] proj = { MediaStore.Images.Media.DATA };
//        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
//        Cursor cursor = loader.loadInBackground();
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String result = cursor.getString(column_index);
//        cursor.close();
//        return result;
//    }

    private String getRealPathFromURI(Uri contentURI) {
        String result = null;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
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


    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()){
            System.out.println("Directory not existy");
        }
        return file;
    }




    public void uploadData(String path) {
        requestPermissions();
        // Initialize AWSMobileClient if not initialized upon the app startup.
        // AWSMobileClient.getInstance().initialize(this).execute();

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();
        System.out.println(getPublicAlbumStorageDir("Pictures"));
        //System.out.println(getRealPathFromURI(PathUri)+"!!!!!!!!!!!!!!!");

        //File file = new File("/sdcard/Download/1p74ap.jpg");
        //File file = new File("/storage/742B-E957/DCIM/Camera/20170815_132902.jpg");
        File file = new File(selectedImagePath);
        String fileName = file.getName();

        if (file == null) {
            Toast.makeText(this, "Could not find the filepath of  the selected file", Toast.LENGTH_LONG).show();
            // to make sure that file is not emapty or null
            return;
        }
        TransferObserver uploadObserver =
                transferUtility.upload(
                        "pierandroid-userfiles-mobilehub-318679301/public",
                        fileName,
                        file);
                //"1p74ap.jpg",
                //"20170815_132902.jpg",
                //"s3Folder/s3Key.txt",

        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    System.out.println("hello");

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
                System.out.println(id+ " "+ex);
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

