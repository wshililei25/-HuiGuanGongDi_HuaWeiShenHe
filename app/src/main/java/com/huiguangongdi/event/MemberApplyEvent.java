package com.huiguangongdi.event;

public class MemberApplyEvent {

    private int position;
    private int op;

    public MemberApplyEvent(int position, int op) {
        this.position = position;
        this.op = op;
    }

    public int getPosition() {
        return position;
    }

    public int getOp() {
        return op;
    }
}
