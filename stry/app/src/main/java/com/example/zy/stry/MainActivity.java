package com.example.zy.stry;

import com.example.zy.stry.lib.Config;
import com.example.zy.stry.lib.DatabaseHandler;
import com.example.zy.stry.lib.Function;
import com.example.zy.stry.lib.NetWorkChecker;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zy.stry.adapter.TabsAdapter;
import com.example.zy.stry.lib.User;

import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
    static SharedPreferences settings;
    static SharedPreferences.Editor prefEditor;

    public static DatabaseHandler db;
    static FragmentManager fmg;
    public static Calendar c = Calendar.getInstance();
    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    public static ExecutorService executorService;


    private NetWorkChecker netWorkChecker = null;


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

        executorService = Executors.newFixedThreadPool(5);
//        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

        db = new DatabaseHandler(getApplicationContext());
        fmg = getSupportFragmentManager();


        netWorkChecker = new NetWorkChecker(getApplicationContext());

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefEditor = settings.edit();

        //SQLiteDatabase job=db.getWritableDatabase();
        //db.onUpgrade(job,0,1);

        toast = Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT);

        if(netWorkChecker.isOnline()) {
            Toast.makeText(this, "检查网络......", Toast.LENGTH_SHORT).show();
            isLinked task = new isLinked(new Function<Boolean, Void>() {
                @Override
                public Void apply(Boolean in) {
                    MainActivity.hvNetwork = true;
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    return null;
                }
            });
            MainActivity.executorService.submit(task);
        } else {
            Message msg = new Message();
            msg.what = -1;
            handler.sendMessage(msg);
        }



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

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    hvNetwork = true;
                    viewPager.setAdapter(myAdapter);
                    mIndicator.setupWithViewPager(viewPager);
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
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), "未联网", Toast.LENGTH_SHORT).show();
                    viewPager.setAdapter(myAdapter);
                    mIndicator.setupWithViewPager(viewPager);
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
                    break;

            }
        }
    };

    private class isLinked implements Runnable{
        private Function<Boolean, Void> callBack;
        public isLinked(Function<Boolean, Void> callBack) {
            this.callBack = callBack;
        }

        @Override
        public void run() {
            HttpClient client = new DefaultHttpClient();
            StringBuilder builder = new StringBuilder();
// HttpGet连接对象使用get方式请求

            HttpGet myget = new HttpGet("http://www.baidu.com/");
            try {
// HttpResponse对象，连接成功后的一个响应对象
                HttpResponse response = client.execute(myget);
// 返回值为200，即为服务器成功响应了请求
                if (response.getStatusLine().getStatusCode() == 200) {
                    callBack.apply(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}