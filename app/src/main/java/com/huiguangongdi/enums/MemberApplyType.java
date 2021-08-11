package com.huiguangongdi.enums;

public enum MemberApplyType {

    AGREE(1), REFUSE(2); //1同意  2拒绝
    private int value;

    private MemberApplyType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
