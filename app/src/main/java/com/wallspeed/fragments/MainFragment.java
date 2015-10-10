package com.wallspeed.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wallspeed.R;
import com.wallspeed.adapters.MainTabAdapter;

/**
 * Created by ThoLH on 9/25/15.
 */
public class MainFragment extends BaseFragment {

    private ViewPager mViewPager;
    private MainTabAdapter mMainTabAdapter;
    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;

    @Override
    public View onCreateView(Context context, ViewGroup container) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.main_drawer);
        mViewPager = (ViewPager) view.findViewById(R.id.main_view_pager);
        mViewPager.setPageMargin(5);
        mMainTabAdapter = new MainTabAdapter(getActivity().getSupportFragmentManager(), this);
        mViewPager.setAdapter(mMainTabAdapter);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mTabLayout != null)
                    mTabLayout.setScrollPosition(position, positionOffset, true);
            }

            @Override
            public void onPageSelected(int position) {
                if (mMainTabAdapter != null)
                    mMainTabAdapter.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onSetupActionBar() {
        super.onSetupActionBar();
        ActionBar actionBar = getActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(getUpIndicator());
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Gallery");
        }
    }

    @Override
    protected Drawable getUpIndicator() {
        Drawable drawable = getActivity().getResources().getDrawable(R.drawable.ic_drawer);
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout != null) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                else
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                return true;
            }
            if (mViewPager != null)
                mViewPager.setCurrentItem(0, false);
        }
        return false;
    }

    @Override
    public boolean onBackPressed() {
        if (mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        }
        if (mViewPager != null)
            mViewPager.setCurrentItem(0, false);
        return false;
    }
}
