package com.ample.dumi.Fragments;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ample.dumi.Activity.ApplyLoanActivity;
import com.ample.dumi.R;
import com.ample.dumi.databinding.FragmentLoanBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanFragment extends Fragment {

    View view;
    FragmentLoanBinding fragmentLoanBinding;
    Context context;
    public LoanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentLoanBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_loan, container, false);
        view = fragmentLoanBinding.getRoot();
        context = getActivity();

        fragmentLoanBinding.lnrApplyLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ApplyLoanActivity.class);
                startActivity(i);
            }
        });
        return view;
    }

}
