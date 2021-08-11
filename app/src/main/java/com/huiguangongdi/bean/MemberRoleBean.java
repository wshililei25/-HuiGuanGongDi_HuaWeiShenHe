package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MemberRoleBean implements Parcelable {

    private int id;
    private String name;
    private String jurisdiction;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    protected MemberRoleBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        jurisdiction = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(jurisdiction);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MemberRoleBean> CREATOR = new Creator<MemberRoleBean>() {
        @Override
        public MemberRoleBean createFromParcel(Parcel in) {
            return new MemberRoleBean(in);
        }

        @Override
        public MemberRoleBean[] newArray(int size) {
            return new MemberRoleBean[size];
        }
    };
}
