package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BuildNoSelectBean implements Parcelable {
    private int id;
    private boolean isSelect;

    public void setId(int id) {
        this.id = id;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getId() {
        return id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public BuildNoSelectBean() {

    }

    public BuildNoSelectBean(int id, boolean isSelect) {
        this.id = id;
        this.isSelect = isSelect;
    }

    protected BuildNoSelectBean(Parcel in) {
        id = in.readInt();
        isSelect = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BuildNoSelectBean> CREATOR = new Creator<BuildNoSelectBean>() {
        @Override
        public BuildNoSelectBean createFromParcel(Parcel in) {
            return new BuildNoSelectBean(in);
        }

        @Override
        public BuildNoSelectBean[] newArray(int size) {
            return new BuildNoSelectBean[size];
        }
    };
}
