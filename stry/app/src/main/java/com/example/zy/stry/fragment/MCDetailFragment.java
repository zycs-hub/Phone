package com.example.zy.stry.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.zy.stry.ChooseActivity;
import com.example.zy.stry.R;

/**
 * Created by zy on 15/9/21.
 * My Center Detail
 */
public class MCDetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_mcd_infor, container, false);

        RelativeLayout add = (RelativeLayout) view.findViewById(R.id.addmore);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ChooseActivity.class);
                startActivity(intent);

            }
        });


        return view;
    }


}
