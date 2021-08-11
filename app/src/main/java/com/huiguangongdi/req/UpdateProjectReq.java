package com.huiguangongdi.req;

import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.SpecialtyBean;

import java.util.List;

public class UpdateProjectReq {
    private int id;
    private String name;
    private String description;
    private String company;
    private String address;
    private List<CompanyListBean> part_company;
    private List<SpecialtyBean> work_type;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPart_company(List<CompanyListBean> part_company) {
        this.part_company = part_company;
    }

    public void setWork_type(List<SpecialtyBean> work_type) {
        this.work_type = work_type;
    }
}
