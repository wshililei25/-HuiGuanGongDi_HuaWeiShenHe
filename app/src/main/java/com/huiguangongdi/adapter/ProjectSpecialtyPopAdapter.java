package com.huiguangongdi.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.SpecialtyBean;

import org.jetbrains.annotations.NotNull;

public class ProjectSpecialtyPopAdapter extends BaseQuickAdapter<SpecialtyBean, BaseViewHolder> {

    public ProjectSpecialtyPopAdapter() {
        super(R.layout.layout_item_project_specialty_pop);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SpecialtyBean bean) {
        baseViewHolder.setText(R.id.nameTv, bean.getName());
    }

}
