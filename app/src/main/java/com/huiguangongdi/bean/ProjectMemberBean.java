package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectMemberBean implements Parcelable {

    private int id;
    private int pid;
    private int uid;
    private int role; //成员角色：1超级管理员 2管理员 3执行者 4查看者
    private int cid;
    private String cname;
    private int wid;
    private String wname;
    private String tag;
    private String create_time;
    private String role_name;
    private String truename;
    private String avatar;
    private String mobile;

    public int getId() {
        return id;
    }

    public int getPid() {
        return pid;
    }

    public int getUid() {
        return uid;
    }

    public int getRole() {
        return role;
    }

    public int getCid() {
        return cid;
    }

    public String getCname() {
        return cname;
    }

    public int getWid() {
        return wid;
    }

    public String getWname() {
        return wname;
    }

    public String getTag() {
        return tag;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getRole_name() {
        return role_name;
    }

    public String getTruename() {
        return truename;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getMobile() {
        return mobile;
    }

    protected ProjectMemberBean(Parcel in) {
        id = in.readInt();
        pid = in.readInt();
        uid = in.readInt();
        role = in.readInt();
        cid = in.readInt();
        cname = in.readString();
        wid = in.readInt();
        wname = in.readString();
        tag = in.readString();
        create_time = in.readString();
        role_name = in.readString();
        truename = in.readString();
        avatar = in.readString();
        mobile = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(pid);
        dest.writeInt(uid);
        dest.writeInt(role);
        dest.writeInt(cid);
        dest.writeString(cname);
        dest.writeInt(wid);
        dest.writeString(wname);
        dest.writeString(tag);
        dest.writeString(create_time);
        dest.writeString(role_name);
        dest.writeString(truename);
        dest.writeString(avatar);
        dest.writeString(mobile);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectMemberBean> CREATOR = new Creator<ProjectMemberBean>() {
        @Override
        public ProjectMemberBean createFromParcel(Parcel in) {
            return new ProjectMemberBean(in);
        }

        @Override
        public ProjectMemberBean[] newArray(int size) {
            return new ProjectMemberBean[size];
        }
    };
}
