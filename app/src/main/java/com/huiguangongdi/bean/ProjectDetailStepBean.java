package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectDetailStepBean implements Parcelable {
    private int dong;
    private int floor;
    private int lat_axis_start;
    private int lat_axis_end;
    private String lon_axis_start;
    private String lon_axis_end;

    public int getDong() {
        return dong;
    }

    public int getFloor() {
        return floor;
    }

    public int getLat_axis_start() {
        return lat_axis_start;
    }

    public int getLat_axis_end() {
        return lat_axis_end;
    }

    public String getLon_axis_start() {
        return lon_axis_start;
    }

    public String getLon_axis_end() {
        return lon_axis_end;
    }

    protected ProjectDetailStepBean(Parcel in) {
        dong = in.readInt();
        floor = in.readInt();
        lat_axis_start = in.readInt();
        lat_axis_end = in.readInt();
        lon_axis_start = in.readString();
        lon_axis_end = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dong);
        dest.writeInt(floor);
        dest.writeInt(lat_axis_start);
        dest.writeInt(lat_axis_end);
        dest.writeString(lon_axis_start);
        dest.writeString(lon_axis_end);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectDetailStepBean> CREATOR = new Creator<ProjectDetailStepBean>() {
        @Override
        public ProjectDetailStepBean createFromParcel(Parcel in) {
            return new ProjectDetailStepBean(in);
        }

        @Override
        public ProjectDetailStepBean[] newArray(int size) {
            return new ProjectDetailStepBean[size];
        }
    };
}
