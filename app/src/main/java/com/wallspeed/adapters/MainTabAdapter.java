package com.wallspeed.adapters;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wallspeed.R;
import com.wallspeed.fragments.CategoriesFragment;
import com.wallspeed.fragments.EmptyFragment;
import com.wallspeed.fragments.MainFragment;
import com.wallspeed.fragments.MostViewFragment;
import com.wallspeed.fragments.NewUpdateFragment;
import com.wallspeed.fragments.NewestFragment;
import com.wallspeed.fragments.TabFragment;
import com.wallspeed.fragments.TagsFragment;

import java.util.ArrayList;

/**
 * Created by ThoLH on 9/20/15.
 */
public class MainTabAdapter extends FragmentStatePagerAdapter {

    private final int UPDATE_TAB = 1;

    private String[] mTabTitles;
    private boolean[] isLoaded = null;
    private MainFragment mMainFragment;
    private TabFragment[] mLoadedFragments = null;
    private Handler mHandlerUI = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if (msg.what == UPDATE_TAB) {
                    isLoaded[msg.arg1] = true;
                    notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public MainTabAdapter(FragmentManager fm, MainFragment mainFragment) {
        super(fm);
        this.mMainFragment = mainFragment;
        mTabTitles = mMainFragment.getActivity().getResources().getStringArray(R.array.main_tab_titles);
        isLoaded = new boolean[mTabTitles.length];
        for (int i = 0; i < isLoaded.length; i++) {
            isLoaded[i] = i == 0;
        }
        mLoadedFragments = new TabFragment[mTabTitles.length];
    }

    public void onPageSelected(int position) {
        if (position > 0 && position < isLoaded.length) {
            if (!isLoaded[position]) {
//                isLoaded[position] = true;
                Message msg = new Message();
                msg.what = UPDATE_TAB;
                msg.arg1 = position;
                mHandlerUI.removeMessages(UPDATE_TAB);
                mHandlerUI.sendMessageDelayed(msg, 1000);
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
            TabFragment loadedFragment = null;
            if (position < mLoadedFragments.length && mLoadedFragments[position] != null)
                return mLoadedFragments[position];
            if (position == 1) {
                loadedFragment = new NewestFragment();
            } else if (position == 2) {
                loadedFragment = new MostViewFragment();
            } else if (position == 3) {
                loadedFragment = new NewUpdateFragment();
            } else if (position == 4) {
                loadedFragment = new TagsFragment();
            }
            if (loadedFragment == null)
                loadedFragment = new CategoriesFragment();
            mLoadedFragments[position] = loadedFragment;
            return loadedFragment;
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
