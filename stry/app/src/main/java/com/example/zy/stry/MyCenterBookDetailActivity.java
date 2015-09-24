package com.example.zy.stry;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.graphics.Palette;


import com.bumptech.glide.Glide;
import com.example.zy.stry.fragment.MCDetailFragment;
import com.example.zy.stry.util.UserbookGlobla;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 15/9/21.
 */
public class MyCenterBookDetailActivity extends AppCompatActivity {
    String bookname;
    private int mposition;
    ImageView header=null;
    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycentre_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.Mc_detail_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Parallax Tabs");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);}
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//
//        　∧__∧
//        　( ●ω●)
//        　｜つ／(＿＿＿
//        ／└-(＿＿＿_／

        bookname =  getIntent().getStringExtra("book");
        mposition =  getIntent().getIntExtra("position", -1);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.Mc_detail_viewpager);
        setupViewPager(viewPager);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.Mc_detail_tabs);
        tabLayout.setupWithViewPager(viewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.Mc_detail_collasp_toolbar);
        //collapsingToolbarLayout.setTitleEnabled(false);
        collapsingToolbarLayout.setTitle(bookname);

        header = (ImageView) findViewById(R.id.Mc_detail_head_image);

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

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        showToast("One");
                        break;
                    case 1:
                        showToast("Two");

                        break;
                    case 2:
                        showToast("Three");

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
        adapter.addFrag(new MCDetailFragment(), "infor",bookname,mposition);
        adapter.addFrag(new MCDetailFragment(), "message",bookname,mposition);
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

        public void addFrag(Fragment fragment, String title,String bookname,int position) {
            Bundle args = new Bundle();
            args.putSerializable("book", bookname);
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
    @Override
    public void onStart() {
        super.onStart();
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
}
