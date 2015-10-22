package com.example.zy.stry.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.zy.stry.ChooseActivity;
import com.example.zy.stry.MainActivity;
import com.example.zy.stry.R;
import com.example.zy.stry.entity.Sell.SellEntity;
import com.example.zy.stry.lib.BookOperarion;
import com.example.zy.stry.lib.Config;
import com.example.zy.stry.lib.Function;
import com.example.zy.stry.util.UserbookGlobla;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zy on 15/9/21.
 * My Center Detail
 */
public class MCDetailFragment extends Fragment {

    SharedPreferences shared_preferences;

    public static final String PREFS_NAME = "MyPrefs";
    private String username = null;


    TextView tprice ;
    TextView taddress ;
    TextView tdamage ;
    TextView tremarks ;
    TextView tname ;
    TextView tauthor ;
    TextView tpublish ;
    TextView toprice;
    TextView tpage ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 　∧__∧
//        　( ●ω●)
//        　｜つ／(＿＿＿
//        ／└-(＿＿＿_／
        Bundle bundle = this.getArguments();
        final String book = bundle.getString("book");
        final int position = bundle.getInt("position");
        final int bid = bundle.getInt("bookid");



        View view = inflater.inflate(R.layout.fragment_mcd_infor, container, false);

        RelativeLayout add = (RelativeLayout) view.findViewById(R.id.addmore);
        RelativeLayout price = (RelativeLayout) view.findViewById(R.id.price);
        RelativeLayout damage = (RelativeLayout) view.findViewById(R.id.damage);
        final RelativeLayout remark = (RelativeLayout) view.findViewById(R.id.remark);
        RelativeLayout address = (RelativeLayout) view.findViewById(R.id.address);
        tprice = (TextView) view.findViewById(R.id.tprice);
        taddress = (TextView) view.findViewById(R.id.taddress);
        tdamage = (TextView) view.findViewById(R.id.tdamage);
        tremarks = (TextView) view.findViewById(R.id.tremarks);
        tname = (TextView) view.findViewById(R.id.tname);
        tauthor = (TextView) view.findViewById(R.id.tauthor);
        tpublish = (TextView) view.findViewById(R.id.tpublish);
        toprice = (TextView) view.findViewById(R.id.toprice);
        tpage = (TextView) view.findViewById(R.id.tpage);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //           ∧__∧
                //        　( ●ω●)
                //        　｜つ／(＿＿＿
                //        ／└-(＿＿＿_／


                Intent intent = new Intent(getActivity(), ChooseActivity.class);
                intent.putExtra("book", book);
                intent.putExtra("bookid",bid);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                new MaterialDialog.Builder(getActivity())
                        .title("输入价格")
                        .content("输入价格")
                        .inputType(InputType.TYPE_CLASS_NUMBER )
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    tprice.setText("价 格：" + input );
                                    final String price = input.toString();
                                    final String sell_id = Integer.toString(UserbookGlobla.lts.get(position)._id);
                                    
                                    shared_preferences = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
                                    username = shared_preferences.getString("username", null);
                                    BookOperarion.editSell task = new BookOperarion().new editSell(username, sell_id, SellEntity.KEY_PRICE,
                                            price, new Function<JSONObject, Void>() {
                                        @Override
                                        public Void apply(JSONObject json) {
                                            if(json == null) {
                                                Message msg = new Message();
                                                msg.what = 0;
                                                handler.sendMessage(msg);
                                            } else {
                                                try {
                                                    if(json.getString(Config.KEY_SUCCESS) != null){
                                                        UserbookGlobla.lts.get(position).price=price;
                                                        MainActivity.db.editTable(SellEntity.TABLE_NAME, "price", price,
                                                                SellEntity.KEY_ID, sell_id);
                                                        Message msg = new Message();
                                                        msg.what = 1;
                                                        handler.sendMessage(msg);
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            
                                            return null;
                                        }
                                    });
                                    MainActivity.executorService.submit(task);

                                }
                            }
                        }).show();

            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                new MaterialDialog.Builder(getActivity())
                        .title("输入价格")
                        .content("输入价格")
                                //.inputType(InputType.TEXT )
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    taddress.setText("地 址：" + input);

                                }
                            }
                        }).show();

            }
        });
        damage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title("输入价格")
                        .content("输入价格")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    tdamage.setText("破损程度：" + input);
                                    UserbookGlobla.lts.get(position).damage = input.toString();
                                }
                            }
                        }).show();

            }
        });
        remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                new MaterialDialog.Builder(getActivity())
                        .title("输入价格")
                        .content("输入价格")
                                //.inputType(InputType.TYPE_CLASS_NUMBER )
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    tremarks.setText("备 注：" + input);
                                    UserbookGlobla.lts.get(position).remarks = input.toString();
                                }
                            }
                        }).show();

            }
        });
        update(position);


        return view;
    }
    public void update(int position) {
        try {
            if (UserbookGlobla.lts.get(position).price!=null)
                tprice.setText("售 价："+UserbookGlobla.lts.get(position).price);
            if (UserbookGlobla.lts.get(position).damage!=null)
                tdamage.setText("破损程度："+UserbookGlobla.lts.get(position).damage);
            if (UserbookGlobla.lts.get(position).remarks!=null)
                tremarks.setText("备 注："+UserbookGlobla.lts.get(position).remarks);
            if (UserbookGlobla.lts.get(position).bookname!=null)
                tname.setText("书 名："+UserbookGlobla.lts.get(position).bookname);
            if (UserbookGlobla.lts.get(position).author!=null)
                tauthor.setText("作 者："+UserbookGlobla.lts.get(position).author);
            if (UserbookGlobla.lts.get(position).publisher!=null)
                tpublish.setText("出版社："+UserbookGlobla.lts.get(position).publisher);
            if (UserbookGlobla.lts.get(position).pages!=null)
                tpage.setText("页 数："+UserbookGlobla.lts.get(position).pages);
            if (UserbookGlobla.lts.get(position).origprice!=null)
                toprice.setText("原 价："+UserbookGlobla.lts.get(position).origprice);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private Handler handler = new Handler() {

        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    Toast.makeText(getActivity(), Config.LOAD_ERROR, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getActivity(), "设置成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



}
