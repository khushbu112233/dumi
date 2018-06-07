package com.ample.dumi.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.ample.dumi.Activity.MainActivity;
import com.ample.dumi.Activity.AddQRActivity;
import com.ample.dumi.Adapter.GalleryAdapter;
import com.ample.dumi.Model.NameCard;
import com.ample.dumi.R;
import com.ample.dumi.Utils.Utility;
import com.ample.dumi.Utils.util;
import com.ample.dumi.databinding.FragmentNameCardList1Binding;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.ample.dumi.Utils.Utility.POST2;

/**
 * A simple {@link Fragment} subclass.
 */
public class NameCardList1Fragment extends Fragment {

    FragmentNameCardList1Binding fragmentNameCardList1Binding;
    private ArrayList<Integer> images;
    private GalleryAdapter mAdapter;
    View view;
    public static ArrayList<NameCard> allTags;

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
        allTags = new ArrayList<>();


        new HttpAsyncTask().execute(Utility.BASE_URL+"GetFriendConnection");
        //mAdapter = new GalleryAdapter(getContext(), images);

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

    public class HttpAsyncTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            String loading = "Fetching cards" ;
            dialog = new ProgressDialog(getContext());
            dialog.setMessage(loading);
            //dialog.setTitle("Saving Reminder");
            dialog.show();
            dialog.setCancelable(false);

        }

        @Override
        protected String doInBackground(String... urls) {
            JSONObject jsonObject = new JSONObject();
            try {

                    jsonObject.accumulate("Type", "desc");

                    jsonObject.accumulate("numofrecords", "1000");
//            jsonObject.accumulate("pageno", pageno);
                    jsonObject.accumulate("pageno", "1");
                    jsonObject.accumulate("userid", "5");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return POST2(urls[0],jsonObject);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result)
        {
//            customProgressBarStatus = "stop";
          /*  String status = "false" ;
            String loading = "Fetching Cards..." ;
            CustomProgressBar(loading, status);*/
            dialog.dismiss();

            try
            {
                if (result != null)
                {
                    JSONObject jsonObject = new JSONObject(result);

                    //Toast.makeText(getContext(), jsonArray.toString(), Toast.LENGTH_LONG).show();
//                    nfcModel.clear();
                    allTags.clear();

                    JSONArray jsonArray;

                        jsonArray = jsonObject.getJSONArray("connection");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        //  Toast.makeText(getContext(), object.getString("Card_Back"), Toast.LENGTH_LONG).show();

                        NameCard nfcModelTag = new NameCard();
                        nfcModelTag.setName(object.getString("FirstName") + " " + object.getString("LastName"));
                        nfcModelTag.setCompany(object.getString("CompanyName"));
                        nfcModelTag.setEmail(object.getString("UserName"));
                        nfcModelTag.setWebsite(object.getString("Website"));
                        nfcModelTag.setPh_no(object.getString("Phone"));
                        nfcModelTag.setDesignation(object.getString("Designation"));
                        nfcModelTag.setCard_front(object.getString("Card_Front"));
                        nfcModelTag.setCard_back(object.getString("Card_Back"));
                        nfcModelTag.setUser_image(object.getString("UserPhoto"));
                        nfcModelTag.setProfile_id(object.getString("ProfileId"));
                        //nfcModelTag.setDateInitiated(object.getString("DateInitiated"));
                        nfcModelTag.setAddress(object.getString("Address1") + " " + object.getString("Address2")
                                + " " + object.getString("Address3") + " " + object.getString("Address4"));
//                        Toast.makeText(getActivity(),"Profile_id"+object.getString("ProfileId"),Toast.LENGTH_SHORT).show();
                      //  nfcModelTag.setLatitude(object.getString("Latitude"));
                      //  nfcModelTag.setLongitude(object.getString("Longitude"));
                        allTags.add(nfcModelTag);

                    }
                    mAdapter = new GalleryAdapter(getContext(), allTags);
                    fragmentNameCardList1Binding.recyclerView.setAdapter(mAdapter);

                }
                else
                {
                    Toast.makeText(getContext(), "Not able to load Cards..", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
