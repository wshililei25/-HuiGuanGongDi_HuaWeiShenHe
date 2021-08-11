package com.huiguangongdi.adapter;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.ProjectDetailStepSdBean;

import org.jetbrains.annotations.NotNull;

public class ShouDongAdapter extends BaseQuickAdapter<ProjectDetailStepSdBean, BaseViewHolder> {

    public ShouDongAdapter() {
        super(R.layout.layout_item_shoudong);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ProjectDetailStepSdBean bean) {
        baseViewHolder.setText(R.id.nameTv, getContext().getString(R.string.building_no)
                + bean.getBuild_num() + " " +
                getContext().getString(R.string.storey) + bean.getFloor_num() + " " +
                getContext().getString(R.string.room_number) + bean.getRoom_num());
        if (bean.isSelect()) {
            baseViewHolder.setVisible(R.id.iv, true);
            baseViewHolder.setTextColor(R.id.nameTv, ContextCompat.getColor(getContext(), R.color.c_001A2B));
        } else {
            baseViewHolder.setVisible(R.id.iv, false);
            baseViewHolder.setTextColor(R.id.nameTv, ContextCompat.getColor(getContext(), R.color.c_8C9AA3));
        }
    }

}
