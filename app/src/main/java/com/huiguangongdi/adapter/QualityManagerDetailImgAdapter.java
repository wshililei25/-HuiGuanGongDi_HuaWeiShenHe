package com.huiguangongdi.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.utils.GlideUtil;

import org.jetbrains.annotations.NotNull;

public class QualityManagerDetailImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public QualityManagerDetailImgAdapter() {
        super(R.layout.layout_item_quelity_manager_detail_img);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String bean) {

        GlideUtil.setImageUrl(getContext(), bean, baseViewHolder.getView(R.id.headIv));
    }

}
