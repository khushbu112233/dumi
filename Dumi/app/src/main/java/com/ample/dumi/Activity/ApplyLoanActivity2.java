package com.ample.dumi.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityApplyLoan2Binding;

/**
 * Created by khushbu987 on 6/1/2018.
 */

public class ApplyLoanActivity2 extends AppCompatActivity {
    ActivityApplyLoan2Binding activityApplyLoan2Binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityApplyLoan2Binding = DataBindingUtil.setContentView(this, R.layout.activity_apply_loan2);
    }
}
