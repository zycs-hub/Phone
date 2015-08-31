package com.example.zy.stry;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.zy.stry.util.My_DB;
import com.example.zy.stry.util.ThreadBooksMessage;
import com.example.zy.stry.util.BookGlobla;

import android.view.View.OnClickListener;
import android.widget.Toast;


//import zychoose.DUTCrawler;


public class LogForT extends Activity { 




    private Handler han =null;
    private String nameString="";
    private String passwordString="";
    //Bundle data=new Bundle();
    My_DB db=null;
    private SQLiteDatabase job =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_for_t);
        Button bt=(Button)findViewById(R.id.teachLogin);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////////////////////////////////////
                han = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case -1:
                                Toast.makeText(LogForT.this, "no_web", Toast.LENGTH_LONG).show();
                                break;
                            case -2:
                                Toast.makeText(LogForT.this, R.string.TLogErr1, Toast.LENGTH_LONG).show();
                                break;
                            case -3:
                                Toast.makeText(LogForT.this, R.string.TLogErr2, Toast.LENGTH_LONG).show();
                                break;
                            case 1: {
                                //if confirm password return finish(); if overridr clear db
                                //确认密码 存入数据库  return
                                //初次登录 晴空， 写入数据库  跳到选书界面
                                db = new My_DB(LogForT.this, My_DB.MY_DB_MANE, null, My_DB.MY_DB_VERSION);
                                job = db.getReadableDatabase();
                                //db.onUpgrade(job,0,0);
                                job.execSQL("DELETE FROM user");
                                long param = db.addDate(BookGlobla.lts, job);
                               // if (param != BookGlobla.lts.size()) Toast.makeText(LogForT.this, "err", Toast.LENGTH_LONG).show();
                                //Bundle data = new Bundle();
                                //data.putString("name", nameString);
                                //data.putString("password", passwordString);
                                Intent intent = new Intent(LogForT.this, SelectFromT.class);
                                //intent.putExtras(data);
                                startActivity(intent);
                                finish();
                                //} else {
                                //Toast.makeText(LogForT.this, "err", Toast.LENGTH_LONG).show();
                                // }
                                break;
                            }
                            default:
                                break;
                        }

                    }
                };
                ///////////////////////////////////////////
                EditText name = (EditText) findViewById(R.id.name);
                EditText password = (EditText) findViewById(R.id.password);
                nameString = name.getText().toString();
                passwordString = password.getText().toString();
                if (nameString.equals("") || passwordString.equals("")) {
                    Toast.makeText(LogForT.this, "Username or password must be filled", Toast.LENGTH_LONG).show();
                    return;
                }
                ///////////////////////////////////////////////////////////////////
                ThreadBooksMessage str = new ThreadBooksMessage(han, nameString, passwordString, "Override");
                new Thread(str).start();
                ///////////////////////////////////////////////////////////////////
            }
        });
        Button button = (Button)findViewById(R.id.Login);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent login = new Intent(getApplicationContext(), Login.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
                // Closing dashboard screen
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
