package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class VerticalAxesSelectBean implements Parcelable {
    private String id;
    private boolean isSelect;

    public void setId(String id) {
        this.id = id;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getId() {
        return id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public VerticalAxesSelectBean() {

    }

    public VerticalAxesSelectBean(String id, boolean isSelect) {
        this.id = id;
        this.isSelect = isSelect;
    }

    protected VerticalAxesSelectBean(Parcel in) {
        id = in.readString();
        isSelect = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VerticalAxesSelectBean> CREATOR = new Creator<VerticalAxesSelectBean>() {
        @Override
        public VerticalAxesSelectBean createFromParcel(Parcel in) {
            return new VerticalAxesSelectBean(in);
        }

        @Override
        public VerticalAxesSelectBean[] newArray(int size) {
            return new VerticalAxesSelectBean[size];
        }
    };
}
