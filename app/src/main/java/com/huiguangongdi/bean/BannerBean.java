package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BannerBean implements Parcelable {

    private int id;
    private String title;
    private int cover;
    private String img;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getCover() {
        return cover;
    }

    public String getImg() {
        return img;
    }

    protected BannerBean(Parcel in) {
        id = in.readInt();
        title = in.readString();
        cover = in.readInt();
        img = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(cover);
        dest.writeString(img);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BannerBean> CREATOR = new Creator<BannerBean>() {
        @Override
        public BannerBean createFromParcel(Parcel in) {
            return new BannerBean(in);
        }

        @Override
        public BannerBean[] newArray(int size) {
            return new BannerBean[size];
        }
    };
}
