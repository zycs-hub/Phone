package com.example.zy.stry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.zy.stry.entity.MessageEntity;
import com.example.zy.stry.entity.Sell;
import com.example.zy.stry.fragment.MCDeMessFragment;
import com.example.zy.stry.fragment.MCDetailFragment;
import com.example.zy.stry.lib.BookOperarion;
import com.example.zy.stry.lib.Config;
import com.example.zy.stry.lib.DatabaseHandler;
import com.example.zy.stry.lib.Function;
import com.example.zy.stry.lib.MessageOperation;
import com.example.zy.stry.util.UserbookGlobla;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zy on 15/9/21.
 */
public class MyCenterBookDetailActivity extends AppCompatActivity {
    String bookname;
    private int mposition;
    int bid;
    ImageView header=null;
    ViewPagerAdapter adapter;
    FloatingActionButton action_on,action_stop,action_done,action_off;

    SharedPreferences shared_preferences;
    public static final String PREFS_NAME = "MyPrefs";
    private String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycentre_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.Mc_detail_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);}
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        action_on = (FloatingActionButton) findViewById(R.id.action_on);
        action_off = (FloatingActionButton) findViewById(R.id.action_off);
        action_done = (FloatingActionButton) findViewById(R.id.action_done);
        action_stop = (FloatingActionButton) findViewById(R.id.action_stop);
        setUpListern();
//
//        　∧__∧
//        　( ●ω●)
//        　｜つ／(＿＿＿
//        ／└-(＿＿＿_／

        bookname =  getIntent().getStringExtra("book");
        mposition =  getIntent().getIntExtra("position", -1);
        bid = getIntent().getIntExtra("bid", -1);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.Mc_detail_viewpager);
        setupViewPager(viewPager);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.Mc_detail_tabs);
        tabLayout.setupWithViewPager(viewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.Mc_detail_collasp_toolbar);
        //collapsingToolbarLayout.setTitleEnabled(false);
        collapsingToolbarLayout.setTitle(bookname);

        header = (ImageView) findViewById(R.id.Mc_detail_head_image);

        shared_preferences = this.getSharedPreferences(PREFS_NAME, this.MODE_PRIVATE);
        username = shared_preferences.getString("username", null);

        if (UserbookGlobla.lts.get(mposition).image!=null)
            Glide.with(header.getContext())
                    .load(UserbookGlobla.lts.get(mposition).image)
                    .fitCenter()
                    .into(header);

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.header);

//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            @SuppressWarnings("ResourceType")
//            @Override
//            public void onGenerated(Palette palette) {
//
//                int vibrantColor = palette.getVibrantColor(R.color.primary_500);
//                int vibrantDarkColor = palette.getDarkVibrantColor(R.color.primary_700);
//                collapsingToolbarLayout.setContentScrimColor(vibrantColor);
//                collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
//            }
//        });
        action_done.setVisibility(View.GONE);
        action_on.setVisibility(View.VISIBLE);
        action_off.setVisibility(View.VISIBLE);
        action_stop.setVisibility(View.GONE);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        action_done.setVisibility(View.GONE);
                        action_on.setVisibility(View.VISIBLE);
                        action_off.setVisibility(View.VISIBLE);
                        action_stop.setVisibility(View.GONE);
                        showToast("One");
                        break;
                    case 1:
                        action_done.setVisibility(View.VISIBLE);
                        action_on.setVisibility(View.GONE);
                        action_off.setVisibility(View.GONE);
                        action_stop.setVisibility(View.VISIBLE);

                        showToast("Two");

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void setupViewPager(ViewPager viewPager) {

         adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MCDetailFragment(), "infor",bookname,mposition, bid);
        adapter.addFrag(new MCDeMessFragment(), "message",bookname,mposition, bid);
        // adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), "MOUSE");
        viewPager.setAdapter(adapter);
    }



    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title,String bookname,int position, int bid) {
            Bundle args = new Bundle();
            args.putSerializable("book", bookname);
            args.putSerializable("bookid", bid);
            args.putSerializable("position", position);
            fragment.setArguments(args);
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
        public MCDetailFragment update() {
            return (MCDetailFragment)getItem(0);

        }
        public MCDeMessFragment update1() {
            return (MCDeMessFragment)getItem(1);

        }
    }


    //    public static class DummyFragment extends Fragment {
//        int color;
//        SimpleRecyclerAdapter adapter;
//
//        public DummyFragment() {
//        }
//
//        @SuppressLint("ValidFragment")
//        public DummyFragment(int color) {
//            this.color = color;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            View view = inflater.inflate(R.layout.dummy_fragment, container, false);
//
//            final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
//            frameLayout.setBackgroundColor(color);
//
//            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);
//
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
//            recyclerView.setLayoutManager(linearLayoutManager);
//            recyclerView.setHasFixedSize(true);
//
//            List<String> list = new ArrayList<String>();
//            for (int i = 0; i < VersionModel.data.length; i++) {
//                list.add(VersionModel.data[i]);
//            }
//
//            adapter = new SimpleRecyclerAdapter(list);
//            recyclerView.setAdapter(adapter);
//
//            return view;
//        }
//    }
    private void setUpListern(){
        action_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMessage(0);

                adapter.update1().upd();
                //actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
        action_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMessage(1);

                adapter.update1().upd();

                //actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
        action_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMessage(2);

                adapter.update1().upd();

                //actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
        action_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMessage(3);

                adapter.update1().upd();

                //actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
    }
    private void setMessage(int type){
        Calendar c = Calendar.getInstance();
        int y=c.get(Calendar.YEAR);
        int m=c.get(Calendar.MONTH);
        int d=c.get(Calendar.DAY_OF_MONTH);
        int h=c.get(Calendar.HOUR);
        int mi=c.get(Calendar.MINUTE);
//        Message message =new Message();
//        message.year=Integer.toString(y);
//        message.moon=Integer.toString(m);
//        message.day=Integer.toString(d);
//        message.hour=Integer.toString(h);
//        message.min=Integer.toString(mi);
        String mess="";
        int isRead=2;
        final int sellid = UserbookGlobla.lts.get(mposition)._id;
        switch (type){
            case 0:
                mess="物品已下架";
//                message.message="物品已下架";
                isRead=0;
//                message.isRead=0;

                MainActivity.db.editTable(Sell.SellEntity.TABLE_NAME, "is_selling", "0",
                        Sell.SellEntity.KEY_ID, Integer.toString(sellid));
                Message msg = new Message();
                break;
            case 1:
                mess="物品已上架";
//                message.message="物品已上架";
                isRead=0;
//                message.isRead=0;
                MainActivity.db.editTable(Sell.SellEntity.TABLE_NAME, "is_selling", "1",
                        Sell.SellEntity.KEY_ID, Integer.toString(sellid));

                break;
            case 2:
                mess="已取消交易，物品已重新上架";
//                message.message="已取消交易，物品已重新上架";
                MainActivity.db.editTable(Sell.SellEntity.TABLE_NAME, "is_selling", "1",
                        Sell.SellEntity.KEY_ID, Integer.toString(sellid));
                MainActivity.db.editTable(Sell.SellEntity.TABLE_NAME, "buyer", "",
                        Sell.SellEntity.KEY_ID, Integer.toString(sellid));
                isRead=1;
//                message.isRead=1;
                break;
            case 3:
                mess="交易已结束，点击“你的交易”查看交易纪录";
//                message.message="交易已结束，点击“你的交易”查看交易纪录";
                isRead=1;
//                message.isRead=1;
                MainActivity.db.editTable(Sell.SellEntity.TABLE_NAME, "is_sold", "1",
                        Sell.SellEntity.KEY_ID, Integer.toString(sellid));
                break;
        }
        MessageEntity messageEntity =new MessageEntity();
        messageEntity.year=y;
        messageEntity.moon=m;
        messageEntity.day=d;
        messageEntity.hour=h;
        messageEntity.min=mi;
        messageEntity.message=mess;
        messageEntity.data=Integer.toString(h)+":"+Integer.toString(mi);
        UserbookGlobla.lts.get(mposition).messageEntities.add(0,messageEntity);


        DatabaseHandler db = MainActivity.db;
        int bid = UserbookGlobla.lts.get(mposition).bid;
        db.addMessage
                (
                        mess,
                        username,
                        UserbookGlobla.lts.get(mposition).username,
                        y,
                        m, d,
                        h, mi,
                        isRead, bid);

        MessageOperation.addMsg task = new MessageOperation().new addMsg(mess,
                username,
                UserbookGlobla.lts.get(mposition).username,
                y,
                m, d,
                h, mi,
                isRead, bid, new Function<JSONObject, Void>() {
            @Override
            public Void apply(JSONObject json) {
                if(json == null) {
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                } else {
                    try {
                        if(json.getString(Config.KEY_SUCCESS) != null){
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        });
        MainActivity.executorService.submit(task);
        //UserbookGlobla.lts.get(mposition).messages.add(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserbookGlobla.lts.get(mposition).image!=null)
            Glide.with(header.getContext())
                    .load(UserbookGlobla.lts.get(mposition).image)
                    .fitCenter()
                    .into(header);
        //if (adapter.update()!=null)
//        adapter.update().update(position);

            if (adapter.update()!=null)
        adapter.update().update(mposition);
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(getApplicationContext(), "验证失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), Config.LOGIN_INFO_ERROR, Toast.LENGTH_SHORT).show();
                    break;


            }
        }
    };
}
