package com.ample.dumi.Activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ample.dumi.Fragments.LoanFragment;
import com.ample.dumi.Fragments.NameCardFragment;
import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment = null;
    public static ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        fragment = new LoanFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, fragment)
                .addToBackStack(null).commit();

        activityMainBinding.includeTabbar.lnrTabLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMainBinding.includeTabbar.imgLoanSelected.setVisibility(View.VISIBLE);
                activityMainBinding.includeTabbar.imgNameSelected.setVisibility(View.INVISIBLE);

                fragment = new LoanFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, fragment)
                            .addToBackStack(null)
                            .commit();

                setActionBarTitle("Loan");
            }
        });


        activityMainBinding.includeTabbar.lnrTabNameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMainBinding.includeTabbar.imgLoanSelected.setVisibility(View.INVISIBLE);
                activityMainBinding.includeTabbar.imgNameSelected.setVisibility(View.VISIBLE);

                fragment = new NameCardFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, fragment)
                        .addToBackStack(null)
                        .commit();
                setActionBarTitle("Name Cards");
            }
        });
    }

    public static void setActionBarTitle(String title) {
        activityMainBinding.includeActionbar.txtActionBarTitle.setText(title);

    }
}
