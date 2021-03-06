package com.ample.dumi.Activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ample.dumi.R;
import com.ample.dumi.Utils.util;
import com.ample.dumi.databinding.ActivityForgotBinding;

public class ForgotActivity extends AppCompatActivity {

    ActivityForgotBinding activityForgotBinding;
    private String email ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotBinding = DataBindingUtil.setContentView(this,R.layout.activity_forgot);

        activityForgotBinding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        activityForgotBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                email = activityForgotBinding.edtEmail.getText().toString();
                if(email.isEmpty())
                {
                    util.showAlert(ForgotActivity.this,getResources().getString(R.string.Email_empty));
                }
                else
                {
                   // new HttpAsyncTask().execute(Utility.BASE_URL+"ForgotPassword");
                }
            }
        });
    }
}
