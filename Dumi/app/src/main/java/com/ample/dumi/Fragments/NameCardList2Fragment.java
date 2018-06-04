package com.ample.dumi.Fragments;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ample.dumi.R;
import com.ample.dumi.databinding.FragmentNameCardList2Binding;

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

        fragmentNameCardList2Binding.gridList2.setAdapter(new ImageAdapterGridView(getContext()));
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
}

