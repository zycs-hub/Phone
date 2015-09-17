package com.example.zy.stry.lib;

import com.example.zy.stry.MyCenterFragment;
import com.example.zy.stry.ProfileFragment;
import com.example.zy.stry.R;
import com.example.zy.stry.ShopFragment;
import com.viewpagerindicator.IconPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by wendy on 15-8-26.
 */
public class TabsAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    public static final String[] TITLES = new String[] { "商城",  "货架", "个人" };
    private static final int[] ICONS = new int[] {
            R.drawable.ic_img_user_default,
            R.drawable.ic_img_user_default,
            R.drawable.ic_img_user_default,
    };

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new ShopFragment();
            case 1:
                return new MyCenterFragment();
            case 2:
                return new ProfileFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return TITLES[position % TITLES.length];
    }

    @Override public int getIconResId(int position) {
        return ICONS[position];
    }

    @Override
    public int getCount()
    {
        // get item count - equal to number of tabtitles
        return 3;
    }

}