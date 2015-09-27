package com.example.zy.stry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wendy on 15-9-27.
 */
public class Fragment2 extends Fragment {
    private View rootView;

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
            rootView = inflater.inflate(R.layout.fragment_2, container, false);
        }

        FragmentTransaction ft = MainActivity.fmg.beginTransaction();
        ft.replace(R.id.fragment_2, new ManagementFragment());
        ft.commitAllowingStateLoss();
        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
