package com.ample.dumi.Activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivitySignupBinding;

/**
 * Created by khushbu987 on 6/1/2018.
 */

public class SignUpActivity extends AppCompatActivity {

    ActivitySignupBinding activitySignupBinding;
    public static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignupBinding = DataBindingUtil.setContentView(this,R.layout.activity_signup);

        activity = this;
        activitySignupBinding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(i);
                LoginActivity.killLogin();
                finish();
            }
        });

        activitySignupBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, OTPSendActivity.class);
                startActivity(i);
            }
        });
    }

    public static void killSignUp(){
        activity.finish();
    }
}
