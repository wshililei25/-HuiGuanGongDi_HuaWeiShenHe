package com.huiguangongdi.req;

public class AddMemberReq {
    private String mobile;
    private int pid;
    private int role;
    private int company;
    private int work_type;
    private String tag;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public void setWork_type(int work_type) {
        this.work_type = work_type;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
