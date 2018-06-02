package com.ample.dumi.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityLoanHistoryBinding;

public class LoanHistoryActivity extends AppCompatActivity {

    ActivityLoanHistoryBinding activityLoanHistoryBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_history);
        activityLoanHistoryBinding.includeActionbar.txtActionBarTitle.setText(getResources().getString(R.string.loan_history));

    }

}
