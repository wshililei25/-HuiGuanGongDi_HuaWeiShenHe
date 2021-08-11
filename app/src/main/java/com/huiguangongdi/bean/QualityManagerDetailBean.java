package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class QualityManagerDetailBean implements Parcelable {

    private QualityManagerDetailInfoBean info;
    private ArrayList<QualityManagerDetailLogBean> log;
    private QualityManagerDetailHandleBean handle;
    private QualityManagerDetailHandleBean check;

    public QualityManagerDetailInfoBean getInfo() {
        return info;
    }

    public ArrayList<QualityManagerDetailLogBean> getLog() {
        return log;
    }

    public QualityManagerDetailHandleBean getHandle() {
        return handle;
    }

    public QualityManagerDetailHandleBean getCheck() {
        return check;
    }

    protected QualityManagerDetailBean(Parcel in) {
        info = in.readParcelable(QualityManagerDetailInfoBean.class.getClassLoader());
        log = in.createTypedArrayList(QualityManagerDetailLogBean.CREATOR);
        handle = in.readParcelable(QualityManagerDetailHandleBean.class.getClassLoader());
        check = in.readParcelable(QualityManagerDetailHandleBean.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(info, flags);
        dest.writeTypedList(log);
        dest.writeParcelable(handle, flags);
        dest.writeParcelable(check, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QualityManagerDetailBean> CREATOR = new Creator<QualityManagerDetailBean>() {
        @Override
        public QualityManagerDetailBean createFromParcel(Parcel in) {
            return new QualityManagerDetailBean(in);
        }

        @Override
        public QualityManagerDetailBean[] newArray(int size) {
            return new QualityManagerDetailBean[size];
        }
    };
}
