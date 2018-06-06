package com.ample.dumi.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

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
        activityAddPayeeDumiTransferBinding.seekbarDumi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;

                activityAddPayeeDumiTransferBinding.txtSeek.setTextColor(getResources().getColor(R.color.white));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityAddPayeeDumiTransferBinding.seekbarDumi.setProgress(0);
        activityAddPayeeDumiTransferBinding.txtSeek.setTextColor(getResources().getColor(R.color.black));
    }
}
