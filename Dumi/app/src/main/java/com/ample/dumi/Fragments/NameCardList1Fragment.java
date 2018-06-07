package com.ample.dumi.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ample.dumi.Activity.AddQRActivity;
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

        fragmentNameCardList1Binding.lnrTapCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NfcManager manager = (NfcManager) getContext().getSystemService(Context.NFC_SERVICE);
                NfcAdapter adapter = manager.getDefaultAdapter();
                if (adapter != null && adapter.isEnabled()) {
                    Toast.makeText(getContext(), "You can scan CircleOne NFC-Cards by holding them behind NFC sensor of your Android device. It could be either on top, middle or bottom.", Toast.LENGTH_LONG).show();
                    //Yes NFC available
                }
                else if (adapter != null && !adapter.isEnabled()){


                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Please ensure that NFC has been enabled under your phone settings. You will still be able to add contacts with the QR scan feature if your device isn't NFC capable.");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Settings",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getContext(), "Please activate NFC and press Back to return to the application!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
                else{
                    Toast.makeText(getContext(), "Your device does not support NFC. You will still be able to add contacts with the QR scan feature if your device isn't NFC capable.", Toast.LENGTH_LONG).show();
                    //Your device doesn't support NFC
                }
            }
        });
        return view;
    }

}
