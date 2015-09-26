package com.wallspeed.adapters;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wallspeed.R;
import com.wallspeed.fragments.CategoriesFragment;
import com.wallspeed.fragments.EmptyFragment;
import com.wallspeed.fragments.MainFragment;
import com.wallspeed.fragments.NewestFragment;

/**
 * Created by ThoLH on 9/20/15.
 */
public class MainTabAdapter extends FragmentStatePagerAdapter {

    private String[] mTabTitles;
    private boolean[] isLoaded = null;
    private MainFragment mMainFragment;
    private Handler mHandlerUI = new Handler(Looper.getMainLooper());

    public MainTabAdapter(FragmentManager fm, MainFragment mainFragment) {
        super(fm);
        this.mMainFragment = mainFragment;
        mTabTitles = mMainFragment.getResources().getStringArray(R.array.main_tab_titles);
        isLoaded = new boolean[mTabTitles.length];
        for (int i = 0; i < isLoaded.length; i++) {
            isLoaded[i] = i == 0;
        }
    }

    public void onPageSelected(int position) {
        if (position > 0 && position < isLoaded.length) {
            if (!isLoaded[position]) {
                isLoaded[position] = true;
                mHandlerUI.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                }, 200);
            }
        }
    }

    @Override
    public int getItemPosition(Object object) {
        if (object == null) return POSITION_UNCHANGED;
        if (object instanceof EmptyFragment) return POSITION_NONE;
//        return super.getItemPosition(object);
        return POSITION_NONE; //Force update ViewPager UI
    }

    @Override
    public Fragment getItem(int position) {
        if (isLoaded[position]) {
            if (position == 1)
                return new NewestFragment();
            return new CategoriesFragment();
        } else {
            return new EmptyFragment();
        }
    }

    @Override
    public int getCount() {
        return mTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }
}
