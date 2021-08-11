package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CreateProjectHandBean implements Parcelable {

    private String name;

    public CreateProjectHandBean() {

    }

    protected CreateProjectHandBean(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreateProjectHandBean> CREATOR = new Creator<CreateProjectHandBean>() {
        @Override
        public CreateProjectHandBean createFromParcel(Parcel in) {
            return new CreateProjectHandBean(in);
        }

        @Override
        public CreateProjectHandBean[] newArray(int size) {
            return new CreateProjectHandBean[size];
        }
    };


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
