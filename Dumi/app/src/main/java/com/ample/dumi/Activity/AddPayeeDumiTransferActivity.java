package com.ample.dumi.Activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityAddPayeeDumiTransferBinding;

public class AddPayeeDumiTransferActivity extends AppCompatActivity {

    ActivityAddPayeeDumiTransferBinding activityAddPayeeDumiTransferBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddPayeeDumiTransferBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_payee_dumi_transfer);
        activityAddPayeeDumiTransferBinding.txtActionBarTitle.setText(getResources().getString(R.string.transfer));
        activityAddPayeeDumiTransferBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
