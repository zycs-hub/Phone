package com.example.zy.stry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.zy.stry.lib.PullToZoomListViewEx;
import com.example.zy.stry.lib.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 15-9-22.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {
    private PullToZoomListViewEx listView = null;
    private View rootView;
    private Button login_bnt;
    private Button left_bnt;

    private List adapterData = new ArrayList<>();

    SharedPreferences shared_preferences;
    FragmentTransaction ft;


    public static final String PREFS_NAME = "MyPrefs";
    private String username = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapterData.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_account, container, false);
        }

        shared_preferences = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
        username = shared_preferences.getString("username", null);
        ft =  MainActivity.fmg.beginTransaction();
        login_bnt = (Button) rootView.findViewById(R.id.login);
        login_bnt.setOnClickListener(this);
        left_bnt = (Button) rootView.findViewById(R.id.left);
        left_bnt.setOnClickListener(this);
//
        if( username != null) {
            left_bnt.setText("登出");
            login_bnt.setText("登入教务处");
            //adapterData.add(username);
        }

//        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, (String[]) adapterData.toArray(new String[adapterData.size()])));



        return rootView;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.left:
                User user = new User();
                user.logoutUser(getActivity());
                ft.replace(R.id.fragment_3, new Login());
                ft.commit();
                break;
            case R.id.login:
                intent = new Intent(getActivity(), LogForT.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//		Log.d(TAG, "TestFragment-----onDestroy");
    }
}
