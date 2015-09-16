package com.example.zy.stry;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.zy.stry.lib.PullToZoomListViewEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 15-8-26.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private PullToZoomListViewEx listView = null;
    private View rootView;
    private List adapterData = new ArrayList<>();
    private Button login_bnt;
    private Button register_bnt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        login_bnt = (Button) rootView.findViewById(R.id.login);
        login_bnt.setOnClickListener(this);
//
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getActivity().getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("username");
                adapterData.add(newString);
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("username");
            adapterData.add(newString);
        }

        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, (String[])adapterData.toArray(new String[adapterData.size()])));
//>>>>>>> 2fef4077ce0be2c0c0dd8de53aae3144088253d8
        return rootView;
    }

    @Override
    public void onClick(View arg0) {
        Intent login = new Intent(getActivity(), LogForT.class);
        startActivity(login);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//		Log.d(TAG, "TestFragment-----onDestroy");
    }
}
