package com.ample.dumi.Fragments;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ample.dumi.Adapter.GalleryAdapter;
import com.ample.dumi.R;
import com.ample.dumi.databinding.FragmentNameCardList1Binding;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NameCardList1Fragment extends Fragment {

    FragmentNameCardList1Binding fragmentNameCardList1Binding;
    private ArrayList<Integer> images;
    private GalleryAdapter mAdapter;
    View view;

    public NameCardList1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentNameCardList1Binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_name_card_list1, container, false);
        view = fragmentNameCardList1Binding.getRoot();

        images = new ArrayList<>();
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);
        layoutManager.setMaxVisibleItems(3);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        fragmentNameCardList1Binding.recyclerView.setLayoutManager(layoutManager);
        fragmentNameCardList1Binding.recyclerView.setHasFixedSize(true);
        mAdapter = new GalleryAdapter(getContext(), images);


        fragmentNameCardList1Binding.recyclerView.setAdapter(mAdapter);

        fragmentNameCardList1Binding.recyclerView.addOnScrollListener(new CenterScrollListener());
        images.add(R.drawable.loan_bal);
        images.add(R.drawable.loan_bal);
        images.add(R.drawable.loan_bal);
        images.add(R.drawable.loan_bal);
        images.add(R.drawable.loan_bal);
        images.add(R.drawable.loan_bal);
        images.add(R.drawable.loan_bal);

        fragmentNameCardList1Binding.lnrScanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddQRActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
