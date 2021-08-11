package com.huiguangongdi.adapter;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.CompanyListBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TurnOverCompanyAdapter extends BaseQuickAdapter<CompanyListBean, BaseViewHolder> {

    public TurnOverCompanyAdapter(List<CompanyListBean> list) {
        super(R.layout.layout_item_turn_over_company, list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CompanyListBean bean) {
        baseViewHolder.setText(R.id.nameTv, bean.getName());
        if (bean.isSelect()) {
            baseViewHolder.setVisible(R.id.line, true);
            baseViewHolder.setTextColor(R.id.nameTv, ContextCompat.getColor(getContext(), R.color.c_0071BC));
            baseViewHolder.setBackgroundColor(R.id.itemV, ContextCompat.getColor(getContext(), R.color.c_F5FAFD));
        } else {
            baseViewHolder.setVisible(R.id.line, false);
            baseViewHolder.setTextColor(R.id.nameTv, ContextCompat.getColor(getContext(), R.color.c_001A2B));
            baseViewHolder.setBackgroundColor(R.id.itemV, ContextCompat.getColor(getContext(), R.color.white));
        }
    }

}
