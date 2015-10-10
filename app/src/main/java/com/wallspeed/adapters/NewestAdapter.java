package com.wallspeed.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wallspeed.R;
import com.wallspeed.data.model.PhotoItem;

import java.util.ArrayList;

/**
 * Created by ThoLH on 9/20/15.
 */
public class NewestAdapter extends RecyclerView.Adapter<NewestAdapter.ViewHolder> {
    public static final int THUMB_MODE = 1, FULL_MODE = 2, ORIGINAL_MODE = 3;

    private OnItemClickListener mListener;
    private ArrayList<PhotoItem> mData = new ArrayList<>();
    private int mMode = THUMB_MODE;

    public void setData(ArrayList<PhotoItem> data) {
        this.mData = new ArrayList<>(data);
    }

    public void setLoadMode(int loadMode) {
        this.mMode = loadMode;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        SimpleDraweeView mDraweeView;
        View mItemView;

        public ViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.newest_title);
            mDraweeView = (SimpleDraweeView) view.findViewById(R.id.img_newest);
            mItemView = view;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newest_home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitle.setText("12K");
        PhotoItem item = mData.get(position);
        String url = mMode == THUMB_MODE ? item.getThumbUrl() : mMode == FULL_MODE ? item.getFullUrl() : item.getOriginalUrl();
        Uri uri = Uri.parse(url);//Uri.parse("http://www.southbayriders.com/forums/attachments/410650/");
        holder.mDraweeView.setImageURI(uri);
        final int positionLeft = position;
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(view, positionLeft);
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mListener = l;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
