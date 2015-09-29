package com.example.zy.stry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by zy on 2015/9/16.
 */
public class BookDetailInfo extends Fragment {
    private FloatingActionButton mFabButton;


    public static BookDetailInfo newInstance(String info) {
        Bundle args = new Bundle();
        BookDetailInfo fragment = new BookDetailInfo();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_info, null);
        //setUpFAB(view);

//        final TextView infoprice = (TextView) view.findViewById(R.id.infoprice);
//        TextView info = (TextView) view.findViewById(R.id.info);
//        info.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n");
//        infoprice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new MaterialDialog.Builder(getActivity())
//                        .title("输入价格")
//                                //.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
//                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
//                            @Override
//                            public void onInput(MaterialDialog dialog, CharSequence input) {
//                                // Do something
//                                if (!TextUtils.isEmpty(input)) {
//                                    infoprice.setText("\n价格：" + input + "\n");
//                                }
//                            }
//                        }).show();
//            }
//        });
        //tvInfo.setText(getArguments().getString("info"));
//        tvInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(v,"hello",Snackbar.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }
//    private void setUpFAB(View view) {
//        mFabButton = (FloatingActionButton) view.findViewById(R.id.fab_normal_info);
//        mFabButton.setVisibility(View.GONE);
//        mFabButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new MaterialDialog.Builder(getActivity())
//                        .title(R.string.search)
//                                //.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
//                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
//                            @Override
//                            public void onInput(MaterialDialog dialog, CharSequence input) {
//                                // Do something
//                                if (!TextUtils.isEmpty(input)) {
//                                   // doSearch(input.toString());
//                                }
//                            }
//                        }).show();
//            }
//        });
//    }

}
