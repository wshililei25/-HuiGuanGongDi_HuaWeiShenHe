package com.huiguangongdi.req;

public class CreateQualityReq {
    private int pid;
    private String name;
    private String[] image;
    private String[] address;
    private String begin_time;
    private String end_time;
    private int day;
    private int[] handle_people;
    private int[] check_people;
    private String solution;
    private String question_supple;
    private int work_type;

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String[] image) {
        this.image = image;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHandle_people(int[] handle_people) {
        this.handle_people = handle_people;
    }

    public void setCheck_people(int[] check_people) {
        this.check_people = check_people;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setQuestion_supple(String question_supple) {
        this.question_supple = question_supple;
    }

    public void setWork_type(int work_type) {
        this.work_type = work_type;
    }
}
