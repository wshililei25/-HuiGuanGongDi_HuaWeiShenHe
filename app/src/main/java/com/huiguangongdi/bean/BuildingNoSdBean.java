package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BuildingNoSdBean implements Parcelable {
    private List<ProjectDetailStepSdBean> manual; //手动创建

    public List<ProjectDetailStepSdBean> getManual() {
        return manual;
    }

    protected BuildingNoSdBean(Parcel in) {
        manual = in.createTypedArrayList(ProjectDetailStepSdBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(manual);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BuildingNoSdBean> CREATOR = new Creator<BuildingNoSdBean>() {
        @Override
        public BuildingNoSdBean createFromParcel(Parcel in) {
            return new BuildingNoSdBean(in);
        }

        @Override
        public BuildingNoSdBean[] newArray(int size) {
            return new BuildingNoSdBean[size];
        }
    };
}
