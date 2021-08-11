package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsNumBean implements Parcelable {

    private String system_count;
    private String project_count;
    private String need_count;

    public String getSystem_count() {
        return system_count;
    }

    public String getProject_count() {
        return project_count;
    }

    public String getNeed_count() {
        return need_count;
    }

    protected NewsNumBean(Parcel in) {
        system_count = in.readString();
        project_count = in.readString();
        need_count = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(system_count);
        dest.writeString(project_count);
        dest.writeString(need_count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NewsNumBean> CREATOR = new Creator<NewsNumBean>() {
        @Override
        public NewsNumBean createFromParcel(Parcel in) {
            return new NewsNumBean(in);
        }

        @Override
        public NewsNumBean[] newArray(int size) {
            return new NewsNumBean[size];
        }
    };
}
