package com.ample.dumi.Activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityApplyLoan2Binding;
import com.github.gcacace.signaturepad.views.SignaturePad;

/**
 * Created by khushbu987 on 6/1/2018.
 */

public class ApplyLoanActivity2 extends AppCompatActivity {
    ActivityApplyLoan2Binding activityApplyLoan2Binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityApplyLoan2Binding = DataBindingUtil.setContentView(this, R.layout.activity_apply_loan2);
        activityApplyLoan2Binding.signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {

            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });
        activityApplyLoan2Binding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap signatureBitmap = activityApplyLoan2Binding.signaturePad.getSignatureBitmap();
                Toast.makeText(ApplyLoanActivity2.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                Log.e("bitmap",""+signatureBitmap);
            }
        });
    }
}
