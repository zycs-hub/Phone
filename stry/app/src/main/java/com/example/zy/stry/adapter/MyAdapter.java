package com.example.zy.stry.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.RadioButton;
import com.example.zy.stry.R;
import com.example.zy.stry.entity.BookEntity;
import java.util.List;
import java.util.Objects;

/**
 * Created by zy on 15/7/8.
 */
public class MyAdapter extends BaseAdapter{
    private List<BookEntity> ssa=null;
    private LayoutInflater inflater=null;
    Context con=null;
    ListView ls=null;
    public MyAdapter(List<BookEntity> ssa,Context con,ListView ls){
        super();
        this.ssa=ssa;
        inflater=LayoutInflater.from(con);
        this.con=con;
        this.ls=ls;
    }
    @Override
    public int getCount(){
        int param=0;
        if(ssa!=null){
            param=ssa.size();
        }
        return param;
    }
    @Override
    public Objects getItem(int arg0){
        return null;
    }
    @Override
    public long getItemId(int arg0){
        return 0;
    }
    public View getView(int arg0,View arg1,ViewGroup arg2){
        arg1=inflater.inflate(R.layout.main_page_list_value,null);
        TextView main_page_1_class=(TextView)arg1.findViewById(R.id.main_page_1_class);
        main_page_1_class.setText(ssa.get(arg0).coursename);
        updateBackground(arg0, main_page_1_class );
        return arg1;
    }
    public void updateBackground(int position, View view) {
        int backgroundId;
        if (ls.isItemChecked(position)) {
            view.setBackgroundResource(R.color.primary_500);
        } else {
            view.setBackgroundResource(R.drawable.white_bankground);
        }
    }

    }
