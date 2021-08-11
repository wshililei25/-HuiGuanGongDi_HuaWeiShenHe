package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UploadPhotoBean implements Parcelable {

    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    protected UploadPhotoBean(Parcel in) {
        avatar = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UploadPhotoBean> CREATOR = new Creator<UploadPhotoBean>() {
        @Override
        public UploadPhotoBean createFromParcel(Parcel in) {
            return new UploadPhotoBean(in);
        }

        @Override
        public UploadPhotoBean[] newArray(int size) {
            return new UploadPhotoBean[size];
        }
    };
}
