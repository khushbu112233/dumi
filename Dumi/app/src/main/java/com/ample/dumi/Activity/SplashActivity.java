package com.ample.dumi.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ample.dumi.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefs = getSharedPreferences("dumy", MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                if (prefs.getBoolean("firstrun", true)) {
                    Intent intent = new Intent(getApplicationContext(), WalkthroughActivity.class);
                    startActivity(intent);
                    prefs.edit().putBoolean("firstrun", false).commit();
                    finish();
                } else {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
