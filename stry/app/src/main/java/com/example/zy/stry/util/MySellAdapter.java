package com.example.zy.stry.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.RadioButton;
import com.example.zy.stry.R;
import com.example.zy.stry.entity.UserEntity;
import java.util.List;
import java.util.Objects;

/**
 * Created by zy on 15/7/8.
 */
public class MySellAdapter extends BaseAdapter{
    private List<String> ssa=null;
    private LayoutInflater inflater=null;
    public MySellAdapter(List<String> ssa,Context con){
        super();
        this.ssa=ssa;
        inflater=LayoutInflater.from(con);
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
        arg1=inflater.inflate(R.layout.activity_sell_page_value,null);
        TextView sell_page_class=(TextView)arg1.findViewById(R.id.sell_page_class);
        // RadioButton main_choose=(RadioButton)arg1.findViewById(R.id.main_choose);
        sell_page_class.setText(ssa.get(arg0));
        return arg1;
    }
}
