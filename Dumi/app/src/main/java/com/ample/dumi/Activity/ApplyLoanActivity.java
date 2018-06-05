package com.ample.dumi.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityApplyLoanBinding;

/**
 * Created by khushbu987 on 6/1/2018.
 */

public class ApplyLoanActivity extends AppCompatActivity {
    ActivityApplyLoanBinding activityApplyLoanBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityApplyLoanBinding = DataBindingUtil.setContentView(this, R.layout.activity_apply_loan);
        activityApplyLoanBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ApplyLoanActivity.this,ApplyLoanActivity2.class);
                startActivity(i);
            }
        });
        activityApplyLoanBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
