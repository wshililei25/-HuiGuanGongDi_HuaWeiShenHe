package com.huiguangongdi.event;

public class UpdateMemberEvent {

    private int position;

    public UpdateMemberEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

}
