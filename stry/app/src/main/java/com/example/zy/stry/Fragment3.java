package com.example.zy.stry;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.util.UserbookGlobla;

/**
 * Created by wendy on 15-8-26.
 */
public class Fragment3 extends Fragment {
    private View rootView;
    SharedPreferences shared_preferences;
    public static CollapsingToolbarLayout collapsingToolbar;
    RecyclerView recyclerView;
    int mutedColor = R.attr.colorPrimary;
    //SimpleRecyclerAdapter simpleRecyclerAdapter;

    public static final String PREFS_NAME = "MyPrefs";
    private String username = null;
    private String name;
    public static ImageButton head;
    FragmentTransaction ft;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_3, container, false);
        }

        ft = MainActivity.fmg.beginTransaction();

        shared_preferences = getActivity().getSharedPreferences(PREFS_NAME,  getActivity().MODE_PRIVATE);
        username = shared_preferences.getString("username", null);
        name = shared_preferences.getString("name", null);
        head = (ImageButton) rootView.findViewById(R.id.fab_head);
        collapsingToolbar = (CollapsingToolbarLayout)rootView.findViewById(R.id.collapsing_toolbar);

        if(username == null) {
            ft.replace(R.id.fragment_3, new Login());
            collapsingToolbar.setTitle("登录");
            head.setVisibility(View.GONE);
        } else if (name==null){
            head.setVisibility(View.VISIBLE);
            collapsingToolbar.setTitle("登录教务处");
            ft.replace(R.id.fragment_3, new AccountFragment());
        }else {
            head.setVisibility(View.VISIBLE);
            ft.replace(R.id.fragment_3, new UserInfFragment());
            //ft.replace(R.id.fragment_3, new UserInfFragment());
            collapsingToolbar.setTitle(username);
        }

        ft.commit();

        RelativeLayout cart = (RelativeLayout) rootView.findViewById(R.id.cart);
        RelativeLayout buying = (RelativeLayout) rootView.findViewById(R.id.buying);
        RelativeLayout bought = (RelativeLayout) rootView.findViewById(R.id.bought);
        RelativeLayout sold = (RelativeLayout) rootView.findViewById(R.id.sold);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //           ∧__∧
                //        　( ●ω●)
                //        　｜つ／(＿＿＿
                //        ／└-(＿＿＿_／


                if (!isLog()) {show();return;}
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });



        head.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(),SetUserInfActivity.class);
                startActivity(intent);

            }
        });

        buying.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isLog()) {show();return;}

                Intent intent = new Intent(getActivity(), BuyingListActivity.class);
                startActivity(intent);
            }
        });

        bought.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isLog()) {show();return;}

                Intent intent = new Intent(getActivity(), BoughtListActivity.class);
                startActivity(intent);
            }
        });

        sold.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isLog()) {show();return;}

                Intent intent = new Intent(getActivity(), SoldListActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
    private boolean isLog(){

        return  shared_preferences.getString("username", null)!=null;
    }
    private void show(){
        Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_LONG).show();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
