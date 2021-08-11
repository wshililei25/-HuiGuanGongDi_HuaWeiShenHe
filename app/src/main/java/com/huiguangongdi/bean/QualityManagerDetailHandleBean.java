package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class QualityManagerDetailHandleBean implements Parcelable {

    private int count;
    private ArrayList<ProjectMemberBean> list;

    public int getCount() {
        return count;
    }

    public ArrayList<ProjectMemberBean> getList() {
        return list;
    }

    protected QualityManagerDetailHandleBean(Parcel in) {
        count = in.readInt();
        list = in.createTypedArrayList(ProjectMemberBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeTypedList(list);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QualityManagerDetailHandleBean> CREATOR = new Creator<QualityManagerDetailHandleBean>() {
        @Override
        public QualityManagerDetailHandleBean createFromParcel(Parcel in) {
            return new QualityManagerDetailHandleBean(in);
        }

        @Override
        public QualityManagerDetailHandleBean[] newArray(int size) {
            return new QualityManagerDetailHandleBean[size];
        }
    };
}
