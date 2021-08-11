package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class QualityManagerDetailLogBean implements Parcelable {

    private int uid;
    private String desc;
    private String truename;
    private String[] image;
    private String question_supple;
    private String create_time;
    private String avatar;

    public int getUid() {
        return uid;
    }

    public String getDesc() {
        return desc;
    }

    public String getTruename() {
        return truename;
    }

    public String[] getImage() {
        return image;
    }

    public String getQuestion_supple() {
        return question_supple;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getAvatar() {
        return avatar;
    }

    protected QualityManagerDetailLogBean(Parcel in) {
        uid = in.readInt();
        desc = in.readString();
        truename = in.readString();
        image = in.createStringArray();
        question_supple = in.readString();
        create_time = in.readString();
        avatar = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(desc);
        dest.writeString(truename);
        dest.writeStringArray(image);
        dest.writeString(question_supple);
        dest.writeString(create_time);
        dest.writeString(avatar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QualityManagerDetailLogBean> CREATOR = new Creator<QualityManagerDetailLogBean>() {
        @Override
        public QualityManagerDetailLogBean createFromParcel(Parcel in) {
            return new QualityManagerDetailLogBean(in);
        }

        @Override
        public QualityManagerDetailLogBean[] newArray(int size) {
            return new QualityManagerDetailLogBean[size];
        }
    };
}
