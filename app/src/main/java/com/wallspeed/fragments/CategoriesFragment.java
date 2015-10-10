package com.wallspeed.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallspeed.R;
import com.wallspeed.adapters.CategoriesAdapter;
import com.wallspeed.global.FragmentData;

/**
 * Created by ThoLH on 9/20/15.
 */
public class CategoriesFragment extends TabFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private String[] data = new String[100];
    private boolean isDraggingRecyclerView = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        for (int i = 0; i < data.length; i++) {
            data[i] = "" + (i + 1);
        }
        return inflater.inflate(R.layout.categories_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mSwipeRefreshLayout != null)
                            mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter();
        categoriesAdapter.setOnItemClickListener(new CategoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                getBaseActivity().presentFragment(BaseActivity.FragmentType.CHAT, null, -1, BaseActivity.TRANSLATION);
                getBaseActivity().getFragmentManagerLayout().showFragment(FragmentData.FragmentType.CHAT, null, 0, false, false);
            }
        });
        mRecyclerView.setAdapter(categoriesAdapter);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int firstVisible = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
                    mSwipeRefreshLayout.setEnabled(firstVisible == 0);
                }
            });
        } else {
            mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    int firstVisible = mRecyclerView.getChildAdapterPosition(mRecyclerView.getChildAt(0));
                    mSwipeRefreshLayout.setEnabled(firstVisible == 0);
                }
            });
        }
//        ListView lv = (ListView) view.findViewById(R.id.listview);
//        lv.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data));
//        view.findViewById(R.id.btnNextFragment).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getBaseActivity().showFragment(FragmentItem.FragmentType.EXIT, null, R.anim.fragment_enter, R.anim.fragment_exit);
//            }
//        });
    }

}
