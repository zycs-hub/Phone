package com.example.zy.stry;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.zy.stry.lib.PullToZoomListViewEx;
import com.example.zy.stry.util.MyBuyAdapter;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zy.stry.entity.UserEntity;
import com.example.zy.stry.util.CourseGlobla;
import com.example.zy.stry.util.MySellAdapter;
import com.example.zy.stry.util.My_DB;
import com.example.zy.stry.util.ThreadUsersMessage;
import com.example.zy.stry.util.UserGlobla;
import java.lang.String;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import com.example.zy.stry.util.MyAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.zy.stry.lib.PullToZoomListViewEx;


/**
 * Created by zy on 15/7/9.
 */
public class Buy_Activity extends Activity {
    public static final int MAIN_ACTIVITY = 100;
    private LayoutInflater inflater = null;
    private RelativeLayout Bmain_body_lin = null;
    private ListView buy_page_list = null;
    private ListView sell_page_list = null;
    private PullToZoomListViewEx listView = null;
    private View v = null;
    private View v1 = null;
    private View v2 = null;
    private MyBuyAdapter ma = null;
    private MySellAdapter ma3=null;
    private MySellAdapter mb=null;

    // private Handler han =null;
    My_DB db = null;
    private SQLiteDatabase job = null;
    private List<UserEntity> lt2 = null;
    private List<String> lt3=null;

    private MyBuyAdapter ma2 = null;
    private MySellAdapter m3 =null;
    private Button sell = null;
    private Button buy = null;
    private Button self = null;
    private Button search_b = null;
    private Handler han = null;
    private Button sh_btn=null;
    private EditText sh_e=null;
    private ListView sh_lv=null;
    private char flag;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        db = new My_DB(Buy_Activity.this, My_DB.MY_DB_MANE, null, My_DB.MY_DB_VERSION);
        job = db.getReadableDatabase();
        List<UserEntity> lt = UserGlobla.lts;
        // if(!lt.isEmpty()){
        //  UserGlobla.lts=lt;
        //}
        // else{
        //ThreadUsersMessage str =new ThreadUsersMessage(han,result.getString("name"),result.getString("password"));
        //new Thread(str).start();
        //}
        inflater = getLayoutInflater();
        // btn_s=(Button) findViewById(R.id.btn_s);
        //main_tv1=(TextView)findViewById(R.id.main_tv1);

        search_b = (Button) findViewById(R.id.search_button);
        Bmain_body_lin = (RelativeLayout) findViewById(R.id.Bmain_body_line);
        v = inflater.inflate(R.layout.activity_buy_page, null);
        v1 = inflater.inflate(R.layout.activity_sell_page, null);
        v2 = inflater.inflate(R.layout.activity_pull_to_zoom_list_view, null);

        buy_page_list = (ListView) v.findViewById(R.id.buy_page_list);
        sell_page_list=(ListView)v1.findViewById(R.id.sell_page_list);
        listView = (PullToZoomListViewEx) v2.findViewById(R.id.listview);

        String[] adapterData = new String[]{"Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient",
                "DDMS", "Android Studio", "Fragment", "Loader", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient"};

        listView.setAdapter(new ArrayAdapter<String>(Buy_Activity.this, android.R.layout.simple_list_item_1, adapterData));
        listView.getPullRootView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("zhuwenwu", "position = " + position);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("zhuwenwu", "position = " + position);
            }
        });

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        AbsListView.LayoutParams localObject = new AbsListView.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        listView.setHeaderLayoutParams(localObject);

        ma = new MyBuyAdapter(UserGlobla.lts, Buy_Activity.this);
        buy_page_list.setAdapter(ma);

        Bmain_body_lin.addView(v);
        flag='b';


        sell = (Button) findViewById(R.id.btn_sell);
        buy = (Button) findViewById(R.id.btn_buy);
        self =(Button)findViewById(R.id.btn_s);
        search_b = (Button) findViewById(R.id.search_button);
        sell.setOnClickListener(new OnClickListener() {
            public void onClick(View v0) {
                search_b.setText("搜索");
                Bmain_body_lin.removeAllViews();
                Bmain_body_lin.addView(v);
                sell.setBackgroundResource(R.drawable.gree_background);
                buy.setBackgroundResource(R.drawable.white_bankground);
                flag='b';

            }
        });
        buy.setOnClickListener(new OnClickListener() {
            public void onClick(View v0) {
                Bmain_body_lin.removeAllViews();
                search_b.setText("搜索");
                // Bmain_body_lin.addView(v);
                sell.setBackgroundResource(R.drawable.white_bankground);
                buy.setBackgroundResource(R.drawable.gree_background);

                       // List<String> lt= new ArrayList<>();  //=db.getUserAll(job);
                if(!CourseGlobla.lts.isEmpty()){


                    mb=new MySellAdapter(CourseGlobla.lts,Buy_Activity.this);
                    sell_page_list.setAdapter(mb);
                    Bmain_body_lin.addView(v1);
                }
                else{
                        }
                flag='s';

            }
        });

        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Buy_Activity.this, PullToZoomListActivity.class));
                Bmain_body_lin.removeAllViews();
                Bmain_body_lin.addView(v2);
            }
        });


        search_b.setOnClickListener(new OnClickListener() {
            public void onClick(View vs) {
                Bmain_body_lin.removeAllViews();
                search_b.setText("");
                View v = inflater.inflate(R.layout.acvtivity_sh, null);
                sh_btn = (Button) v.findViewById(R.id.sh_btn);
                sh_lv = (ListView) v.findViewById(R.id.sh_lv);
                sh_e = (EditText) v.findViewById(R.id.sh_e);
                //if(flag=='s'){
                //sell.setBackgroundResource(R.drawable.white_bankground);
                //buy.setBackgroundResource(R.drawable.white_bankground);
                //}
                sh_btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flag == 'b') {
                            if (UserGlobla.lts != null) {
                                lt2 = new ArrayList<UserEntity>();
                                String param = sh_e.getText().toString().trim();
                                for (UserEntity str : UserGlobla.lts) {
                                    if (str.getBook() != null && str.getBook().contains(param)) {
                                        if (!lt2.contains(str)) {
                                            lt2.add(str);
                                        }
                                    }
                                }
                                if (lt2.isEmpty()) {
                                    //弹出提示框
                                }
                                ma2 = new MyBuyAdapter(lt2, Buy_Activity.this);
                                sh_lv.setAdapter(ma2);
                            }
                        } else if (flag == 's') {
                            //sell.setBackgroundResource(R.drawable.white_bankground);
                            //buy.setBackgroundResource(R.drawable.gree_background);
                            if (UserGlobla.lts != null) {
                                lt3 = new ArrayList<String>();
                                String param = sh_e.getText().toString().trim();
                                for (String str : CourseGlobla.lts) {
                                    if (str.toString() != null && str.toString().contains(param)) {
                                        if (!lt3.contains(str)) {
                                            lt3.add(str);
                                        }
                                    }
                                }
                                if (lt3.isEmpty()) {
                                    //弹出提示框
                                }
                                ma3 = new MySellAdapter(lt3, Buy_Activity.this);
                                sh_lv.setAdapter(ma3);
                            }
                        }


                    }
                });


                Bmain_body_lin.addView(v);
                // sell.setBackgroundResource(R.drawable.gree_background);
                // buy.setBackgroundResource(R.drawable.white_bankground);

            }
        });
        buy_page_list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(Buy_Activity.this, ShowMessageActivity.class);
                it.putExtra("bookName", UserGlobla.lts.get(i).getBook());
                it.putExtra("position", i);
                startActivityForResult(it, MAIN_ACTIVITY);

            }
        });
        sell_page_list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(Buy_Activity.this, ShowMessageActivity_.class);
                it.putExtra("bookName", CourseGlobla.lts.get(i).toString());
                it.putExtra("position", i);
                startActivityForResult(it, MAIN_ACTIVITY);

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

