package com.huiguangongdi.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.SpecialtyBean;

import org.jetbrains.annotations.NotNull;

public class ProjectSelectSpecialtyAdapter extends BaseQuickAdapter<SpecialtyBean, BaseViewHolder> {

    public ProjectSelectSpecialtyAdapter() {
        super(R.layout.layout_item_project_select_specialty);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SpecialtyBean bean) {
        if (null != baseViewHolder && null != bean) {
            baseViewHolder.setText(R.id.nameTv, bean.getName());
        }
    }

}
