package com.example.zy.stry;

import com.example.zy.stry.lib.DatabaseHandler;
import com.example.zy.stry.lib.NetWorkChecker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.lib.TabsAdapter;
import com.example.zy.stry.lib.User;
import com.example.zy.stry.util.LogStatusGlobla;
import com.example.zy.stry.util.MyBuyAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
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

    public static ViewPager viewPager;
    private TabsAdapter myAdapter;
    private TabLayout mIndicator ;
    private Toolbar toolbar;
    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;

    static DatabaseHandler db;
    static FragmentManager fmg;

    private NetWorkChecker netWorkChecker = null;
    private User user = null;

    public static final int MAIN_ACTIVITY = 100;
    public static boolean hvNetwork = false;
    public static final String PREFS_NAME = "MyPrefs";

    private LayoutInflater inflater = null;

    private Toast toast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initilization
        toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIndicator = (TabLayout) findViewById(R.id.id_indicator);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        myAdapter = new TabsAdapter(getSupportFragmentManager());
        netWorkChecker = new NetWorkChecker(getApplicationContext());
        hvNetwork = netWorkChecker.isOnline();
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefEditor = settings.edit();
        db = new DatabaseHandler(getApplicationContext());
        fmg = getSupportFragmentManager();
        toast = Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT);


        viewPager.setAdapter(myAdapter);
        mIndicator.setupWithViewPager(viewPager);

//        if( !user.isUserLoggedIn(getApplicationContext()) ) {
//
//
//        }

        mIndicator.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                showToast(TabsAdapter.TITLES[position]);
                toolbar.setTitle(TabsAdapter.TITLES[position]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


//        hanMain.sendEmptyMessage(-1);

        final  Handler hanMain = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                //more1 = (LinearLayout)findViewById(R.id.more1);
                //more1t =(TextView)findViewById(R.id.more1t);
                switch (msg.what) {
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

        //db = new My_DB(MainActivity.this, My_DB.MY_DB_MANE, null, My_DB.MY_DB_VERSION);
        //job = db.getReadableDatabase();
        //List<BookEntity> lt = db.getUserAll(job);
        //BookGlobla.lts=lt;


        if (LogStatusGlobla.getLogStatus() == -1) {
            //  ThreadLogStatusTest thr = new ThreadLogStatusTest(hanMain,MainActivity.this);
            //  new Thread(thr).start();
        }


        //more1 = (LinearLayout)findViewById(R.id.more1);
        //more1t =(TextView)findViewById(R.id.more1t);

//        BLinearLayout1=(LinearLayout)findViewById(R.id.BLinearLayout1);


        //取在售的BookEntity
        // if(!lt.isEmpty()){
        //   BookGlobla.lts=lt;
        // }
        //界面2显示信息
        // else{
        //}
//        inflater = getLayoutInflater();
//        // btn_s=(Button) findViewById(R.id.btn_s);
//        //main_tv1=(TextView)findViewById(R.id.main_tv1);
//
//        Bmain_body_lin = (RelativeLayout) findViewById(R.id.Bmain_body_line);
//        v = inflater.inflate(R.layout.activity_buy_page, null);
//        v1 = inflater.inflate(R.layout.activity_sell_page, null);
//
//        buy_page_list = (ListView) v.findViewById(R.id.buy_page_list);
//        sell_page_list=(ListView)v1.findViewById(R.id.sell_page_list);
//        listView = (PullToZoomListViewEx) v2.findViewById(R.id.listview);
//        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
//       // final TextView rndNum = (TextView) v3.findViewById(R.id.rndNum);

//
//        listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, adapterData));
//        listView.getPullRootView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("zhuwenwu", "position = " + position);
//                //Toast.makeText(MainActivity.this, "123", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("zhuwenwu", "position = " + position);
//                if (position==0){
//                    Intent intent = new Intent(MainActivity.this, LogForT.class);
//                    startActivity(intent);
//                }
//                //Toast.makeText(MainActivity.this, "123", Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
//        int mScreenHeight = localDisplayMetrics.heightPixels;
//        int mScreenWidth = localDisplayMetrics.widthPixels;
//        AbsListView.LayoutParams localObject = new AbsListView.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
//        listView.setHeaderLayoutParams(localObject);
//
//        ma = new MyBuyAdapter(CourseGlobla.lts, MainActivity.this);
//        buy_page_list.setAdapter(ma);
//
//
//        mb=new MySellAdapter(BookGlobla.lts,MainActivity.this);
//        sell_page_list.setAdapter(mb);
//
//        Bmain_body_lin.addView(v);
//
//        flag='b';
//

//
//        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeView.setRefreshing(true);
//                Log.d("Swipe", "Refreshing Number");
//                (new Handler()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeView.setRefreshing(false);
//                        double f = Math.random();
//                        //rndNum.setText(String.valueOf(f));
//                    }
//                }, 3000);
//            }
//        });
//
//
//        sell = (Button) findViewById(R.id.btn_sell);
//        buy = (Button) findViewById(R.id.btn_buy);
//        self =(Button)findViewById(R.id.btn_self);
//        sell.setOnClickListener(new OnClickListener() {
//            public void onClick(View v0) {
//                BLinearLayout1.setVisibility(View.VISIBLE);
//
//                ActionBar actionBar = getActionBar();
//                actionBar.show();
//                search_b.setText("搜索");
//                Bmain_body_lin.removeAllViews();
//                Bmain_body_lin.addView(v1);
//                sell.setBackgroundResource(R.drawable.gree_background);
//                buy.setBackgroundResource(R.drawable.white_bankground);
//                self.setBackgroundResource(R.drawable.white_bankground);
//                flag='b';
//
//            }
//        });
//        buy.setOnClickListener(new OnClickListener() {
//            public void onClick(View v0) {
//                BLinearLayout1.setVisibility(View.VISIBLE);
//                ActionBar actionBar = getActionBar();
//                actionBar.show();
//                //actionBar.
//                //actionBar.setDisplayShowTitleEnabled(false);
//                //actionBar.setElevation(100);
//                //actionBar.setHideOffset(0);
//                Bmain_body_lin.removeAllViews();
//                search_b.setText("搜索");
//                // Bmain_body_lin.addView(v);
//                sell.setBackgroundResource(R.drawable.white_bankground);
//                buy.setBackgroundResource(R.drawable.gree_background);
//                self.setBackgroundResource(R.drawable.white_bankground);
//
//                // List<String> lt= new ArrayList<>();  //=db.getUserAll(job);
//               // if(!CourseGlobla.lts.isEmpty()){
//
//
//                    mb=new MySellAdapter(BookGlobla.lts,MainActivity.this);
//                    sell_page_list.setAdapter(mb);
//                    Bmain_body_lin.addView(v);
//               // }
//               // else{
//               // }
//                flag='s';
//
//            }
//        });
//

//        //tv_login=(Button)findViewById(R.id.tv_login);


    }
    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));

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