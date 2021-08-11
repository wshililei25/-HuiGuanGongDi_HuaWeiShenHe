package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MemberApplyListBean implements Parcelable {

    private int id;
    private int uid;
    private String create_time;
    private String truename;
    private String avatar;
    private String reason;
    private String company;
    private String work_type;

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getTruename() {
        return truename;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getReason() {
        return reason;
    }

    public String getCompany() {
        return company;
    }

    public String getWork_type() {
        return work_type;
    }

    protected MemberApplyListBean(Parcel in) {
        id = in.readInt();
        uid = in.readInt();
        create_time = in.readString();
        truename = in.readString();
        avatar = in.readString();
        reason = in.readString();
        company = in.readString();
        work_type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(uid);
        dest.writeString(create_time);
        dest.writeString(truename);
        dest.writeString(avatar);
        dest.writeString(reason);
        dest.writeString(company);
        dest.writeString(work_type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MemberApplyListBean> CREATOR = new Creator<MemberApplyListBean>() {
        @Override
        public MemberApplyListBean createFromParcel(Parcel in) {
            return new MemberApplyListBean(in);
        }

        @Override
        public MemberApplyListBean[] newArray(int size) {
            return new MemberApplyListBean[size];
        }
    };
}
