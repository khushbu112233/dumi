package com.ample.dumi.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivitySignupBinding;

/**
 * Created by khushbu987 on 6/1/2018.
 */

public class SignUpActivity extends AppCompatActivity {

    ActivitySignupBinding activitySignupBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignupBinding = DataBindingUtil.setContentView(this,R.layout.activity_signup);
    }
}
