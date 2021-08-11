package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBean implements Parcelable {

    private int id;
    private String mobile;
    private String truename;
    private String company;
    private String position;
    private int province;
    private int major;
    private String major_name;
    private String avatar;
    private int create_time;
    private int edit_time;
    private int city;
    private int district;
    private String area;
    private String address;

    public int getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getTruename() {
        return truename;
    }

    public String getCompany() {
        return company;
    }

    public String getPosition() {
        return position;
    }

    public int getProvince() {
        return province;
    }

    public int getMajor() {
        return major;
    }

    public String getMajor_name() {
        return major_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getCreate_time() {
        return create_time;
    }

    public int getEdit_time() {
        return edit_time;
    }

    public int getCity() {
        return city;
    }

    public int getDistrict() {
        return district;
    }

    public String getArea() {
        return area;
    }

    public String getAddress() {
        return address;
    }

    protected UserBean(Parcel in) {
        id = in.readInt();
        mobile = in.readString();
        truename = in.readString();
        company = in.readString();
        position = in.readString();
        province = in.readInt();
        major = in.readInt();
        major_name = in.readString();
        avatar = in.readString();
        create_time = in.readInt();
        edit_time = in.readInt();
        city = in.readInt();
        district = in.readInt();
        area = in.readString();
        address = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(mobile);
        dest.writeString(truename);
        dest.writeString(company);
        dest.writeString(position);
        dest.writeInt(province);
        dest.writeInt(major);
        dest.writeString(major_name);
        dest.writeString(avatar);
        dest.writeInt(create_time);
        dest.writeInt(edit_time);
        dest.writeInt(city);
        dest.writeInt(district);
        dest.writeString(area);
        dest.writeString(address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
