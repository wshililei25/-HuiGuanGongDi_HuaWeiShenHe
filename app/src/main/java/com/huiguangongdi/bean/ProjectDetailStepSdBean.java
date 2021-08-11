package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectDetailStepSdBean implements Parcelable {
    private int id;
    private int build_num;
    private int floor_num;
    private int room_num;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getId() {
        return id;
    }

    public int getBuild_num() {
        return build_num;
    }

    public int getFloor_num() {
        return floor_num;
    }

    public int getRoom_num() {
        return room_num;
    }

    protected ProjectDetailStepSdBean(Parcel in) {
        id = in.readInt();
        build_num = in.readInt();
        floor_num = in.readInt();
        room_num = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(build_num);
        dest.writeInt(floor_num);
        dest.writeInt(room_num);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectDetailStepSdBean> CREATOR = new Creator<ProjectDetailStepSdBean>() {
        @Override
        public ProjectDetailStepSdBean createFromParcel(Parcel in) {
            return new ProjectDetailStepSdBean(in);
        }

        @Override
        public ProjectDetailStepSdBean[] newArray(int size) {
            return new ProjectDetailStepSdBean[size];
        }
    };
}
