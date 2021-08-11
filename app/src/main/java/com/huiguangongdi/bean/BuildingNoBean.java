package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BuildingNoBean implements Parcelable {
    private BuildingNoAxisBean axis;

    public BuildingNoAxisBean getAxis() {
        return axis;
    }

    protected BuildingNoBean(Parcel in) {
        axis = in.readParcelable(BuildingNoAxisBean.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(axis, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BuildingNoBean> CREATOR = new Creator<BuildingNoBean>() {
        @Override
        public BuildingNoBean createFromParcel(Parcel in) {
            return new BuildingNoBean(in);
        }

        @Override
        public BuildingNoBean[] newArray(int size) {
            return new BuildingNoBean[size];
        }
    };
}
