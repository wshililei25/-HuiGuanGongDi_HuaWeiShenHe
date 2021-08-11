package com.huiguangongdi.req;

public class CreateProjectTwoSdReq {
    private int pid;
    private int type;
    private String[] build_num;
    private String[] floor_num;
    private String[] room_num;

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setBuild_num(String[] build_num) {
        this.build_num = build_num;
    }

    public void setFloor_num(String[] floor_num) {
        this.floor_num = floor_num;
    }

    public void setRoom_num(String[] room_num) {
        this.room_num = room_num;
    }
}
