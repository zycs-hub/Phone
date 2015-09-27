package com.example.zy.stry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Chenyc on 2015/6/29.
 */
public class DetailFragment extends Fragment {

    public static DetailFragment newInstance(String info) {
        Bundle args = new Bundle();
        DetailFragment fragment = new DetailFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, null);
        TextView tvInfo = (TextView) view.findViewById(R.id.tvInfo);
 //       Button button  = (Button) view.findViewById(R.id.button_detail);
        tvInfo.setText(getArguments().getString("info"));
//        tvInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(v,"hello",Snackbar.LENGTH_SHORT).show();
//            }
//        });
        /*
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v0) {
                String[] S ={"手动添加","数据库自动添加"};
                new MaterialDialog.Builder(getActivity())
                        .title("选择添加信息方式")
                        .items(S)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                Log.e("zhuwenwu", "position = " + dialog + view +which+text);
                                switch (which){
                                    case 0:
                                        Toast.makeText(getActivity(), "待完成", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        Intent intent = new Intent(getActivity(), ChooseActivity.class);
                                        startActivity(intent);
                                        break;

                                }

                            }})
                        .show();
            }
        });
        */
        return view;
    }
}
