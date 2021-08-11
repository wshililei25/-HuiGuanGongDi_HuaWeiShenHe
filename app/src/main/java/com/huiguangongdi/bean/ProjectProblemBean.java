package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectProblemBean implements Parcelable {
    private int quality;
    private int security;

    public int getQuality() {
        return quality;
    }

    public int getSecurity() {
        return security;
    }

    protected ProjectProblemBean(Parcel in) {
        quality = in.readInt();
        security = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quality);
        dest.writeInt(security);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectProblemBean> CREATOR = new Creator<ProjectProblemBean>() {
        @Override
        public ProjectProblemBean createFromParcel(Parcel in) {
            return new ProjectProblemBean(in);
        }

        @Override
        public ProjectProblemBean[] newArray(int size) {
            return new ProjectProblemBean[size];
        }
    };
}
