package com.groupl.project.pier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.StartupAuthResult;
import com.amazonaws.mobile.auth.core.StartupAuthResultHandler;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

public class SplashActivity extends AppCompatActivity {
    String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.i(TAG, "onCreate: Splash started");
        AWSMobileClient.getInstance().initialize(SplashActivity.this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
                identityManager.resumeSession(SplashActivity.this, new StartupAuthResultHandler() {
                    @Override
                    public void onComplete(StartupAuthResult authResults) {
                        if (authResults.isUserSignedIn()) {
                            Log.i(TAG, "onComplete: User is signed in");
                            startActivity(new Intent(SplashActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                        } else {
                            Log.i(TAG, "onComplete: user is not signed in");
                            startActivity(new Intent(SplashActivity.this, WelcomeSlider.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                        }
                    }
                }, 2000);
            }
        }).execute();
    }
}
