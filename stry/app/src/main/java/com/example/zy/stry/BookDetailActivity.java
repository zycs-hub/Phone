package com.example.zy.stry;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zy.stry.R;
import com.example.zy.stry.DetailFragment;
import com.bumptech.glide.Glide;
import com.example.zy.stry.entity.Book;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zy on 15/7/1.
 */
public class BookDetailActivity extends AppCompatActivity {


    private ViewPager mViewPager;
    private Book mBook  = new Book();
    private String bookname;
    FrameLayout fab;
    ImageButton fabBtn;
    View fabShadow;
    int _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbar_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        bookname =  getIntent().getStringExtra("book");
        _id = getIntent().getIntExtra("_id", -1);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(bookname);

        ImageView ivImage = (ImageView) findViewById(R.id.ivImage);
        if (mBook==null|| mBook.getImage()==null|| mBook.getImages().getLarge()==null)
        ivImage.setImageResource(R.drawable.splash01);
        else
        Glide.with(ivImage.getContext())
                .load(mBook.getImages().getLarge())
                .fitCenter()
                .into(ivImage);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("内容简介"));
        //tabLayout.addTab(tabLayout.newTab().setText("消息"));
        //tabLayout.addTab(tabLayout.newTab().setText("目录"));

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);

        tabLayout.setupWithViewPager(mViewPager);
        fab = (FrameLayout) findViewById(R.id.myfab_main);
        fabBtn = (ImageButton) findViewById(R.id.myfab_main_btn);
        fabShadow = findViewById(R.id.myfab_shadow);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fabShadow.setVisibility(View.GONE);
            fabBtn.setBackground(getDrawable(R.drawable.ripple_accent));
        }

        fab.startAnimation(animation);

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                   ∧＿∧
                （｡･ω･｡)つ━☆.. ・*。
                ⊂　　 ノ 　 　　・゜+.
　               しーＪ　 　　°。+ *´¨)
　　　 　　              .· ´¸.·*´¨) ¸.·*¨)
　　　　　　 　 　                  (¸.·´ (¸.·’*


                DB操作
                job.execSQL("update courses set  isSelected=1 where book=?", new String[]{na});
                感觉 where 后写 ID 比较好 唯一确定 书

                 */
                //将当前物品编号加入购物车表内

                String formattedDate = MainActivity.df.format(MainActivity.c.getTime());

                MainActivity.db.addInCart(_id, formattedDate);
//                Log.d("CLICK", "FAB CLICK");

                Toast.makeText(getBaseContext(), "已加入购物车", Toast.LENGTH_SHORT).show();
                final FragmentTransaction ft = MainActivity.fmg.beginTransaction();
                ft.replace(R.id.fragment_1,  new CartFragment());
                ft.addToBackStack(null);
                ft.commitAllowingStateLoss();
                finish();

            }
        });
    }


    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(BookDetailInfo.newInstance(mBook.getSummary()), "内容简介");
        //adapter.addFragment(DetailFragment.newInstance(mBook.getAuthor_intro()), "消息");
        //adapter.addFragment(DetailFragment.newInstance(mBook.getCatalog()), "目录");
        mViewPager.setAdapter(adapter);
    }


    static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
