package com.ample.dumi.Activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityTransferBinding;

public class TransferActivity extends AppCompatActivity {

    ActivityTransferBinding activityTransferBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTransferBinding = DataBindingUtil.setContentView(this,R.layout.activity_transfer);
        activityTransferBinding.includeActionbar1.txtActionBarTitle.setText(getResources().getString(R.string.transfer));
    }
}
