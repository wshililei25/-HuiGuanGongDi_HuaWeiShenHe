package com.huiguangongdi.req;

public class UserInfoReq {
    private String mobile;
    private String truename;
    private String company;
    private String position;
    private int province;
    private int city;
    private int district;
    private int major;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public void setMajor(int major) {
        this.major = major;
    }
}
