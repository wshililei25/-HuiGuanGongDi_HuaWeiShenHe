package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectBean implements Parcelable {
    private int id;
    private String name;
    private String address;
    private String description;
    private String company;
    private String part_company;
    private String work_type;
    private int uid;
    private int create_time;

    protected ProjectBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        description = in.readString();
        company = in.readString();
        part_company = in.readString();
        work_type = in.readString();
        uid = in.readInt();
        create_time = in.readInt();
    }

    public static final Creator<ProjectBean> CREATOR = new Creator<ProjectBean>() {
        @Override
        public ProjectBean createFromParcel(Parcel in) {
            return new ProjectBean(in);
        }

        @Override
        public ProjectBean[] newArray(int size) {
            return new ProjectBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(description);
        parcel.writeString(company);
        parcel.writeString(part_company);
        parcel.writeString(work_type);
        parcel.writeInt(uid);
        parcel.writeInt(create_time);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getCompany() {
        return company;
    }

    public String getPart_company() {
        return part_company;
    }

    public String getWork_type() {
        return work_type;
    }

    public int getUid() {
        return uid;
    }

    public int getCreate_time() {
        return create_time;
    }
}
