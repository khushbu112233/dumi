package com.ample.dumi.Activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

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
        activityAddPayeeTransferBinding.seekbarDumi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;

                activityAddPayeeTransferBinding.txtSeek.setTextColor(getResources().getColor(R.color.white));
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
        activityAddPayeeTransferBinding.seekbarDumi.setProgress(0);
        activityAddPayeeTransferBinding.txtSeek.setTextColor(getResources().getColor(R.color.black));
    }
}
