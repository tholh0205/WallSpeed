package com.wallspeed.global;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.wallspeed.application.MainApplication;
import com.wallspeed.fragments.BaseFragment;
import com.wallspeed.fragments.ExitFragment;
import com.wallspeed.fragments.MainFragment;
import com.wallspeed.fragments.ReuseFragment;

import java.util.UUID;

/**
 * Created by ThoLH on 9/19/15.
 */
public class FragmentItem implements Parcelable {

    public String tag;
    public final Class<? extends BaseFragment> cls;

    public BaseFragment fragment;
    public final Bundle data = new Bundle();

    public String callerFragmentTag;
    public int requestCode = 0;

    private final FragmentType fragmentType;

    //for fragment animation
    public int animationEnter = 0;
    public int animationExit = 0;

    private FragmentItem(Parcel in) {
        tag = in.readString();

        Object obj = in.readSerializable();
        if (obj instanceof Class<?>) {
            cls = (Class<? extends BaseFragment>) obj;
        } else {
            cls = null;
        }

        Bundle dataTemp = in.readBundle(MainApplication.getInstance().getApplicationContext().getClassLoader());
        if (dataTemp != null) data.putAll(dataTemp);

        fragment = null;
        callerFragmentTag = in.readString();
        requestCode = in.readInt();

        fragmentType = (FragmentType) in.readSerializable();

        animationEnter = in.readInt();
        animationExit = in.readInt();
    }

    public FragmentItem(FragmentType fragmentType, Bundle data) {
        this.cls = fragmentType.getFragmentClass();
        tag = UUID.randomUUID().toString();
        if (data != null) this.data.putAll(data);
        this.fragmentType = fragmentType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tag);
        dest.writeSerializable(cls);
        dest.writeBundle(data);
        dest.writeString(callerFragmentTag);
        dest.writeInt(requestCode);
        dest.writeSerializable(fragmentType);
        dest.writeInt(animationEnter);
        dest.writeInt(animationExit);
    }

    public static final Parcelable.Creator<FragmentItem> CREATOR
            = new Parcelable.Creator<FragmentItem>() {
        public FragmentItem createFromParcel(Parcel in) {
            return new FragmentItem(in);
        }

        public FragmentItem[] newArray(int size) {
            return new FragmentItem[size];
        }
    };

//        public BaseFragment getFragment() {
//            return fragment;
//        }

    public enum FragmentType {
        MAIN(0, MainFragment.class),
        EXIT(1, ExitFragment.class),
        REUSE(2, ReuseFragment.class);

        private int typeId;
        private Class<? extends BaseFragment> cls;

        FragmentType(int typeId, Class<? extends BaseFragment> cls) {
            this.typeId = typeId;
            this.cls = cls;
        }

        public int getTypeId() {
            return typeId;
        }

        public Class<? extends BaseFragment> getFragmentClass() {
            return cls;
        }

        public static FragmentType getFragmentTypeById(int typeId) {
            for (FragmentType typeEnum : FragmentType.values()) {
                if (typeEnum.getTypeId() == typeId) {
                    return typeEnum;
                }
            }
            return null;
        }

        public static FragmentType getFragmentTypeByClass(Class<? extends BaseFragment> cls) {
            for (FragmentType typeEnum : FragmentType.values()) {
                if (typeEnum.cls == cls) {
                    return typeEnum;
                }
            }
            return null;
        }
    }

}
