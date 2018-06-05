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
import com.ample.dumi.databinding.ActivityTransfertoDummyBinding;

public class TransfertoDummyActivity extends AppCompatActivity {

    ActivityTransfertoDummyBinding activityTransfertoDummyBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTransfertoDummyBinding = DataBindingUtil.setContentView(this,R.layout.activity_transferto_dummy);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        activityTransfertoDummyBinding.includeActionbar1.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activityTransfertoDummyBinding.seekbarTransDumi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent i = new Intent(TransfertoDummyActivity.this,AddPayeeDumiTransferActivity.class);
                startActivity(i);
            }
        });
    }
}
