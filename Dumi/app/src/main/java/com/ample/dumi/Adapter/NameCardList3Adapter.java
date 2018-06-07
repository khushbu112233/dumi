package com.ample.dumi.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ample.dumi.Model.NameCard;
import com.ample.dumi.R;
import com.ample.dumi.Utils.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

public class NameCardList3Adapter extends BaseAdapter{

    private Context context;
    ArrayList<NameCard> nameCardArrayList = new ArrayList<>();
    private int layoutResourceId;
    private LayoutInflater mInflater;

    public NameCardList3Adapter(Context context, int grid_list3_layout, ArrayList<NameCard> nfcModel)
    {
        this.context = context ;
        this.layoutResourceId = grid_list3_layout ;
        this.nameCardArrayList = nfcModel ;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return nameCardArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return nameCardArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView = mInflater.inflate(R.layout.grid_list4_layout, viewGroup, false);
        ViewHolder holder = null;
        holder = new ViewHolder();
        holder.imageDesc = (TextView) rowView.findViewById(R.id.textList3);
        holder.imageName = (TextView) rowView.findViewById(R.id.textNameList3);
        holder.imageDesignation = (TextView) rowView.findViewById(R.id.textDescList3);
        holder.image = (CircleImageView) rowView.findViewById(R.id.imageList4);
        holder.progressBar1= (ProgressBar)rowView.findViewById(R.id.progressBar1);
        holder.fm_img = (FrameLayout) rowView.findViewById(R.id.fm_img);


        String name = nameCardArrayList.get(position).getName();
        String company = nameCardArrayList.get(position).getCompany();
        String email = nameCardArrayList.get(position).getEmail();
        String website = nameCardArrayList.get(position).getWebsite();
        String mobile = nameCardArrayList.get(position).getMob_no();
        String designation = nameCardArrayList.get(position).getDesignation();

        holder.imageName.setText(name);
        String desc = "";

        if (!company.trim().equalsIgnoreCase("")){
            desc += company;
        }
        if (!email.trim().equalsIgnoreCase("")){
            desc += "\n"+email;
        }
        if (!mobile.trim().equalsIgnoreCase("")){
            desc += "\n"+mobile;
        }
        if (!website.trim().equalsIgnoreCase("")){
            desc += "\n"+website;
        }
        holder.imageDesc.setText(desc);
        try
        {
            if (!designation.equalsIgnoreCase(""))
            {
                holder.imageDesignation.setText(designation);
            }
        }
        catch (Exception e){  }

        try
        {
            if(nameCardArrayList.get(position).getUser_image().equals(""))
            {
                //holder.image.setVisibility(View.GONE);
                holder.progressBar1.setVisibility(View.GONE);
                holder.image.setImageResource(R.drawable.usr);
            }
            else
            {
                //holder.progressBar1.setVisibility(View.VISIBLE);
                holder.image.setVisibility(View.VISIBLE);
                //holder.image.setImageResource(nameCardArrayList.get(position).getImage());

                final ViewHolder finalHolder = holder;
                Glide.with(context)
                        .asBitmap().load(Utility.BASE_IMAGE_URL+"UserProfile/"+nameCardArrayList.get(position).getUser_image())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                finalHolder.progressBar1.setVisibility(View.GONE);
                                finalHolder.image.setImageBitmap(resource);
                            }
                        });
            }
        }
        catch (Exception e)
        {
            holder.image.setImageResource(R.drawable.usr);
        }
        return rowView;
    }

    static class ViewHolder
    {
        TextView imageDesc;
        TextView imageName;
        TextView imageDesignation;
        CircleImageView image;
        ProgressBar progressBar1;
        FrameLayout fm_img;

    }
}
