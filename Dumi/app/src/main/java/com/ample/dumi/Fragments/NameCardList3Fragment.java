package com.ample.dumi.Fragments;


import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.ample.dumi.Adapter.NameCardList3Adapter;
import com.ample.dumi.Model.NameCard;
import com.ample.dumi.R;
import com.ample.dumi.Utils.Utility;
import com.ample.dumi.Utils.util;
import com.ample.dumi.databinding.FragmentNameCardList3Binding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ample.dumi.Utils.Utility.POST2;

/**
 * A simple {@link Fragment} subclass.
 */
public class NameCardList3Fragment extends Fragment {

    FragmentNameCardList3Binding fragmentNameCardList3Binding;
    View view;
    public static NameCardList3Adapter gridAdapter;
    public static ArrayList<NameCard> arrayNameCard ;
    public static int pageno = 1 ;
    static int numberCount, listSize;
    public static String count, counts;
    public static ArrayList<NameCard> allTaggs;

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
        allTaggs = new ArrayList<>();
        pageno = 1;
        gridAdapter = new NameCardList3Adapter(getActivity(), R.layout.grid_list4_layout, allTaggs);
        fragmentNameCardList3Binding.list3.setAdapter(gridAdapter);
        new HttpAsyncTask().execute(Utility.BASE_URL+"GetFriendConnection");

        return view;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String>
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

                jsonObject.accumulate("numofrecords", "10");
//            jsonObject.accumulate("pageno", pageno);
                jsonObject.accumulate("pageno", pageno);
                jsonObject.accumulate("userid", "5");
                pageno++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return POST2(urls[0],jsonObject);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result)
        {
            dialog.dismiss();
            try
            {
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);
//                    numberCount = Integer.parseInt(jsonObject.getString("count")) ;
                    count = jsonObject.getString("count");

                    if (pageno == 2) {
                        allTaggs.clear();
                        counts = jsonObject.getString("count");
                    }
                    if (count.equals("") || count.equals("null")) {
                        numberCount = 0;
                    } else {
                        numberCount = Integer.parseInt(count);
                    }

                    JSONArray jsonArray;
                    jsonArray = jsonObject.getJSONArray("connection");
                    //Toast.makeText(getContext(), jsonArray.toString(), Toast.LENGTH_LONG).show();
//                    numberCount = jsonArray.length();
                    fragmentNameCardList3Binding.rlLoadMore.setVisibility(View.GONE);

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        //  Toast.makeText(getContext(), object.getString("Card_Back"), Toast.LENGTH_LONG).show();

                        NameCard nfcModelTag = new NameCard();
                        nfcModelTag.setName(object.getString("FirstName") + " " + object.getString("LastName"));
                        nfcModelTag.setCompany(object.getString("CompanyName"));
                        nfcModelTag.setEmail(object.getString("UserName"));
                        nfcModelTag.setWebsite("");
                        nfcModelTag.setMob_no(object.getString("Phone"));
                        nfcModelTag.setDesignation(object.getString("Designation"));
                        nfcModelTag.setCard_front(object.getString("Card_Front"));
                        nfcModelTag.setCard_back(object.getString("Card_Back"));
                        nfcModelTag.setUser_image(object.getString("UserPhoto"));
                        nfcModelTag.setProfile_id(object.getString("ProfileId"));
                        //nfcModelTag.setNfc_tag("en000000001");
                        nfcModelTag.setAddress(object.getString("Address1") + " " + object.getString("Address2") + " "
                                + object.getString("Address3") + object.getString("Address4"));
                       // nfcModelTag.setDateInitiated(object.getString("DateInitiated"));
                       // nfcModelTag.setLatitude(object.getString("Latitude"));
                        //nfcModelTag.setLongitude(object.getString("Longitude"));
                        allTaggs.add(nfcModelTag);

                    }
                    Log.e("allTaggs",""+allTaggs.size());

                    gridAdapter.notifyDataSetChanged();

                    listSize = allTaggs.size();

                    fragmentNameCardList3Binding.list3.setOnScrollListener(new AbsListView.OnScrollListener()
                    {
                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState)
                        {
                            // TODO Auto-generated method stub

                            //progressStatus = "LOAD MORE";

                            int threshold = 1;
                            int count = fragmentNameCardList3Binding.list3.getCount();

                            if (scrollState == SCROLL_STATE_IDLE)
                            {
                                if (listSize <= numberCount)
                                {
                                    if (fragmentNameCardList3Binding.list3.getLastVisiblePosition() >= count - threshold)
                                    {
                                        //  rlLoadMore.setVisibility(View.VISIBLE);
                                        // Execute LoadMoreDataTask AsyncTask
                                        //CallApi();
                                        new HttpAsyncTask().execute(Utility.BASE_URL+"GetFriendConnection");
                                    }
                                } else {

                                }
                            }
                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem,
                                             int visibleItemCount, int totalItemCount) {
                            // TODO Auto-generated method stub

                        }
                    });

                } else {
                    Toast.makeText(getContext(), "Not able to load Cards..", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
