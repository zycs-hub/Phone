package com.example.zy.stry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.lib.BookOperarion;
import com.example.zy.stry.lib.Config;
import com.example.zy.stry.lib.Function;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wendy on 15-10-12.
 */
public class OrderActivity extends AppCompatActivity  {
    private ListView listView;
    private EditText addressText;
    private EditText commentText;
    private Button confirmBnt;
    private String username;
    private String books;
    private String address;
    private String comment;

    public static ArrayList<BookEntity> mListItems;
    private ArrayAdapter<BookEntity> mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        listView =  (ListView) findViewById(R.id.listView1);
        addressText = (EditText) findViewById(R.id.text_adr);
        commentText = (EditText) findViewById(R.id.comment);
        confirmBnt = (Button) findViewById(R.id.button);


        Intent intent = getIntent();
        books =  intent.getStringExtra("books");
        username = intent.getStringExtra("username");

        mListItems= (ArrayList<BookEntity>) intent.getSerializableExtra("items");

        mAdapter = new OrderAdapter(this, mListItems);
        listView.setAdapter(mAdapter);

        confirmBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                address = addressText.getText().toString();
                comment = commentText.getText().toString();

                BookOperarion bookOpt = new BookOperarion();
                BookOperarion.buyBooks task = bookOpt.new buyBooks(username, books, address, comment, new Function<JSONObject, Void>() {
                    @Override
                    public Void apply(JSONObject json) {

                        Message msg = new Message();
                        try {

                            if(json == null){
                                msg.what = 0;
                                handler.sendMessage(msg);
                            } else {
                                if (json.getString(Config.KEY_SUCCESS) != null) {
                                    for (BookEntity item : mListItems) {
                                        MainActivity.db.changeSelling(item._id);
                                    }
                                    msg.what = 1;
                                    handler.sendMessage(msg);
                                } else {
                                    msg.what = -1;
                                    handler.sendMessage(msg);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Exception : " + e.getMessage());
                        }

                        return null;
                    }
                });

                MainActivity.executorService.submit(task);

            }
        });
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getApplicationContext(), Config.LOAD_ERROR, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "购买成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), BuyingListActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), "订单失败", Toast.LENGTH_SHORT).show();
                    break;


            }
        }
    };

    private static class ViewHolder {
        ImageView s_image;
        TextView s_title;
        TextView book;
        //            TextView author;
        TextView s_price;
        //            TextView message;
        TextView s_owner;
        TextView s_major;
        TextView s_course;
        public ViewHolder() {}

    }

    public class OrderAdapter extends ArrayAdapter<BookEntity> {

        private LayoutInflater inflater;
        private List<BookEntity> mList;
        private Context context;


        public OrderAdapter(Context context,List<BookEntity> mList) {
            super(context, R.layout.item_cart, mList);
            // Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context) ;
            this.mList = mList;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CheckBox checkBox ;
            ViewHolder viewHolder = null;


            BookEntity item =  mList.get(position);
//            BookEntity item;
//            item =  new BookEntity();
//
            if(convertView == null) {
                viewHolder = new ViewHolder();

                convertView = inflater.inflate(R.layout.item_shop, null);

                viewHolder.s_image = (ImageView) convertView.findViewById(R.id.s_image);
                viewHolder.s_title = (TextView) convertView.findViewById(R.id.s_title);
//                viewHolder.book = (TextView) convertView.findViewById(R.id.book);
//                viewHolder.author = (TextView) convertView.findViewById(R.id.author);
                viewHolder.s_price = (TextView) convertView.findViewById(R.id.s_price);
//                viewHolder.message = (TextView) convertView.findViewById(R.id.message);
                viewHolder.s_owner = (TextView) convertView.findViewById(R.id.s_owner);
                viewHolder.s_major = (TextView) convertView.findViewById(R.id.s_major);
                viewHolder.s_course = (TextView) convertView.findViewById(R.id.s_course);

                convertView.setTag(viewHolder);

                // If CheckBox is toggled, update the planet it is tagged with.


            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // Tag the CheckBox with the Planet it is displaying, so that we can
            // access the planet in onClick() when the CheckBox is toggled.

            // Display planet data
            viewHolder.s_title.setText("课程名：" + mList.get(position).coursename);
            return convertView;
        }
    }
}