package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class QualityStatusBean implements Parcelable {
    private int status;
    private String name;
    private boolean isSelect;

    public QualityStatusBean(int status, String name, boolean isSelect) {
        this.status = status;
        this.name = name;
        this.isSelect = isSelect;
    }

    protected QualityStatusBean(Parcel in) {
        status = in.readInt();
        name = in.readString();
        isSelect = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(name);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QualityStatusBean> CREATOR = new Creator<QualityStatusBean>() {
        @Override
        public QualityStatusBean createFromParcel(Parcel in) {
            return new QualityStatusBean(in);
        }

        @Override
        public QualityStatusBean[] newArray(int size) {
            return new QualityStatusBean[size];
        }
    };

    public void setStatus(int status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
