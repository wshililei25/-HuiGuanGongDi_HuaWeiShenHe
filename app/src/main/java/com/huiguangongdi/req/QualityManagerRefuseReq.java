package com.huiguangongdi.req;

public class QualityManagerRefuseReq {
    private int id;
    private int status;
    private String question_supple;
    private String[] image;

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setQuestion_supple(String question_supple) {
        this.question_supple = question_supple;
    }

    public void setImage(String[] image) {
        this.image = image;
    }
}
