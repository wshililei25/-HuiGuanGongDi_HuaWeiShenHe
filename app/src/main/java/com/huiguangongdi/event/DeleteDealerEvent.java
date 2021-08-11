package com.huiguangongdi.event;

/**
 * 删除选择的处理人
 */
public class DeleteDealerEvent {

    private int position;

    public DeleteDealerEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

}
