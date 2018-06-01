package com.ample.dumi.Activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ample.dumi.Fragments.LoanFragment;
import com.ample.dumi.R;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = new LoanFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, fragment)
                .addToBackStack(null).commit();

    }
}
