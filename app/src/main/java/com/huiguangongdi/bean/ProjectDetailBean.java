package com.huiguangongdi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProjectDetailBean implements Parcelable {
    private int id;
    private int uid;
    private int type;
    private int status;
    private int user_count;
    private int user_apply_count;
    private int role; //成员角色：1超级管理员 2管理员 3执行者 4查看者
    private String role_name;
    private String name;
    private String address;
    private String description;
    private String company;
    private String part_company;
    private String work_type;
    private String create_time;
    private String part_company_name;
    private String work_type_name;
    private String mobile;
    private String create_user_name;
    private ProjectDetailStepBean step2;  //轴线图
    private List<ProjectDetailStepSdBean> manual; //手动创建

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public int getUser_count() {
        return user_count;
    }

    public int getUser_apply_count() {
        return user_apply_count;
    }

    public int getRole() {
        return role;
    }

    public String getRole_name() {
        return role_name;
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

    public String getCreate_time() {
        return create_time;
    }

    public String getPart_company_name() {
        return part_company_name;
    }

    public String getWork_type_name() {
        return work_type_name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCreate_user_name() {
        return create_user_name;
    }

    public ProjectDetailStepBean getStep2() {
        return step2;
    }

    public List<ProjectDetailStepSdBean> getManual() {
        return manual;
    }

    protected ProjectDetailBean(Parcel in) {
        id = in.readInt();
        uid = in.readInt();
        type = in.readInt();
        status = in.readInt();
        user_count = in.readInt();
        user_apply_count = in.readInt();
        role = in.readInt();
        role_name = in.readString();
        name = in.readString();
        address = in.readString();
        description = in.readString();
        company = in.readString();
        part_company = in.readString();
        work_type = in.readString();
        create_time = in.readString();
        part_company_name = in.readString();
        work_type_name = in.readString();
        mobile = in.readString();
        create_user_name = in.readString();
        step2 = in.readParcelable(ProjectDetailStepBean.class.getClassLoader());
        manual = in.createTypedArrayList(ProjectDetailStepSdBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(uid);
        dest.writeInt(type);
        dest.writeInt(status);
        dest.writeInt(user_count);
        dest.writeInt(user_apply_count);
        dest.writeInt(role);
        dest.writeString(role_name);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(description);
        dest.writeString(company);
        dest.writeString(part_company);
        dest.writeString(work_type);
        dest.writeString(create_time);
        dest.writeString(part_company_name);
        dest.writeString(work_type_name);
        dest.writeString(mobile);
        dest.writeString(create_user_name);
        dest.writeParcelable(step2, flags);
        dest.writeTypedList(manual);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectDetailBean> CREATOR = new Creator<ProjectDetailBean>() {
        @Override
        public ProjectDetailBean createFromParcel(Parcel in) {
            return new ProjectDetailBean(in);
        }

        @Override
        public ProjectDetailBean[] newArray(int size) {
            return new ProjectDetailBean[size];
        }
    };
}
