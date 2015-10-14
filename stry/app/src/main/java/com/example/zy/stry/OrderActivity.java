package com.example.zy.stry;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zy.stry.lib.BookOperarion;
import com.example.zy.stry.lib.Config;
import com.example.zy.stry.lib.Function;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

/**
 * Created by wendy on 15-10-12.
 */
public class OrderActivity extends AppCompatActivity {
    private ListView listView;
    private EditText addressText;
    private EditText commentText;
    private Button confirmBnt;
    private String username;
    private String books;
    private String address;
    private String comment;


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
                    Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), "订单失败", Toast.LENGTH_SHORT).show();
                    break;


            }
        }
    };
}