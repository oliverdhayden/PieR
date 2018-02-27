package com.groupl.project.pier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


import java.io.File;

public class FileUpload extends AppCompatActivity {

    ProgressBar pb;
    Button btn_upload;
    TextView _status;

    AmazonS3Client s3;
    BasicAWSCredentials credentials;
    TransferUtility transferUtility;
    TransferObserver observer;

    String key = "FK5382F0HJ409J2309";
    String secret = "FAJ9E280F39FA0FUA90FSP/ACN3820F";
    String path = "someFilePath.png";
    Intent intent = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);


        pb = (ProgressBar) findViewById(R.id.progressBar);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        _status = (TextView) findViewById(R.id.txt_progress);
        

        //when button clicked open file manager
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                credentials = new BasicAWSCredentials(key, secret);
                s3 = new AmazonS3Client(credentials);
                transferUtility = new TransferUtility(s3, FileUpload.this);

                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 7);

            }
        });

    }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                // TODO Auto-generated method stub
                switch(requestCode){
                    case 7:
                        if(resultCode==RESULT_OK){
                            String PathHolder = data.getData().getPath();
                            Toast.makeText(FileUpload.this, PathHolder , Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            }





//                File file = new File(path);
//                if(!file.exists()) {
//                    Toast.makeText(FileUpload.this, "File Not Found!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                observer = transferUtility.upload(
//                        "media.invision.com",
//                        "Test_Video",
//                        file
//                );
//
//                observer.setTransferListener(new TransferListener() {
//                    @Override
//                    public void onStateChanged(int id, TransferState state) {
//
//                        if (state.COMPLETED.equals(observer.getState())) {
//
//                            Toast.makeText(FileUpload.this, "File Upload Complete", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
//
//
//
//                        long _bytesCurrent = bytesCurrent;
//                        long _bytesTotal = bytesTotal;
//
//                        float percentage =  ((float)_bytesCurrent /(float)_bytesTotal * 100);
//                        Log.d("percentage","" +percentage);
//                        pb.setProgress((int) percentage);
//                        _status.setText(percentage + "%");
//                    }
//
//                    @Override
//                    public void onError(int id, Exception ex) {
//
//                        Toast.makeText(FileUpload.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });

//    }

}