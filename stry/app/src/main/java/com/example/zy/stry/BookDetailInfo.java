package com.example.zy.stry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.zy.stry.entity.SellBook;

/**
 * Created by zy on 2015/9/16.
 */
public class BookDetailInfo extends Fragment {
    private FloatingActionButton mFabButton;


    public static BookDetailInfo newInstance(SellBook info) {
        Bundle args = new Bundle();
        BookDetailInfo fragment = new BookDetailInfo();
        args.putSerializable("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_info, null);
        SellBook book =(SellBook) getArguments().getSerializable("info");
        TextView tname=(TextView)view.findViewById(R.id.s_tname);
        TextView tprice=(TextView)view.findViewById(R.id.s_tprice);
        TextView tpublisher=(TextView)view.findViewById(R.id.s_tpublisher);
        TextView tdamage=(TextView)view.findViewById(R.id.s_tdamage);
        TextView towner=(TextView)view.findViewById(R.id.s_towner);
        TextView tmajor=(TextView)view.findViewById(R.id.s_tmajor);
        TextView tcourse=(TextView)view.findViewById(R.id.s_tcourse);
        TextView tcourseid=(TextView)view.findViewById(R.id.s_tcourseid);
        TextView tcoursenum=(TextView)view.findViewById(R.id.s_tcoursenum);
        TextView taddtime=(TextView)view.findViewById(R.id.s_taddtime);
        TextView tupdatetime=(TextView)view.findViewById(R.id.s_tupdatetime);

        RelativeLayout name = (RelativeLayout)view.findViewById(R.id.s_name);
        RelativeLayout price = (RelativeLayout)view.findViewById(R.id.s_price);
        RelativeLayout publisher = (RelativeLayout)view.findViewById(R.id.s_publisher);
        RelativeLayout damage = (RelativeLayout)view.findViewById(R.id.s_damage);
        RelativeLayout owner = (RelativeLayout)view.findViewById(R.id.s_owner);
        RelativeLayout major = (RelativeLayout)view.findViewById(R.id.s_major);
        RelativeLayout course = (RelativeLayout)view.findViewById(R.id.s_course);
        RelativeLayout courseid = (RelativeLayout)view.findViewById(R.id.s_courseid);
        RelativeLayout coursenum = (RelativeLayout)view.findViewById(R.id.s_coursenum);
        RelativeLayout addtime = (RelativeLayout)view.findViewById(R.id.s_addtime);
        RelativeLayout updatetime = (RelativeLayout)view.findViewById(R.id.s_updatetime);

        tname.setText("书 名："+book.bookname);

        tprice.setText("价 格："+new Integer(book.price).toString() );

        if (book.press!=null&& !book.press.equals("null"))
            tpublisher.setText("出版社："+book.press);
        else
            publisher.setVisibility(View.GONE);

//        tdamage.setText("破损程度："+book.damage);

        towner.setText("卖 家："+book.username);

//        tmajor.setText("专 业："+book.major);

        tcourse.setText("课程名："+book.coursename);

        if (book.courseid!=0) {
            tcourseid.setText("课序号："+new Integer(book.courseid).toString());
        }
        else
            courseid.setVisibility(View.GONE);

//        tcoursenum.setText(""+book.coursenum);

        if (book.add_time!=null&& !book.add_time.equals("null")) {
            taddtime.setText("添加时间："+book.add_time);
        }else addtime.setVisibility(View.GONE);

        if (book.update_time!=null&& !book.update_time.equals("null")) {
            tupdatetime.setText("更新时间："+book.update_time);
        }
        else
            updatetime.setVisibility(View.GONE);







        return view;
    }
}