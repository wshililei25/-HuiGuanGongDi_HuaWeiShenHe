package com.huiguangongdi.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.CompanyListBean;

import org.jetbrains.annotations.NotNull;

public class CompanySelectAdapter extends BaseQuickAdapter<CompanyListBean, BaseViewHolder> {

    public CompanySelectAdapter() {
        super(R.layout.layout_item_project_select_specialty);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CompanyListBean bean) {
        baseViewHolder.setText(R.id.nameTv, bean.getName());
    }

}
