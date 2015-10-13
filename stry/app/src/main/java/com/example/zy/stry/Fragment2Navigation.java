package com.example.zy.stry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by wendy on 15-9-27.
 */
public class Fragment2Navigation extends Fragment {
    private View rootView;
    private Button bnt_mng;
    private Button bnt_cart;

    public static final String PREFS_NAME = "MyPrefs";
    private String username = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_2_navigation, container, false);
        }
        bnt_mng = (Button) rootView.findViewById(R.id.bnt_mng);
        bnt_cart = (Button) rootView.findViewById(R.id.bnt_cart);


        bnt_mng.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction ft = MainActivity.fmg.beginTransaction();
                ft.replace(R.id.fragment_2, new ManagementFragment());
                ft.addToBackStack("navigation");
                ft.commitAllowingStateLoss();

            }
        });

        bnt_cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction ft = MainActivity.fmg.beginTransaction();
                //ft.replace(R.id.fragment_2, new CartFragment());
                ft.addToBackStack("navigation");
                ft.commitAllowingStateLoss();
            }
        });

        return rootView;
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
