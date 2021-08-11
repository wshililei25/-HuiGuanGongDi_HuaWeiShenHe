package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BuildingNoAxisBean implements Parcelable {
    private int dong;
    private int floor;
    private int lat_axis_start;
    private int lat_axis_end;
    private String lon_axis_start;
    private String lon_axis_end;
    private List<Integer> floor_list;
    private List<Integer> dong_list;
    private List<Integer> lat_axis;
    private List<String> lon_axis;

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

    public List<Integer> getFloor_list() {
        return floor_list;
    }

    public List<Integer> getDong_list() {
        return dong_list;
    }

    public List<Integer> getLat_axis() {
        return lat_axis;
    }

    public List<String> getLon_axis() {
        return lon_axis;
    }

    protected BuildingNoAxisBean(Parcel in) {
        dong = in.readInt();
        floor = in.readInt();
        lat_axis_start = in.readInt();
        lat_axis_end = in.readInt();
        lon_axis_start = in.readString();
        lon_axis_end = in.readString();
        lon_axis = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dong);
        dest.writeInt(floor);
        dest.writeInt(lat_axis_start);
        dest.writeInt(lat_axis_end);
        dest.writeString(lon_axis_start);
        dest.writeString(lon_axis_end);
        dest.writeStringList(lon_axis);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BuildingNoAxisBean> CREATOR = new Creator<BuildingNoAxisBean>() {
        @Override
        public BuildingNoAxisBean createFromParcel(Parcel in) {
            return new BuildingNoAxisBean(in);
        }

        @Override
        public BuildingNoAxisBean[] newArray(int size) {
            return new BuildingNoAxisBean[size];
        }
    };
}
