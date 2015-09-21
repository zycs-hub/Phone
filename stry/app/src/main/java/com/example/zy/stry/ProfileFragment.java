package com.example.zy.stry;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.zy.stry.lib.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 15-8-26.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private PullToZoomListViewEx listView = null;
    private View rootView;
    private Button login_bnt;
    private Button left_bnt;

    private List adapterData = new ArrayList<>();

    SharedPreferences shared_preferences;

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
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        listView = (PullToZoomListViewEx) rootView.findViewById(R.id.listview);
//        AbsListView.LayoutParams localObject = new AbsListView.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));


//        listView.getHeaderView().setImageResource(R.drawable.splash01);
//        listView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
//        listView.setHeaderLayoutParams(localObject);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//           @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("zhuwenwu", "position = " + position);
//                if (position==0){
//                    Intent intent = new Intent(getActivity(), LogForT.class);
//                    startActivity(intent);
//                }
//                //Toast.makeText(MainActivity.this, "123", Toast.LENGTH_LONG).show();/
//
//           }
//       });
        shared_preferences = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
        username = shared_preferences.getString("username", null);
        login_bnt = (Button) rootView.findViewById(R.id.login);
        login_bnt.setOnClickListener(this);
        left_bnt = (Button) rootView.findViewById(R.id.left);
        left_bnt.setOnClickListener(this);
//
        if( username != null) {
            left_bnt.setText("登出");
            login_bnt.setText("登入教务处");
            adapterData.add(username);
        }

        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, (String[])adapterData.toArray(new String[adapterData.size()])));
        return rootView;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.left:
                if(username != null) {
                    User user = new User();
                    user.logoutUser(getActivity());
                    left_bnt.setText("注册");
                    login_bnt.setText("登入");
                    adapterData.clear();
                    listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, (String[]) adapterData.toArray(new String[adapterData.size()])));
                } else {
                    intent = new Intent(getActivity(), RegisterActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.login:
                if(username != null) {
                    intent = new Intent(getActivity(), LogForT.class);
                } else {
                    intent = new Intent(getActivity(), Login.class);
                }
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
