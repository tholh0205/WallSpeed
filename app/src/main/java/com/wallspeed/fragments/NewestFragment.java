package com.wallspeed.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.wallspeed.R;
import com.wallspeed.adapters.NewestAdapter;
import com.wallspeed.data.model.PhotoItem;

import java.util.ArrayList;

/**
 * Created by ThoLH on 9/26/15.
 */
public class NewestFragment extends TabFragment {
    private RecyclerView mRecyclerView;
    private NewestAdapter mNewestAdapter;
    private ArrayList<PhotoItem> mData = new ArrayList<>();
    private boolean isDraggingRecyclerView = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        android.util.Log.d("ThoLH", "NewestFragment onCreateView " + savedInstanceState);
        if (savedInstanceState == null)
            initHardCodeData();
        return inflater.inflate(R.layout.newest_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mNewestAdapter = new NewestAdapter();
        mNewestAdapter.setData(mData);
        mNewestAdapter.setOnItemClickListener(new NewestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                getBaseActivity().showFragment(FragmentItem.FragmentType.EXIT, null, R.anim.fragment_enter, R.anim.fragment_exit);
            }
        });
        mRecyclerView.setAdapter(mNewestAdapter);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rb_thumb:
                        if (mNewestAdapter != null) {
                            mNewestAdapter.setLoadMode(NewestAdapter.THUMB_MODE);
                            mNewestAdapter.notifyDataSetChanged();
                        }
                        break;
                    case R.id.rb_full:
                        if (mNewestAdapter != null) {
                            mNewestAdapter.setLoadMode(NewestAdapter.FULL_MODE);
                            mNewestAdapter.notifyDataSetChanged();
                        }
                        break;
                    case R.id.rb_original:
                        if (mNewestAdapter != null) {
                            mNewestAdapter.setLoadMode(NewestAdapter.ORIGINAL_MODE);
                            mNewestAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        });
    }

    private void initHardCodeData() {
        PhotoItem photoItem = new PhotoItem("http://otomoto.vn/data/photos/m/0/25-1443262655-72963251c47bc4731d2969a5d4955f7b.jpg",
                "http://otomoto.vn/data/photos/m/0/25-1443262655-72963251c47bc4731d2969a5d4955f7b.jpg",
                "http://otomoto.vn/data/photos/o/0/25-1443262655-f49a17c36a53218cdfdf6f05f9f2cbb8.jpg");
        mData.add(photoItem);
        photoItem = new PhotoItem("http://otomoto.vn/data/photos/m/0/31-1443265116-177d393ab857111f6c721f865167f8f3.jpg",
                "http://otomoto.vn/data/photos/l/0/31-1443265116-4eabefa57dab925ace5d7cf861cee99d.jpg",
                "http://otomoto.vn/data/photos/o/0/31-1443265116-6b33f52157d0cafde88d1386456bf23b.jpg");
        mData.add(photoItem);
        photoItem = new PhotoItem("http://otomoto.vn/data/photos/m/0/19-1443257854-32f77acd723b6e8ef2d0d6c673419cdd.jpg",
                "http://otomoto.vn/data/photos/l/0/19-1443257854-7ca9af272ce458cfd54816352a27e190.jpg",
                "http://otomoto.vn/data/photos/o/0/19-1443257854-a1341a76663068413a54aab95613c95e.jpg");
        mData.add(photoItem);
        photoItem = new PhotoItem("http://otomoto.vn/data/photos/m/0/18-1443257853-ee5f5b2ddccb997ea0ae67bed4e4607e.jpg",
                "http://otomoto.vn/data/photos/l/0/18-1443257853-5705e29559b081ea460e4835e10e98e1.jpg",
                "http://otomoto.vn/data/photos/o/0/18-1443257853-aa5b43d3658fd4b85913c885a829131a.jpg");
        mData.add(photoItem);
        photoItem = new PhotoItem("http://otomoto.vn/data/photos/m/0/21-1443261575-b137f10b333b87397376c7b6a49d5c00.jpg",
                "http://otomoto.vn/data/photos/l/0/21-1443261575-e4c0ac8ba79964a3dd287a3dee50f77a.jpg",
                "http://otomoto.vn/data/photos/o/0/21-1443261575-14fb51ad1c3e874b24beec1955b235aa.jpg");
        mData.add(photoItem);
        photoItem = new PhotoItem("http://otomoto.vn/data/photos/m/0/23-1443261577-141a45ca133f3aa0b712914e44abc35e.jpg",
                "http://otomoto.vn/data/photos/l/0/23-1443261577-9f89c69599ac1ca8f642eab271f251d1.jpg",
                "http://otomoto.vn/data/photos/o/0/23-1443261577-850daa21afc60da8f13972eaba709f51.jpg");
        mData.add(photoItem);
        photoItem = new PhotoItem("http://otomoto.vn/data/photos/m/0/10-1443253626-c77fffb2e2ca3d171b62a61ed10a04e5.jpg",
                "http://otomoto.vn/data/photos/l/0/10-1443253626-03690549439404f53e8768706ab3d4e2.jpg",
                "http://otomoto.vn/data/photos/o/0/10-1443253626-8f3bd3252d8b0faf8154e69e3fdb5b92.jpg");
        mData.add(photoItem);
        photoItem = new PhotoItem("http://otomoto.vn/data/photos/m/0/13-1443253628-0036e1fb0eb5aa30959bb588adc401a8.jpg",
                "http://otomoto.vn/data/photos/l/0/13-1443253628-d0fa37d6f1f8d5f8f12c299fae86f131.jpg",
                "http://otomoto.vn/data/photos/o/0/13-1443253628-b50567fce6318d261c7daab8c33eaaed.jpg");
        mData.add(photoItem);
        photoItem = new PhotoItem("http://otomoto.vn/data/photos/m/0/6-1443252656-a54ddcfe76ead59b84df98dc0698e722.jpg",
                "http://otomoto.vn/data/photos/l/0/6-1443252656-dabb19354e2cc5f00b7d9ac2851418d4.jpg",
                "http://otomoto.vn/data/photos/o/0/6-1443252656-9cce67cd68543595d32d60dd095c6e38.jpg");
        mData.add(photoItem);
        photoItem = new PhotoItem("http://otomoto.vn/data/photos/m/0/14-1443254622-6eebec81663fd1b85718f0e694a5a312.jpg",
                "http://otomoto.vn/data/photos/l/0/14-1443254622-ec3ad92072b55ac886782bc630fa9813.jpg",
                "http://otomoto.vn/data/photos/o/0/14-1443254622-a32f42fe5c2ad5f3bf955da2eed002be.jpg");
        mData.add(photoItem);
    }
}
