package com.wallspeed.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wallspeed.R;

/**
 * Created by ThoLH on 9/20/15.
 */
public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {
    private OnItemClickListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTagLeft, mTagMiddle, mTagRight;

        public ViewHolder(View view) {
            super(view);
            mTagLeft = (TextView) view.findViewById(R.id.btn_tag_left);
            mTagMiddle = (TextView) view.findViewById(R.id.btn_tag_middle);
            mTagRight = (TextView) view.findViewById(R.id.btn_tag_right);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_home_item, parent, false);
//        view.setBackgroundColor(parent.getContext().getResources().getColor(android.R.color.darker_gray));
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return 90;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int positionLeft = position * 3;
        holder.mTagLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(view, positionLeft);
                }
            }
        });
        final int positionMiddle = position * 3 + 1;
        holder.mTagMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onItemClick(view, positionMiddle);
            }
        });
        final int positionRight = position * 3 + 2;
        holder.mTagRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onItemClick(view, positionRight);
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
