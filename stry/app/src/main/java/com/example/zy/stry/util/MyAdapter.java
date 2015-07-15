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
public class MyAdapter extends BaseAdapter{
    private List<UserEntity> ssa=null;
    private LayoutInflater inflater=null;
    public MyAdapter(List<UserEntity> ssa,Context con){
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
        arg1=inflater.inflate(R.layout.main_page_list_value,null);
        TextView main_page_1_class=(TextView)arg1.findViewById(R.id.main_page_1_class);
        RadioButton main_choose=(RadioButton)arg1.findViewById(R.id.main_choose);
        main_page_1_class.setText(ssa.get(arg0).getBook());
        return arg1;
    }
}
