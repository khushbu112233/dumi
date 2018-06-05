package com.ample.dumi.Activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityAddPayeeTransferBinding;

public class AddPayeeTransferActivity extends AppCompatActivity {

    ActivityAddPayeeTransferBinding activityAddPayeeTransferBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddPayeeTransferBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_payee_transfer);
        activityAddPayeeTransferBinding.txtActionBarTitle.setText(getResources().getString(R.string.transfer));
        activityAddPayeeTransferBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
