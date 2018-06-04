package com.ample.dumi.Fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ample.dumi.Adapter.NameCardList3Adapter;
import com.ample.dumi.Model.NameCard;
import com.ample.dumi.R;
import com.ample.dumi.databinding.FragmentNameCardList3Binding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NameCardList3Fragment extends Fragment {

    FragmentNameCardList3Binding fragmentNameCardList3Binding;
    View view;
    public static NameCardList3Adapter gridAdapter;
    public static ArrayList<NameCard> arrayNameCard ;

    public NameCardList3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentNameCardList3Binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_name_card_list3, container, false);
        view = fragmentNameCardList3Binding.getRoot();
        arrayNameCard = new ArrayList<>();
        for (int i = 0; i < 6 ; i++) {
            NameCard nfcModelTag = new NameCard();
            nfcModelTag.setName("Physicialn ong");
            nfcModelTag.setCompany("TCMOng Medicare pvt ltd");
            nfcModelTag.setEmail("physicial.com.sg");
            nfcModelTag.setWebsite("www.tcmong.com.sg");
            nfcModelTag.setMob_no("+65 6842 6188");
            nfcModelTag.setDesignation("");
            nfcModelTag.setImage(R.drawable.usr);
            nfcModelTag.setAddress("A:60 Pava Labor Road, #09-06 Pava Lebar Square, Singapore");
            arrayNameCard.add(nfcModelTag);
        }

        gridAdapter = new NameCardList3Adapter(getActivity(), R.layout.grid_list4_layout, arrayNameCard);
        fragmentNameCardList3Binding.list3.setAdapter(gridAdapter);
        return view;

    }

}
