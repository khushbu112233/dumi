package com.ample.dumi.Activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityTransferToYourBankBinding;

public class TransferToYourBankActivity extends AppCompatActivity {
ActivityTransferToYourBankBinding activityTransferToYourBankBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTransferToYourBankBinding = DataBindingUtil.setContentView(this,R.layout.activity_transfer_to_your_bank);
    }
}
