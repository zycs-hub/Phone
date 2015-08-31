package com.example.zy.stry;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.zy.stry.lib.PullToZoomListViewEx;

/**
 * Created by wendy on 15-8-26.
 */
public class ProfileFragment extends Fragment {
    private PullToZoomListViewEx listView = null;
    private View rootView;
    private String[] adapterData;

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
        adapterData = new String[] { "Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver",
                "ADT", "Sqlite3", "HttpClient", "DDMS", "Android Studio", "Fragment", "Loader" };
//        AbsListView.LayoutParams localObject = new AbsListView.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));


        listView.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, adapterData));
//        listView.getHeaderView().setImageResource(R.drawable.splash01);
//        listView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
//        listView.setHeaderLayoutParams(localObject);
        return rootView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//		Log.d(TAG, "TestFragment-----onDestroy");
    }
}
