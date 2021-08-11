package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class SpecialtyBean implements Parcelable {
    private int id;
    private String name;
    private boolean isSelect;
    private boolean isSelectFromDetail;
    private boolean isSelectFromDetailNew;

    public SpecialtyBean() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelectFromDetail() {
        return isSelectFromDetail;
    }

    public void setSelectFromDetail(boolean selectFromDetail) {
        isSelectFromDetail = selectFromDetail;
    }

    public boolean isSelectFromDetailNew() {
        return isSelectFromDetailNew;
    }

    public void setSelectFromDetailNew(boolean selectFromDetailNew) {
        isSelectFromDetailNew = selectFromDetailNew;
    }

    protected SpecialtyBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        isSelect = in.readByte() != 0;
        isSelectFromDetail = in.readByte() != 0;
        isSelectFromDetailNew = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeByte((byte) (isSelect ? 1 : 0));
        dest.writeByte((byte) (isSelectFromDetail ? 1 : 0));
        dest.writeByte((byte) (isSelectFromDetailNew ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SpecialtyBean> CREATOR = new Creator<SpecialtyBean>() {
        @Override
        public SpecialtyBean createFromParcel(Parcel in) {
            return new SpecialtyBean(in);
        }

        @Override
        public SpecialtyBean[] newArray(int size) {
            return new SpecialtyBean[size];
        }
    };
}
