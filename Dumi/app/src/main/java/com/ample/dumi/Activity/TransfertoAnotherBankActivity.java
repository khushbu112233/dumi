package com.ample.dumi.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityTransfertoAnotherBankBinding;

public class TransfertoAnotherBankActivity extends AppCompatActivity {

    ActivityTransfertoAnotherBankBinding activityTransfertoAnotherBankBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTransfertoAnotherBankBinding = DataBindingUtil.setContentView(this,R.layout.activity_transferto_another_bank);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        activityTransfertoAnotherBankBinding.includeActionbar1.txtActionBarTitle.setText(getResources().getString(R.string.transfer));
        activityTransfertoAnotherBankBinding.includeActionbar1.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activityTransfertoAnotherBankBinding.seekTransToBank.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;

                activityTransfertoAnotherBankBinding.txtSeek.setTextColor(getResources().getColor(R.color.white));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent i = new Intent(TransfertoAnotherBankActivity.this,AddPayeeTransferActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityTransfertoAnotherBankBinding.seekTransToBank.setProgress(0);
        activityTransfertoAnotherBankBinding.txtSeek.setTextColor(getResources().getColor(R.color.black));
    }
}
