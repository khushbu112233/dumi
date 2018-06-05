package com.ample.dumi.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ample.dumi.Fragments.LoanFragment;
import com.ample.dumi.Fragments.NameCardFragment;
import com.ample.dumi.R;
import com.ample.dumi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment = null;
    public static ActivityMainBinding activityMainBinding;
    boolean doubleBackToExitPressedOnce = false;

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
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container_wrapper, fragment)
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
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container_wrapper, fragment)
                        .addToBackStack(null)
                        .commit();
                setActionBarTitle("Name Cards");
            }
        });
    }

    public static void setActionBarTitle(String title) {
        activityMainBinding.includeActionbar.txtActionBarTitle.setText(title);

    }

    public Fragment getCurrentFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container_wrapper);
        //    Log.e("currentFragment",""+currentFragment);
        return currentFragment;

    }

    @Override
    public void onBackPressed() {
        if (getCurrentFragment() instanceof LoanFragment){
            if (doubleBackToExitPressedOnce) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                finish();

            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }

        else if (getCurrentFragment() instanceof NameCardFragment){

            activityMainBinding.includeTabbar.imgLoanSelected.setVisibility(View.VISIBLE);
            activityMainBinding.includeTabbar.imgNameSelected.setVisibility(View.INVISIBLE);

            fragment = new LoanFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container_wrapper, fragment)
                    .addToBackStack(null)
                    .commit();

            setActionBarTitle("Loan");
        }
    }
}
