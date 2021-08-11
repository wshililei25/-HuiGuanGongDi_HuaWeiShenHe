package com.huiguangongdi.req;

import android.os.Parcel;
import android.os.Parcelable;

import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.SpecialtyBean;

import java.util.List;

public class CreateProjectReq implements Parcelable {
    private String name;
    private String address;
    private String description;
    private String company;
    private List<CompanyListBean> part_company;
    private List<SpecialtyBean> work_type;

    public List<CompanyListBean> getPart_company() {
        return part_company;
    }

    public CreateProjectReq() {

    }

    public String getName() {
        return name;
    }

    protected CreateProjectReq(Parcel in) {
        name = in.readString();
        address = in.readString();
        description = in.readString();
        company = in.readString();
        part_company = in.createTypedArrayList(CompanyListBean.CREATOR);
        work_type = in.createTypedArrayList(SpecialtyBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(description);
        dest.writeString(company);
        dest.writeTypedList(part_company);
        dest.writeTypedList(work_type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreateProjectReq> CREATOR = new Creator<CreateProjectReq>() {
        @Override
        public CreateProjectReq createFromParcel(Parcel in) {
            return new CreateProjectReq(in);
        }

        @Override
        public CreateProjectReq[] newArray(int size) {
            return new CreateProjectReq[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPart_company(List<CompanyListBean> part_company) {
        this.part_company = part_company;
    }

    public void setWork_type(List<SpecialtyBean> work_type) {
        this.work_type = work_type;
    }
}
