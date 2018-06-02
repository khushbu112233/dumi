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

    FragmentNameCardBinding fragmentNameCardBinding;
    View view;
    private Fragment fragment = null;

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

        fragmentNameCardBinding.imgList1Selected.setVisibility(View.VISIBLE);
        fragmentNameCardBinding.imgList2Selected.setVisibility(View.INVISIBLE);
        fragmentNameCardBinding.imgList3Selected.setVisibility(View.INVISIBLE);

        fragment = new NameCardList1Fragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.namecard_container_wrapper, fragment)
                .addToBackStack(null).commit();


        fragmentNameCardBinding.lnrList1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentNameCardBinding.imgList1Selected.setVisibility(View.VISIBLE);
                fragmentNameCardBinding.imgList2Selected.setVisibility(View.INVISIBLE);
                fragmentNameCardBinding.imgList3Selected.setVisibility(View.INVISIBLE);

                fragment = new NameCardList1Fragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.namecard_container_wrapper, fragment)
                        .addToBackStack(null).commit();

            }
        });

        fragmentNameCardBinding.lnrList2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentNameCardBinding.imgList1Selected.setVisibility(View.INVISIBLE);
                fragmentNameCardBinding.imgList2Selected.setVisibility(View.VISIBLE);
                fragmentNameCardBinding.imgList3Selected.setVisibility(View.INVISIBLE);

                fragment = new NameCardList2Fragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.namecard_container_wrapper, fragment)
                        .addToBackStack(null).commit();

            }
        });

        fragmentNameCardBinding.lnrList3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

}
