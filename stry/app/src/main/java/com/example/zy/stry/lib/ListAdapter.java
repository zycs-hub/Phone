package com.example.zy.stry.lib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zy.stry.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by wendy on 15-9-9.
 */
public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mStrings;

    public ListAdapter(Context context, ArrayList<String> strings) {
        super();
        mContext=context;
        mStrings = strings;
    }

    private static class ViewHolder {
        TextView title;
    }


    @Override
    public View getView(int position, View rootView, ViewGroup parent) {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.activity_appbar_detail, parent, false);


//        ViewHolder viewHolder;
//        if(rootView == null){
////            viewHolder = new ViewHolder();
//            rootView = LayoutInflater.from(mContext).inflate(R.layout.activity_appbar_detail, parent, false);
////            viewHolder.title = (TextView) rootView.findViewById(R.id.title);
//
////            rootView.setTag(viewHolder);
//        }else{
////            viewHolder = (ViewHolder) rootView.getTag();
//        }

//        viewHolder.title.setText(mStrings.get(position));

        return rootView;
    }

    public int getCount()
    {
        // return the number of records in cursor
        return mStrings.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

}


