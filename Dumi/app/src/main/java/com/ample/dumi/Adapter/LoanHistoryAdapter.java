package com.ample.dumi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ample.dumi.R;


public class LoanHistoryAdapter extends BaseAdapter
{
    Context context ;
    private static LayoutInflater inflater = null;

    public LoanHistoryAdapter(Context applicationContext)
    {
        this.context = applicationContext ;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder
    {
        TextView tvEventName ;
        ImageView ivCancel ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view = convertView ;
        ViewHolder holder = null;

        if( view == null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.loan_history_adapter_layout, null);
            holder = new ViewHolder();

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)view.getTag();
        }


        return view ;
    }
}
