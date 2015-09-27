package com.example.zy.stry;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wendy on 15-8-26.
 */
public class Fragment3 extends Fragment {
    private View rootView;
    SharedPreferences shared_preferences;

    public static final String PREFS_NAME = "MyPrefs";
    private String username = null;

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

        FragmentTransaction ft = MainActivity.fmg.beginTransaction();

        shared_preferences = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
        username = shared_preferences.getString("username", null);
        if(username == null) {
            ft.replace(R.id.fragment_3, new Login());
        } else {
            ft.replace(R.id.fragment_3, new AccountFragment());
        }

        ft.commit();
        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
