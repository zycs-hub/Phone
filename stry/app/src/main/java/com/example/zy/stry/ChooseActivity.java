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
        // ThreadBooksMessage tsmfm =new ThreadBooksMessage(han,result.getString("name"),result.getString("password"));
        // GetInputStream in= new GetInputStream();
        //PullForXml on=new PullForXml();
        //String it=new String();
        //  it=PullForXml.getBookEntitys(GetInputStream.getInputStream(result.getString("name"), result.getString("password"))).toString();

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
               // txt=new String (PullForXml.getBookEntitys(GetInputStream.getInputStream("201347028","255016",1)).toString());
                //name.setText("txt");
                han1.sendEmptyMessage(3);
                //  else
                //      han1.sendEmptyMessage(4);
            }
        }).start();
    }

}