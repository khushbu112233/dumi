package com.ample.dumi.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.ample.dumi.Adapter.GalleryAdapter;
import com.ample.dumi.Adapter.GridViewAdapter;
import com.ample.dumi.Model.NameCard;
import com.ample.dumi.R;
import com.ample.dumi.Utils.Utility;
import com.ample.dumi.Utils.util;
import com.ample.dumi.databinding.FragmentNameCardList2Binding;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import static com.ample.dumi.Utils.Utility.POST2;

/**
 * A simple {@link Fragment} subclass.
 */
public class NameCardList2Fragment extends Fragment {

    Integer[] imageIDs = {
            R.drawable.loan_bal, R.drawable.loan_bal, R.drawable.loan_bal,
            R.drawable.loan_bal, R.drawable.loan_bal, R.drawable.loan_bal,
            R.drawable.loan_bal, R.drawable.loan_bal, R.drawable.loan_bal,
            R.drawable.loan_bal, R.drawable.loan_bal, R.drawable.loan_bal,
            R.drawable.loan_bal, R.drawable.loan_bal, R.drawable.loan_bal,
            R.drawable.loan_bal, R.drawable.loan_bal, R.drawable.loan_bal
    };
    FragmentNameCardList2Binding fragmentNameCardList2Binding;
    View view;
    public static int pageno = 1;
    public static ArrayList<NameCard> allTaggs;
    static int numberCount, gridSize;
    public static String count, counts;
    private GridViewAdapter gridAdapter;

    public NameCardList2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentNameCardList2Binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_name_card_list2, container, false);
        view = fragmentNameCardList2Binding.getRoot();
        pageno = 1;
        allTaggs = new ArrayList<>();
        gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_list2_layout, allTaggs);
        fragmentNameCardList2Binding.gridList2.setAdapter(gridAdapter);
        new HttpAsyncTask().execute(Utility.BASE_URL+"GetFriendConnection");
        //fragmentNameCardList2Binding.gridList2.setAdapter(new ImageAdapterGridView(getContext()));
        return view;

    }

    public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        public ImageAdapterGridView(Context c) {
            mContext = c;
        }

        public int getCount() {
            return imageIDs.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
               // mImageView.setPadding(16, 16, 16, 16);
            } else {
                mImageView = (ImageView) convertView;
            }
            mImageView.setImageResource(imageIDs[position]);
            return mImageView;
        }
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
            try {
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);

                    count = jsonObject.getString("count");

                    if (pageno == 2) {
                        counts = jsonObject.getString("count");
                    }
                    if (count.equals("") || count.equals("null")) {
                        numberCount = 0;
                    } else {
                        numberCount = Integer.parseInt(count);
                    }
                    JSONArray jsonArray;

                    jsonArray = jsonObject.getJSONArray("connection");

                    fragmentNameCardList2Binding.rlLoadMore.setVisibility(View.GONE);

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
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
                       // nfcModelTag.setNfc_tag("en000000001");
                        //nfcModelTag.setLatitude(object.getString("Latitude"));
                       // nfcModelTag.setLongitude(object.getString("Longitude"));
                        allTaggs.add(nfcModelTag);

                    }
                    gridAdapter.notifyDataSetChanged();
                    gridSize = allTaggs.size();

                    fragmentNameCardList2Binding.gridList2.setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState)
                        {
                            // TODO Auto-generated method stub

                            //progressStatus = "LOAD MORE";

                            int threshold = 1;
                            int count = fragmentNameCardList2Binding.gridList2.getCount();

                            if (scrollState == SCROLL_STATE_IDLE) {
                                if (gridSize <= numberCount) {
                                    if (fragmentNameCardList2Binding.gridList2.getLastVisiblePosition() >= count - threshold) {
                                        // rlLoadMore.setVisibility(View.VISIBLE);
                                        // Execute LoadMoreDataTask AsyncTask
                                        // CallApi();
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

