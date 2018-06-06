package com.ample.dumi.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityTransferToYourBankBinding;

public class TransferToYourBankActivity extends AppCompatActivity {
    ActivityTransferToYourBankBinding activityTransferToYourBankBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTransferToYourBankBinding = DataBindingUtil.setContentView(this,R.layout.activity_transfer_to_your_bank);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        activityTransferToYourBankBinding.includeActionbar1.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activityTransferToYourBankBinding.seekTransToYourBank.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent i = new Intent(TransferToYourBankActivity.this,AddPayeeTransferActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityTransferToYourBankBinding.seekTransToYourBank.setProgress(0);
    }
}
