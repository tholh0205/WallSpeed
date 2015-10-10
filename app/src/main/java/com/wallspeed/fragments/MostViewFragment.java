package com.wallspeed.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallspeed.R;
import com.wallspeed.adapters.MostViewAdapter;

/**
 * Created by ThoLH on 9/27/15.
 */
public class MostViewFragment extends TabFragment {
    private RecyclerView mRecyclerView;
    private String[] data = new String[100];
    private boolean isDraggingRecyclerView = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        for (int i = 0; i < data.length; i++) {
            data[i] = "" + (i + 1);
        }
        return inflater.inflate(R.layout.most_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MostViewAdapter mostViewAdapter = new MostViewAdapter();
        mostViewAdapter.setOnItemClickListener(new MostViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                getBaseActivity().showFragment(FragmentItem.FragmentType.REUSE, null, R.anim.fragment_enter, R.anim.fragment_exit);
            }
        });
        mRecyclerView.setAdapter(mostViewAdapter);
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
