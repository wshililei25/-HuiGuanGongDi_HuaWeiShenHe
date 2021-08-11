package com.huiguangongdi.adapter;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.SpecialtyBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpecialtyAdapter extends BaseQuickAdapter<SpecialtyBean, BaseViewHolder> {

    public SpecialtyAdapter(List<SpecialtyBean> list) {
        super(R.layout.layout_item_specialty, list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SpecialtyBean bean) {
        baseViewHolder.setText(R.id.nameTv, bean.getName());
        if (bean.isSelect()) {
            baseViewHolder.setTextColor(R.id.nameTv, ContextCompat.getColor(getContext(), R.color.c_0071BC));
        } else {
            baseViewHolder.setTextColor(R.id.nameTv, ContextCompat.getColor(getContext(), R.color.c_8C9AA3));
        }
    }

}
