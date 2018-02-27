package com.groupl.project.pier;

import android.accounts.AccountAuthenticatorActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
// aws google sign-in imports
import android.app.Activity;
import android.os.Bundle;

import com.amazonaws.mobile.auth.google.GoogleButton;
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

public class Login extends AppCompatActivity {
    String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);

        Log.i(TAG,"onCreate ran --------------------------------");
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                AuthUIConfiguration config =
                        new AuthUIConfiguration.Builder()
                                .userPools(true)
                                .logoResId(R.mipmap.icon)
                                .canCancel(true)
                                .build();

//                SignInUI signin = (SignInUI) AWSMobileClient.getInstance().getClient(Login.this, SignInUI.class);
//                signin.login(Login.this, MainActivity.class).execute();

                SignInUI signinUI = (SignInUI) AWSMobileClient.getInstance().getClient(Login.this, SignInUI.class);
                signinUI.login(Login.this, MainActivity.class).authUIConfiguration(config).execute();
            }
        }).execute();


        }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Log.i(TAG,"--------------------------------------");
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
