package com.wallspeed.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ThoLH on 9/30/15.
 */
public class PhotoItem implements Parcelable {

    private String mThumbUrl, mFullUrl, mOriginalUrl;

    public PhotoItem() {

    }

    public PhotoItem(String mThumbUrl, String mFullUrl, String mOriginalUrl) {
        this.mThumbUrl = mThumbUrl;
        this.mFullUrl = mFullUrl;
        this.mOriginalUrl = mOriginalUrl;
    }

    protected PhotoItem(Parcel in) {
        mThumbUrl = in.readString();
        mFullUrl = in.readString();
        mOriginalUrl = in.readString();
    }

    public static final Creator<PhotoItem> CREATOR = new Creator<PhotoItem>() {
        @Override
        public PhotoItem createFromParcel(Parcel in) {
            return new PhotoItem(in);
        }

        @Override
        public PhotoItem[] newArray(int size) {
            return new PhotoItem[size];
        }
    };

    public String getThumbUrl() {
        return mThumbUrl;
    }

    public void setThumbUrl(String mThumbUrl) {
        this.mThumbUrl = mThumbUrl;
    }

    public String getFullUrl() {
        return mFullUrl;
    }

    public void setFullUrl(String mFullUrl) {
        this.mFullUrl = mFullUrl;
    }

    public String getOriginalUrl() {
        return mOriginalUrl;
    }

    public void setOriginalUrl(String mOriginalUrl) {
        this.mOriginalUrl = mOriginalUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mThumbUrl);
        parcel.writeString(mFullUrl);
        parcel.writeString(mOriginalUrl);
    }
}
