package com.example.zy.stry;

import com.example.zy.stry.lib.DatabaseHandler;
import com.example.zy.stry.lib.NetWorkChecker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zy.stry.adapter.TabsAdapter;
import com.example.zy.stry.lib.User;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
    public static Calendar c = Calendar.getInstance();
    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    public static ExecutorService executorService = Executors.newFixedThreadPool(5);


    private NetWorkChecker netWorkChecker = null;
    private User user = null;

    public static final int MAIN_ACTIVITY = 100;
    public static boolean hvNetwork = false;
    public static final String PREFS_NAME = "MyPrefs";

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

//
//        db.getWritableDatabase().execSQL("DROP TABLE cart");
//
//        String CREATE_CART_TABLE = "CREATE TABLE cart ("
//                + "id_" + " INTEGER PRIMARY KEY , "
//                + "sell_id INTEGER , add_time TEXT )";
//
//
//        db.getWritableDatabase().execSQL(CREATE_CART_TABLE);
        //SQLiteDatabase job=db.getWritableDatabase();
        //db.onUpgrade(job,0,1);
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
                //toolbar.setTitle(TabsAdapter.TITLES[position]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


//        hanMain.sendEmptyMessage(-1);

//        final  Handler hanMain = new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//
//                //more1 = (LinearLayout)findViewById(R.id.more1);
//                //more1t =(TextView)findViewById(R.id.more1t);
//                switch (msg.what) {
//                    case 1:
//                    case 2:
//                    case 3:
//                        //more1.setVisibility(View.GONE);
//                        break;
//                    case 4:
//                        // more1.setVisibility(View.VISIBLE);
//                        break;//数据库空 list else 显示数据库
//                    case -1:
//                        //more1.setVisibility(View.VISIBLE);
//                        //more1t.setText("登录显示更多");
//                        break;//清空数据库，1页登录，2页登录 3页登录 注册
//                    case -4:
//                        //more1.setVisibility(View.VISIBLE);
//                        //more1t.setText("绑定教务处显示个性推荐");
//                        break;//1页 绑定 2页绑定 3页 隐藏
//                    default:
//                        break;
//                }
//
//            }
//        };

        //List<BookEntity> lt = db.getUserAll(job);
        //BookGlobla.lts=lt;


//        if (LogStatusGlobla.getLogStatus() == -1) {
//            //  ThreadLogStatusTest thr = new ThreadLogStatusTest(hanMain,MainActivity.this);
//            //  new Thread(thr).start();
//        }

    }
    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

        int count = fmg.getBackStackEntryCount();

        if (count == 0) {
            quitToast();
            super.onBackPressed();
            //additional code
        } else {
            showToast("后退");
            FragmentManager.BackStackEntry backEntry= fmg.getBackStackEntryAt(count - 1);
//            String str=backEntry.getName();
//            Fragment fragment=fmg.findFragmentByTag(str);
//            FragmentTransaction ft = MainActivity.fmg.beginTransaction();
//            ft.detach();

//                ft.add(R.id.fragment_2, new ManagementFragment());
//            ft.addToBackStack("navigation");
//            ft.commitAllowingStateLoss();
            fmg.popBackStack();
        }

    }

//
//    public void onBackPressed() {
//
//    }
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
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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