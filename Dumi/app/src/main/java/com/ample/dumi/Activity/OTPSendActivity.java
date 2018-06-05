package com.ample.dumi.Activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityOtpsendBinding;

public class OTPSendActivity extends AppCompatActivity {
    ActivityOtpsendBinding activityOtpsendBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOtpsendBinding = DataBindingUtil.setContentView(this,R.layout.activity_otpsend);
    }
}
