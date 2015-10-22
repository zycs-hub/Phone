package com.example.zy.stry.adapter;

import com.example.zy.stry.Fragment1;
import com.example.zy.stry.Fragment2;
import com.example.zy.stry.Fragment3;
import com.example.zy.stry.MainActivity;
import com.example.zy.stry.ManagementFragment;
import com.example.zy.stry.R;
import com.example.zy.stry.ShopFragment;
import com.viewpagerindicator.IconPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 15-8-26.
 */
public class TabsAdapter extends FragmentPagerAdapter  {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public static final String[] TITLES = new String[] { "商城",  "管理", "个人" };

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private FragmentManager mfm;

    public TabsAdapter(FragmentManager fm) {
        super(fm);
        mfm = fm;
        mFragmentList.add(new ShopFragment());
        mFragmentList.add(new ManagementFragment());
        mFragmentList.add(new  Fragment3());
    }

    @Override
    public Fragment getItem(int index) {
        return mFragmentList.get(index);

//        switch (index) {
//            case 0:
//                return new ShopFragment();
//            case 1:
//                return new ManagementFragment();
//            case 2:
////                Fragment frg2 = null;
////                if (frg2 == null)
////                {
////                    frg2 = ProfileFragment(new ProfileFragmentListener() {
////                        public void onSwitchToLoginFragment() {
////                            mfm.beginTransaction().remove(frg2).commit();
////                            frg2 = new Login();
////                            notifyDataSetChanged();
////                        }
////                    });
////                }
////                return frg2;
//                return new Fragment3();
//        }
//
//        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return TITLES[position % TITLES.length];
    }


    @Override
    public int getCount()
    {
        // get item count - equal to number of tabtitles
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public interface ProfileFragmentListener {
        void onSwitchToNextFragment();
    }

}