package com.huiguangongdi.event;

/**
 * 删除创建项目时手动创建的房源号
 */
public class DeleteManualEvent {

    private int position;

    public DeleteManualEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

}
