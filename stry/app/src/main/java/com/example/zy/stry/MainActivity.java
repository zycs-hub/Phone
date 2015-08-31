package com.example.zy.stry;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import com.example.zy.stry.R;
import com.example.zy.stry.entity.UserEntity;
import com.example.zy.stry.lib.PullToZoomListViewEx;
import com.example.zy.stry.util.CourseGlobla;
import com.example.zy.stry.util.LogStatusGlobla;
import com.example.zy.stry.util.ThreadLogStatusTest;
import com.example.zy.stry.util.MyAdapter;
import com.example.zy.stry.util.MyBuyAdapter;
import com.example.zy.stry.util.MySellAdapter;
import com.example.zy.stry.util.My_DB;
import com.example.zy.stry.util.ThreadLogStatusTest;
import com.example.zy.stry.util.ThreadUsersMessage;
import com.example.zy.stry.util.UserGlobla;

import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.example.zy.stry.entity.takingCourseEntity;





public class MainActivity extends Activity {
    /*
    LogStatusGlobla
    * 0 默认
    * 1 登录
    * 2 登录有网
    * 3 验证成功
    * 4 绑定教务处
    * -1 未登录
    * -2 登录无网
    * -4 未绑定教务处
    */




    public static final int MAIN_ACTIVITY = 100;
    private LayoutInflater inflater = null;
    private RelativeLayout Bmain_body_lin = null;
    private ListView buy_page_list = null;
    private ListView sell_page_list = null;
    private PullToZoomListViewEx listView = null;
    private View v = null;
    private View v1 = null;
    private View v2 = null;
    private View v3=null;
    private MyBuyAdapter ma = null;
    private MySellAdapter ma3=null;
    private MySellAdapter mb=null;

    My_DB db = null;
    SQLiteDatabase job = null;
    private List<takingCourseEntity> lt2 = null;
    private List<UserEntity> lt3=null;

    private MyBuyAdapter ma2 = null;
    private Button sell = null;
    private Button buy = null;
    private Button self = null;
    private Button search_b = null;

    private Button sh_btn=null;
    private EditText sh_e=null;
    private ListView sh_lv=null;
    private char flag;





    private Button tv_login=null;
    private Handler hanMain = null;
    private LinearLayout BLinearLayout1=null;
    private Toast toast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toast = Toast.makeText(getApplicationContext(), "再按一次退出", 0);








        /*
    LogStatusGlobla
    * 0 默认
    * 1 登录
    * 2 登录有网
    * 3 验证成功
    * 4 绑定教务处
    * -1 未登录
    * -2 登录无网
    * -4 未绑定教务处
    */


        //hanMain.sendEmptyMessage(-1);

        hanMain = new Handler()
        {
            @Override
            public  void handleMessage(Message msg)
            {

                //more1 = (LinearLayout)findViewById(R.id.more1);
                //more1t =(TextView)findViewById(R.id.more1t);
                switch (msg.what)
                {
                    case 1:
                    case 2:
                    case 3:
                        //more1.setVisibility(View.GONE);
                        break;
                    case 4:
                        // more1.setVisibility(View.VISIBLE);
                        break;//数据库空 list else 显示数据库
                    case -1:
                        //more1.setVisibility(View.VISIBLE);
                        //more1t.setText("登录显示更多");
                        break;//清空数据库，1页登录，2页登录 3页登录 注册
                    case -4:
                        //more1.setVisibility(View.VISIBLE);
                        //more1t.setText("绑定教务处显示个性推荐");
                        break;//1页 绑定 2页绑定 3页 隐藏
                    default:
                        break;
                }

            }
        };








        db = new My_DB(MainActivity.this, My_DB.MY_DB_MANE, null, My_DB.MY_DB_VERSION);
        job = db.getReadableDatabase();
        List<UserEntity> lt = db.getUserAll(job);
        UserGlobla.lts=lt;



        ThreadLogStatusTest thr = new ThreadLogStatusTest(hanMain,MainActivity.this);
        new Thread(thr).start();





        //more1 = (LinearLayout)findViewById(R.id.more1);
        //more1t =(TextView)findViewById(R.id.more1t);

        BLinearLayout1=(LinearLayout)findViewById(R.id.BLinearLayout1);












        //取在售的UserEntity
        // if(!lt.isEmpty()){
        //   UserGlobla.lts=lt;
        // }
        //界面2显示信息
        // else{
        //}
        inflater = getLayoutInflater();
        // btn_s=(Button) findViewById(R.id.btn_s);
        //main_tv1=(TextView)findViewById(R.id.main_tv1);

        search_b = (Button) findViewById(R.id.search_button);
        Bmain_body_lin = (RelativeLayout) findViewById(R.id.Bmain_body_line);
        v = inflater.inflate(R.layout.activity_buy_page, null);
        v1 = inflater.inflate(R.layout.activity_sell_page, null);
        v2 = inflater.inflate(R.layout.activity_pull_to_zoom_list_view, null);
        //v3=inflater.inflate(R.layout.refresh,null);

        buy_page_list = (ListView) v.findViewById(R.id.buy_page_list);
        sell_page_list=(ListView)v1.findViewById(R.id.sell_page_list);
        listView = (PullToZoomListViewEx) v2.findViewById(R.id.listview);
        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
       // final TextView rndNum = (TextView) v3.findViewById(R.id.rndNum);

        String[] adapterData = new String[]{"Activity", "Service", "Content Provider", "Intent", "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient",
                "DDMS", "Android Studio", "Fragment", "Loader", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient", "Activity", "Service", "Content Provider", "Intent",
                "BroadcastReceiver", "ADT", "Sqlite3", "HttpClient"};

        listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, adapterData));
        listView.getPullRootView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("zhuwenwu", "position = " + position);
                //Toast.makeText(MainActivity.this, "123", Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("zhuwenwu", "position = " + position);
                if (position==0){
                    Intent intent = new Intent(MainActivity.this, LogForT.class);
                    startActivity(intent);
                }
                //Toast.makeText(MainActivity.this, "123", Toast.LENGTH_LONG).show();

            }
        });

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        AbsListView.LayoutParams localObject = new AbsListView.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        listView.setHeaderLayoutParams(localObject);

        ma = new MyBuyAdapter(CourseGlobla.lts, MainActivity.this);
        buy_page_list.setAdapter(ma);


        mb=new MySellAdapter(UserGlobla.lts,MainActivity.this);
        sell_page_list.setAdapter(mb);

        Bmain_body_lin.addView(v);
        //Bmain_body_lin.removeAllViews();

        flag='b';








        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeView.setRefreshing(false);
                        double f = Math.random();
                        //rndNum.setText(String.valueOf(f));
                    }
                }, 3000);
            }
        });



















        sell = (Button) findViewById(R.id.btn_sell);
        buy = (Button) findViewById(R.id.btn_buy);
        self =(Button)findViewById(R.id.btn_self);
        search_b = (Button) findViewById(R.id.search_button);
        sell.setOnClickListener(new OnClickListener() {
            public void onClick(View v0) {
                BLinearLayout1.setVisibility(View.VISIBLE);

                ActionBar actionBar = getActionBar();
                actionBar.show();
                search_b.setText("搜索");
                Bmain_body_lin.removeAllViews();
                Bmain_body_lin.addView(v1);
                sell.setBackgroundResource(R.drawable.gree_background);
                buy.setBackgroundResource(R.drawable.white_bankground);
                self.setBackgroundResource(R.drawable.white_bankground);
                flag='b';

            }
        });
        buy.setOnClickListener(new OnClickListener() {
            public void onClick(View v0) {
                BLinearLayout1.setVisibility(View.VISIBLE);
                ActionBar actionBar = getActionBar();
                actionBar.show();
                //actionBar.
                //actionBar.setDisplayShowTitleEnabled(false);
                //actionBar.setElevation(100);
                //actionBar.setHideOffset(0);
                Bmain_body_lin.removeAllViews();
                search_b.setText("搜索");
                // Bmain_body_lin.addView(v);
                sell.setBackgroundResource(R.drawable.white_bankground);
                buy.setBackgroundResource(R.drawable.gree_background);
                self.setBackgroundResource(R.drawable.white_bankground);

                // List<String> lt= new ArrayList<>();  //=db.getUserAll(job);
               // if(!CourseGlobla.lts.isEmpty()){


                    mb=new MySellAdapter(UserGlobla.lts,MainActivity.this);
                    sell_page_list.setAdapter(mb);
                    Bmain_body_lin.addView(v);
               // }
               // else{
               // }
                flag='s';

            }
        });

        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BLinearLayout1.setVisibility(View.GONE);
                ActionBar actionBar = getActionBar();
                actionBar.hide();
                //startActivity(new Intent(Buy_Activity.this, PullToZoomListActivity.class));

                sell.setBackgroundResource(R.drawable.white_bankground);
                buy.setBackgroundResource(R.drawable.white_bankground);
                self.setBackgroundResource(R.drawable.gree_background);
                Bmain_body_lin.removeAllViews();
                Bmain_body_lin.addView(v2);
            }
        });
        //tv_login=(Button)findViewById(R.id.tv_login);


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
                                lt2 = new ArrayList<takingCourseEntity>();
                                String param = sh_e.getText().toString().trim();
                                for (takingCourseEntity str : CourseGlobla.lts) {
                                    if (str.getCourse() != null && str.getCourse().contains(param)) {
                                        if (!lt2.contains(str)) {
                                            lt2.add(str);
                                        }
                                    }
                                }
                                if (lt2.isEmpty()) {
                                    //弹出提示框
                                }
                                ma2 = new MyBuyAdapter(lt2, MainActivity.this);
                                sh_lv.setAdapter(ma2);
                            }
                        } else if (flag == 's') {
                            //sell.setBackgroundResource(R.drawable.white_bankground);
                            //buy.setBackgroundResource(R.drawable.gree_background);
                            if (CourseGlobla.lts != null) {
                                lt3 = new ArrayList<UserEntity>();
                                String param = sh_e.getText().toString().trim();
                                for (UserEntity str : UserGlobla.lts) {
                                    if (str.getBook() != null && str.toString().contains(param)) {
                                        if (!lt3.contains(str)) {
                                            lt3.add(str);
                                        }
                                    }
                                }
                                if (lt3.isEmpty()) {
                                    //弹出提示框
                                }
                                ma3 = new MySellAdapter(lt3, MainActivity.this);
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
        buy_page_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(MainActivity.this, ShowMessageActivity.class);
                it.putExtra("bookName", UserGlobla.lts.get(i).getBook());
                it.putExtra("position", i);
                startActivityForResult(it, MAIN_ACTIVITY);

            }
        });
        sell_page_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(MainActivity.this, ShowMessageActivity_.class);
                it.putExtra("bookName", UserGlobla.lts.get(i).toString());
                it.putExtra("position", i);
                startActivityForResult(it, MAIN_ACTIVITY);
            }
        });


    }







    public void onBackPressed() {
        quitToast();
    }
    /*
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        System.out.println(keyCode + "...." + event.getKeyCode());
        if(keyCode == KeyEvent.KEYCODE_BACK){
            quitToast();
        }
        return super.onKeyDown(keyCode, event);
    }
    */
    private void quitToast() {
        if(null == toast.getView().getParent()){
            toast.show();
        }else{
            System.exit(0);
        }
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