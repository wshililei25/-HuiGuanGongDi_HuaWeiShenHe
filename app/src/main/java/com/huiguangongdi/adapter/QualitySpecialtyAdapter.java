package com.huiguangongdi.adapter;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.SpecialtyBean;

import org.jetbrains.annotations.NotNull;

public class QualitySpecialtyAdapter extends BaseQuickAdapter<SpecialtyBean, BaseViewHolder> {

    public QualitySpecialtyAdapter() {
        super(R.layout.layout_item_quality_company);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SpecialtyBean bean) {
        baseViewHolder.setText(R.id.nameTv, bean.getName());
        if (bean.isSelect()) {
            baseViewHolder.setVisible(R.id.iv, true);
            baseViewHolder.setTextColor(R.id.nameTv, ContextCompat.getColor(getContext(), R.color.c_001A2B));
        } else {
            baseViewHolder.setVisible(R.id.iv, false);
            baseViewHolder.setTextColor(R.id.nameTv, ContextCompat.getColor(getContext(), R.color.c_8C9AA3));
        }
    }

}
