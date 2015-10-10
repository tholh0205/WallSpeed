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
public class MostViewAdapter extends RecyclerView.Adapter<MostViewAdapter.ViewHolder> {
    private OnItemClickListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleLeft, mTitleRight;
        SimpleDraweeView mDraweeViewLeft, mDraweeViewRight;
        View mItemViewLeft, mItemViewRight;

        public ViewHolder(View view) {
            super(view);
            mTitleLeft = (TextView) view.findViewById(R.id.newest_title_left);
            mTitleRight = (TextView) view.findViewById(R.id.newest_title_right);
            mDraweeViewLeft = (SimpleDraweeView) view.findViewById(R.id.img_newest_left);
            mDraweeViewRight = (SimpleDraweeView) view.findViewById(R.id.img_newest_right);
            mItemViewLeft = view.findViewById(R.id.item_view_left);
            mItemViewRight = view.findViewById(R.id.item_view_right);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_view_home_item, parent, false);
//        view.setBackgroundColor(parent.getContext().getResources().getColor(android.R.color.darker_gray));
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitleLeft.setText("12K");
        holder.mTitleRight.setText("22K");
        Uri uri = Uri.parse("http://www.southbayriders.com/forums/attachments/410650/");
        Uri uri2 = Uri.parse("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcST2tICFgdUIY17gywhnXUAnmv9upL1HXVPqu-Ewda7eYNqOMm6");
        holder.mDraweeViewLeft.setImageURI(uri);
        holder.mDraweeViewRight.setImageURI(uri2);
        final int positionLeft = position * 2;
        holder.mItemViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(view, positionLeft);
                }
            }
        });
        final int positionRight = position * 2 + 1;
        holder.mItemViewRight.setOnClickListener(new View.OnClickListener() {
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
