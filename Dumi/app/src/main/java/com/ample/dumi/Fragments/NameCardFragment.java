package com.ample.dumi.Fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ample.dumi.Adapter.GalleryAdapter;
import com.ample.dumi.R;
import com.ample.dumi.databinding.FragmentNameCardBinding;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NameCardFragment extends Fragment {

    private ArrayList<Integer> images;
    private GalleryAdapter mAdapter;
    FragmentNameCardBinding fragmentNameCardBinding;
    View view;

    public NameCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentNameCardBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_name_card, container, false);
        view = fragmentNameCardBinding.getRoot();
        images = new ArrayList<>();
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);
        layoutManager.setMaxVisibleItems(3);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        fragmentNameCardBinding.recyclerView.setLayoutManager(layoutManager);
        fragmentNameCardBinding.recyclerView.setHasFixedSize(true);
        mAdapter = new GalleryAdapter(getContext(), images);


        fragmentNameCardBinding.recyclerView.setAdapter(mAdapter);

        fragmentNameCardBinding.recyclerView.addOnScrollListener(new CenterScrollListener());
        images.add(R.drawable.loan_bal);
        images.add(R.drawable.loan_bal);
        images.add(R.drawable.loan_bal);
        images.add(R.drawable.loan_bal);
        images.add(R.drawable.loan_bal);
        images.add(R.drawable.loan_bal);
        images.add(R.drawable.loan_bal);

        return view;
    }

}
