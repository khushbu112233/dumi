package com.ample.dumi.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ample.dumi.Adapter.LoanHistoryAdapter;
import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityLoanHistoryBinding;

public class LoanHistoryActivity extends AppCompatActivity {

    ActivityLoanHistoryBinding activityLoanHistoryBinding;
    LoanHistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLoanHistoryBinding = DataBindingUtil.setContentView(this,R.layout.activity_loan_history);
        activityLoanHistoryBinding.includeActionbar1.txtActionBarTitle.setText(getResources().getString(R.string.loan_history));
        adapter = new LoanHistoryAdapter(LoanHistoryActivity.this);
        activityLoanHistoryBinding.lstLoanHistory.setAdapter(adapter);
        activityLoanHistoryBinding.includeActionbar1.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
