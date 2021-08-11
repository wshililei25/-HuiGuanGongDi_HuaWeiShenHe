package com.huiguangongdi.req;

public class ExamineAddProjectReq {

    private int id;
    private int op;
    private int role;
    private int company;
    private int work_type;
    private String tag;

    public void setId(int id) {
        this.id = id;
    }

    public void setOp(int op) {
        this.op = op;
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
