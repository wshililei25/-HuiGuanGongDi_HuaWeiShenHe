package com.huiguangongdi.req;

public class QualityManagerHandleReq {
    private int id;
    private String question_supple;
    private String[] image;

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion_supple(String question_supple) {
        this.question_supple = question_supple;
    }

    public void setImage(String[] image) {
        this.image = image;
    }
}
