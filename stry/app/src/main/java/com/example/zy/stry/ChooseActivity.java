package com.example.zy.stry;

/**
 * Created by zy on 15/7/7.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zy
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;
import com.example.zy.stry.util.GetInputStream;

import com.example.zy.stry.R;
import com.example.zy.stry.util.MyAdapter;
import com.example.zy.stry.util.XMLParser;
import com.example.zy.stry.util.ThreadUsersMessage;
import com.example.zy.stry.util.UserGlobla;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.os.StrictMode;


public class ChooseActivity extends Activity {

    private TextView name ;
    public String txt;
    //测试登录功能，返回“自动”登录后的页面
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
       /* if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/
        name = (TextView)findViewById(R.id.sname);
        TextView password=(TextView)findViewById(R.id.spassword);
        Intent intent=getIntent();
        Bundle result=intent.getExtras();
        //name.setText(result.getString("name"));
        // ThreadUsersMessage tsmfm =new ThreadUsersMessage(han,result.getString("name"),result.getString("password"));
        // GetInputStream in= new GetInputStream();
        //PullForXml on=new PullForXml();
        //String it=new String();
        //  it=PullForXml.getUserEntitys(GetInputStream.getInputStream(result.getString("name"), result.getString("password"))).toString();

        // name.setText(it);
        getU();
    }
    private  Handler han1 =new Handler(){
        public  void handleMessage(Message msg){
            switch (msg.what){
                case 3:
                    name.setText(txt);
                    break;
                case 4:
                    name.setText("4");
                    break;
                default:
                    name.setText("error");
                    break;
            }
        }
    };
    public void getU(){
        //  name.setText("run");
        // name.setText("run1");

        new Thread(new Runnable() {
            @Override
            public void run() {
               // txt=new String (PullForXml.getUserEntitys(GetInputStream.getInputStream("201347028","255016",1)).toString());
                //name.setText("txt");
                han1.sendEmptyMessage(3);
                //  else
                //      han1.sendEmptyMessage(4);
            }
        }).start();
    }

}