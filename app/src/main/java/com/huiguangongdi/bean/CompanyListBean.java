package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CompanyListBean implements Parcelable {
    private int id;
    private String name;
    private boolean isSelect;
    private boolean isSelectFromDetail;
    private boolean isSelectFromDetailNew;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public boolean isSelectFromDetail() {
        return isSelectFromDetail;
    }

    public boolean isSelectFromDetailNew() {
        return isSelectFromDetailNew;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void setSelectFromDetail(boolean selectFromDetail) {
        isSelectFromDetail = selectFromDetail;
    }

    public void setSelectFromDetailNew(boolean selectFromDetailNew) {
        isSelectFromDetailNew = selectFromDetailNew;
    }

    public CompanyListBean() {

    }

    protected CompanyListBean(Parcel in) {
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

    public static final Creator<CompanyListBean> CREATOR = new Creator<CompanyListBean>() {
        @Override
        public CompanyListBean createFromParcel(Parcel in) {
            return new CompanyListBean(in);
        }

        @Override
        public CompanyListBean[] newArray(int size) {
            return new CompanyListBean[size];
        }
    };
}
