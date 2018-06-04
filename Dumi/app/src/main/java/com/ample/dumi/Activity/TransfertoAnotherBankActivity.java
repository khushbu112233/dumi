package com.ample.dumi.Activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityTransfertoAnotherBankBinding;

public class TransfertoAnotherBankActivity extends AppCompatActivity {
ActivityTransfertoAnotherBankBinding activityTransfertoAnotherBankBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTransfertoAnotherBankBinding = DataBindingUtil.setContentView(this,R.layout.activity_transferto_another_bank);
    }
}
