package com.example.zy.stry;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.zy.stry.lib.PullToZoomListViewEx;
import com.example.zy.stry.lib.TabsAdapter;
import com.example.zy.stry.lib.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 15-8-26.
 */
public class ProfileFragment extends Fragment {
    private View rootView;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        }

        FragmentTransaction ft = MainActivity.fmg.beginTransaction();
        ft.replace(R.id.fragment_profile, new Login());
        ft.commit();
        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
