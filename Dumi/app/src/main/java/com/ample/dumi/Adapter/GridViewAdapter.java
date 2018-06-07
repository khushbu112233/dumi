package com.ample.dumi.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ample.dumi.Model.NameCard;
import com.ample.dumi.R;
import com.ample.dumi.Utils.LoginSession;
import com.ample.dumi.Utils.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.dd.ShadowLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class GridViewAdapter extends BaseSwipeAdapter
{
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    int posi ;
    private LayoutInflater inflater;

    ArrayList<NameCard> nfcModelList1 = new ArrayList<>();
    private Fragment fragment;


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public GridViewAdapter(Context context, int grid_list3_layout, ArrayList<NameCard> nfcModel)
    {
        this.context = context;
        this.layoutResourceId = grid_list3_layout;
        this.nfcModelList1 = nfcModel;
//        this.nfcModelListFilter = new ArrayList<NFCModel>();
        inflater = LayoutInflater.from(context);

    }

    @Override
    public View generateView(final int position, ViewGroup parent)
    {
        Utility.freeMemory();
        View v = LayoutInflater.from(context).inflate(R.layout.grid_list2_layout, null);

        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                //YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        swipeLayout.setSwipeEnabled(false);


        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                //  Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });

        v.findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                db.DeactiveCards(nfcModelList.get(position).getId());
                swipeLayout.close();

                posi  = position ;
//                Toast.makeText(context, "Friend Profile ID: "+ nfcModelList1.get(posi).getProfile_id(), Toast.LENGTH_SHORT).show();


               /* AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.Blue_AlertDialog);
                alert.setMessage("Do you want to Delete this Profile?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        dialog.dismiss();
                        new HttpAsyncTask().execute(Utility.BASE_URL+"FriendConnection_Operation");
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();*/


            }
        });

        return v;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void fillValues(final int position, View convertView) {
        View row = convertView;
        ViewHolder holder = null;

        holder = new ViewHolder();
        holder.imageTitle = (TextView) row.findViewById(R.id.text);
        holder.image = (ImageView) row.findViewById(R.id.image);
        holder.defaultCard = (ShadowLayout) row.findViewById(R.id.rltDefaultCard);
        holder.tvPersonName = (TextView) row.findViewById(R.id.tvPersonName);
        holder.tvPersonProfile = (TextView) row.findViewById(R.id.tvPersonProfile);
        holder.tvPersonWebsite = (TextView) row.findViewById(R.id.tvPersonWebsite);
        holder.tvPersonAddress = (TextView) row.findViewById(R.id.tvPersonAddress);
        holder.tvPersonContact = (TextView) row.findViewById(R.id.tvPersonMobile);
        holder.tvPersonNameLast = (TextView) row.findViewById(R.id.tvPersonNameLast);
        holder.fm_img = (ShadowLayout) row.findViewById(R.id.fm_img);
        holder.progressBar1 = (ProgressBar)row.findViewById(R.id.progressBar1);
        holder.rltDefaultCard = (ShadowLayout) row.findViewById(R.id.rltDefaultCard);
        holder.rldfl = (RelativeLayout)row.findViewById(R.id.rldfl);
        holder.imgDefault = (ImageView)row.findViewById(R.id.imgDefault);
        holder.v1 =(View)row.findViewById(R.id.v1);

        row.setTag(holder);

        if (nfcModelList1.get(position).getCard_front().equals(""))
        {
            holder.image.setVisibility(View.GONE);
            holder.fm_img.setVisibility(View.GONE);
            holder.progressBar1.setVisibility(View.GONE);
            holder.defaultCard.setVisibility(View.VISIBLE);
            try
            {
                try {
                    if (nfcModelList1.get(position).getDesignation().trim().equals("") || nfcModelList1.get(position).getDesignation().equals("null")
                            || nfcModelList1.get(position).getDesignation() == null) {
                        holder.tvPersonProfile.setVisibility(View.GONE);
                    }
                }catch (Exception e){}

                try {
                    if (nfcModelList1.get(position).getEmail().trim().equals("") || nfcModelList1.get(position).getEmail().equals("null")
                            || nfcModelList1.get(position).getEmail() == null) {
                        holder.tvPersonWebsite.setVisibility(View.GONE);
                    }
                }catch (Exception e){}

                try {
                    if (nfcModelList1.get(position).getAddress().trim().equals("") || nfcModelList1.get(position).getAddress().equalsIgnoreCase("null")
                            || nfcModelList1.get(position).getAddress() == null) {
                        holder.tvPersonAddress.setVisibility(View.INVISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    if (nfcModelList1.get(position).getPh_no().trim().equals("") || nfcModelList1.get(position).getPh_no().equals("null")
                            || nfcModelList1.get(position).getPh_no() == null) {
                        holder.tvPersonContact.setVisibility(View.INVISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
               /* if (nfcModelList.get(position).getMob_no().equals("") || nfcModelList.get(position).getMob_no().equals("null")
                        || nfcModelList.get(position).getMob_no() == null) {
                    tvPersonMobile.setVisibility(View.GONE);
                }*/
            }
            catch (Exception e){
                e.printStackTrace();
            }

            String name = nfcModelList1.get(position).getName();
            String kept = name.substring(0, name.indexOf(" "));
            String remainder = name.substring(name.indexOf(" ") + 1, name.length());
            holder.tvPersonName.setText(kept);
            holder.tvPersonNameLast.setText(remainder);
            if(nfcModelList1.get(position).getEmail().equalsIgnoreCase("")||nfcModelList1.get(position).getEmail()!=null||nfcModelList1.get(position).getEmail()!="null") {
                if (nfcModelList1.get(position).getEmail().contains("circle8")) {
                    holder.rldfl.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    holder.tvPersonWebsite.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tvPersonProfile.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tvPersonAddress.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tvPersonContact.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tvPersonName.setTextColor(context.getResources().getColor(R.color.white));
                    holder.tvPersonNameLast.setTextColor(context.getResources().getColor(R.color.white));
                    holder.imgDefault.setColorFilter(context.getResources().getColor(R.color.white));
                    holder.v1.setBackgroundColor(context.getResources().getColor(R.color.white));
                } else {
                    holder.rldfl.setBackgroundColor(context.getResources().getColor(R.color.black));
                    holder.tvPersonWebsite.setTextColor(context.getResources().getColor(R.color.black));
                    holder.tvPersonProfile.setTextColor(context.getResources().getColor(R.color.black));
                    holder.tvPersonAddress.setTextColor(context.getResources().getColor(R.color.black));
                    holder.tvPersonContact.setTextColor(context.getResources().getColor(R.color.black));
                    holder.imgDefault.setColorFilter(context.getResources().getColor(R.color.black));
                    holder.tvPersonName.setTextColor(context.getResources().getColor(R.color.black));
                    holder.tvPersonNameLast.setTextColor(context.getResources().getColor(R.color.black));
                    holder.v1.setBackgroundColor(context.getResources().getColor(R.color.black));
                }
            }else
            {
                holder.rldfl.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                holder.tvPersonWebsite.setTextColor(context.getResources().getColor(R.color.white));
                holder.tvPersonProfile.setTextColor(context.getResources().getColor(R.color.white));
                holder.tvPersonAddress.setTextColor(context.getResources().getColor(R.color.white));
                holder.tvPersonContact.setTextColor(context.getResources().getColor(R.color.white));
                holder.imgDefault.setColorFilter(context.getResources().getColor(R.color.white));
                holder.tvPersonName.setTextColor(context.getResources().getColor(R.color.white));
                holder.tvPersonNameLast.setTextColor(context.getResources().getColor(R.color.white));
                holder.v1.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
            holder.tvPersonProfile.setText(nfcModelList1.get(position).getDesignation());
            holder.tvPersonWebsite.setText("E : " + nfcModelList1.get(position).getEmail());
            holder.tvPersonAddress.setText("A : " + nfcModelList1.get(position).getAddress());
            holder.tvPersonContact.setText("M : " + nfcModelList1.get(position).getPh_no());


        }
        else
        {
            holder.fm_img.setVisibility(View.VISIBLE);
            holder.image.setVisibility(View.VISIBLE);
            holder.progressBar1.setVisibility(View.VISIBLE);
            holder.defaultCard.setVisibility(View.GONE);
            //imageView.setImageResource(nfcModelList.get(position).getCard_front());
            //   ImageLoader.getInstance().displayImage(Utility.BASE_IMAGE_URL+"Cards/" + nfcModelList1.get(position).getCard_front(), holder.image, options, animateFirstListener);
            final ViewHolder finalHolder = holder;
            Glide.with(context)
                    .asBitmap().load(Utility.BASE_IMAGE_URL+"Cards/" + nfcModelList1.get(position).getCard_front())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            finalHolder.progressBar1.setVisibility(View.GONE);
                            finalHolder.image.setImageBitmap(resource);
                        }
                    });

            //  Picasso.with(context).load(Utility.BASE_IMAGE_URL+"Cards/" + nfcModelList1.get(position).getCard_front()).resize(400,280).onlyScaleDown().skipMemoryCache().into(holder.image);
        }

        // holder.imageTitle.setText(name);
        // Bitmap bmp = BitmapFactory.decodeByteArray(nfcModelList.get(position).getCard_front(), 0, nfcModelList.get(position).getCard_front().length);
        // ImageView image = (ImageView) findViewById(R.id.imageView1);

//        holder.image.setImageResource(nfcModelList.get(position).getCard_front());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                CardDetail.profile_id = nfcModelList1.get(position).getProfile_id();
                CardDetail.DateInitiated = nfcModelList1.get(position).getDateInitiated();
                CardDetail.lat = nfcModelList1.get(position).getLatitude();
                CardDetail.lon = nfcModelList1.get(position).getLongitude();
                fragment = new CardDetail();
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, fragment)
                        .addToBackStack(null)
                        .commit();
                // finish();*/
            }
        });


        holder.defaultCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*CardDetail.profile_id = nfcModelList1.get(position).getProfile_id();
                CardDetail.DateInitiated = nfcModelList1.get(position).getDateInitiated();
                CardDetail.lat = nfcModelList1.get(position).getLatitude();
                CardDetail.lon = nfcModelList1.get(position).getLongitude();
                fragment = new CardDetail();
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, fragment)
                        .addToBackStack(null)
                        .commit();*/
                // finish();
            }
        });
    }

    @Override
    public int getCount() {
        return nfcModelList1.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        View v1;
        ImageView imgDefault;
        ShadowLayout rltDefaultCard;
        RelativeLayout rldfl;
        TextView imageTitle;
        ShadowLayout fm_img;
        ImageView image;
        ShadowLayout defaultCard;
        ProgressBar progressBar1;
        TextView tvPersonName, tvPersonProfile, tvPersonWebsite, tvPersonAddress, tvPersonContact, tvPersonNameLast;

    }

   /* public void Filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        nfcModelList1.clear();

        if (charText.length() == 0)
        {
            nfcModelList1.addAll(nfcModelListFilter1);
        }
        else
        {
            for (FriendConnection md : nfcModelListFilter1)
            {
                if (md.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    nfcModelList1.add(md);
                }
                CardsActivity.setActionBarTitle("Cards - " + nfcModelList1.size() + "/"+ CardsActivity.Connection_Limit);
            }
        }
        notifyDataSetChanged();
    }*/

}
