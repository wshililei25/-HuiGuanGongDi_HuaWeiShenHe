package com.huiguangongdi.adapter;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.CompanyListBean;

import org.jetbrains.annotations.NotNull;

public class CompanyAdapter extends BaseQuickAdapter<CompanyListBean, BaseViewHolder> {

    public CompanyAdapter() {
        super(R.layout.layout_item_company);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CompanyListBean bean) {
        baseViewHolder.setText(R.id.nameTv, bean.getName());
        if (bean.isSelectFromDetail() && bean.isSelect()) {
            baseViewHolder.setVisible(R.id.iv, true);
            baseViewHolder.setImageResource(R.id.iv, R.mipmap.icon_selected_grey);
            baseViewHolder.setTextColor(R.id.nameTv, ContextCompat.getColor(getContext(), R.color.c_001A2B));
        } else if (bean.isSelect()) {
            baseViewHolder.setVisible(R.id.iv, true);
            baseViewHolder.setImageResource(R.id.iv, R.mipmap.icon_selected_blue);
            baseViewHolder.setTextColor(R.id.nameTv, ContextCompat.getColor(getContext(), R.color.c_001A2B));
        } else {
            baseViewHolder.setVisible(R.id.iv, false);
            baseViewHolder.setTextColor(R.id.nameTv, ContextCompat.getColor(getContext(), R.color.c_8C9AA3));
        }
    }

}
