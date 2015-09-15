package com.example.zy.stry.lib;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zy.stry.BookDetailActivity;
import com.example.zy.stry.R;
import com.example.zy.stry.entity.SellEntity;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wendy on 15-9-9.
 */
public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<SellEntity.SellBook> mData;


    public ListAdapter(Context context,ArrayList<SellEntity.SellBook> data) {
        super();
        mContext=context;
        mData = data;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView =  LayoutInflater.from(mContext).inflate(R.layout.item, null);
//            convertView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(mData.get(position).bookname);
        viewHolder.price.setText(Integer.toString(mData.get(position).price));

        return convertView;
    }


    public int getCount()
    {
        // return the number of records in cursor
        return mData.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mData.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    class ViewHolder{
        TextView title;
        TextView price;
    }


}


