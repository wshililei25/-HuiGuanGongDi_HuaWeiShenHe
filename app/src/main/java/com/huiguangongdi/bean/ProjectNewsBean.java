package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectNewsBean implements Parcelable {

    private int pid;
    private int create_time;
    private String type;
    private String content;
    private String date;

    public int getPid() {
        return pid;
    }

    public int getCreate_time() {
        return create_time;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    protected ProjectNewsBean(Parcel in) {
        pid = in.readInt();
        create_time = in.readInt();
        type = in.readString();
        content = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pid);
        dest.writeInt(create_time);
        dest.writeString(type);
        dest.writeString(content);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectNewsBean> CREATOR = new Creator<ProjectNewsBean>() {
        @Override
        public ProjectNewsBean createFromParcel(Parcel in) {
            return new ProjectNewsBean(in);
        }

        @Override
        public ProjectNewsBean[] newArray(int size) {
            return new ProjectNewsBean[size];
        }
    };
}
