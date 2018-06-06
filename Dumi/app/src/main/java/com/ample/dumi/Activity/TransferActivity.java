package com.ample.dumi.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityTransferBinding;

public class TransferActivity extends AppCompatActivity {

    ActivityTransferBinding activityTransferBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTransferBinding = DataBindingUtil.setContentView(this,R.layout.activity_transfer);
        activityTransferBinding.includeActionbar1.txtActionBarTitle.setText(getResources().getString(R.string.transfer));

/**
 * transfer to your account
 */
        activityTransferBinding.seekTrYourBank.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;

                activityTransferBinding.txtSeek1.setTextColor(getResources().getColor(R.color.white));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent i = new Intent(TransferActivity.this,TransferToYourBankActivity.class);
                startActivity(i);
            }
        });
        /**
         * transfer to another account
         */
        activityTransferBinding.seekTrAnotherBank.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;

                activityTransferBinding.txtSeek2.setTextColor(getResources().getColor(R.color.white));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent i = new Intent(TransferActivity.this,TransfertoAnotherBankActivity.class);
                startActivity(i);
            }
        });
        /**
         * transfer to dummy user
         */
        activityTransferBinding.seekTrDummyUser.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;

                activityTransferBinding.txtSeek3.setTextColor(getResources().getColor(R.color.white));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent i = new Intent(TransferActivity.this,TransfertoDummyActivity.class);
                startActivity(i);
            }
        });
        activityTransferBinding.includeActionbar1.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityTransferBinding.seekTrDummyUser.setProgress(0);
        activityTransferBinding.seekTrAnotherBank.setProgress(0);
        activityTransferBinding.seekTrYourBank.setProgress(0);

        activityTransferBinding.txtSeek1.setTextColor(getResources().getColor(R.color.black));
        activityTransferBinding.txtSeek2.setTextColor(getResources().getColor(R.color.black));
        activityTransferBinding.txtSeek3.setTextColor(getResources().getColor(R.color.black));
    }
}
