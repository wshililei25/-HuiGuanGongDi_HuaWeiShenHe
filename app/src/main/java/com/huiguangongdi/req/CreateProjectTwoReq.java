package com.huiguangongdi.req;

public class CreateProjectTwoReq {
    private int pid;
    private int type;
    private int dong;
    private int floor;
    private int lat_axis_start;
    private int lat_axis_end;
    private String lon_axis_start;
    private String lon_axis_end;

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDong(int dong) {
        this.dong = dong;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setLat_axis_start(int lat_axis_start) {
        this.lat_axis_start = lat_axis_start;
    }

    public void setLat_axis_end(int lat_axis_end) {
        this.lat_axis_end = lat_axis_end;
    }

    public void setLon_axis_start(String lon_axis_start) {
        this.lon_axis_start = lon_axis_start;
    }

    public void setLon_axis_end(String lon_axis_end) {
        this.lon_axis_end = lon_axis_end;
    }
}
