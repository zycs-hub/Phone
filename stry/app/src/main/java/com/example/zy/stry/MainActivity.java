package com.example.zy.stry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.zy.stry.R;
import com.example.zy.stry.util.MyAdapter;
import com.example.zy.stry.util.ThreadUsersMessage;
import com.example.zy.stry.util.UserGlobla;

import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;


//import zychoose.DUTCrawler;


public class MainActivity extends Activity {




    private Handler han =null;
    private String nameString="";
    private String passwordString="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt=(Button)findViewById(R.id.teachLogin);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////////////////////////////////////
                han = new Handler(){
                    @Override
                    public void handleMessage(Message msg){
                        switch (msg.what){
                            // case 1:
                            //   ma.notifyDataSetChanged();
                            //         break;
                            //   case 2:
                            //   new OutDialog(LogActivity.this).getDialog("shibai",true).show();
                            //    break;
                            case -1:
                                Toast.makeText(MainActivity.this, "no_web", Toast.LENGTH_LONG).show();
                                // long param = db.addDate(UserGlobla.lts,job);
                                // if(param==UserGlobla.lts.size()){
                                //page_list=(ListView)v.findViewById(R.id.page_list);
                                //ma=new MyAdapter(UserGlobla.lts,LogActivity.this);
                                //page_list.setAdapter(ma);
                                //main_body_lin.addView(v);

                                // }
                                break;
                            case -2:
                                Toast.makeText(MainActivity.this, R.string.TLogErr1, Toast.LENGTH_LONG).show();
                                break;
                            case -3:
                                Toast.makeText(MainActivity.this, R.string.TLogErr2, Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                Bundle data=new Bundle();
                                data.putString("name",nameString);
                                data.putString("password", passwordString);
                                Intent  intent=new Intent(MainActivity.this, LogActivity.class);
                                intent.putExtras(data);
                                startActivity(intent);
                                finish();
                                break;
                            default:break;
                        }

                    }
                };
                ///////////////////////////////////////////
                EditText name=(EditText)findViewById(R.id.name);
                EditText password=(EditText)findViewById(R.id.password);
                nameString=name.getText().toString();
                passwordString=password.getText().toString();
                if (nameString.equals("") || passwordString.equals("")) {
                    Toast.makeText(MainActivity.this, "Username or password must be filled", Toast.LENGTH_LONG).show();
                    return;
                }
                ///////////////////////////////////////////////////////////////////
                ThreadUsersMessage str =new ThreadUsersMessage(han,nameString,passwordString);
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
