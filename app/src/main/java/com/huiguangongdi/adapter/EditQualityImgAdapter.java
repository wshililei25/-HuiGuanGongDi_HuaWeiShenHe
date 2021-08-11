package com.huiguangongdi.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.utils.GlideUtil;

import org.jetbrains.annotations.NotNull;

public class EditQualityImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public EditQualityImgAdapter() {
        super(R.layout.layout_item_edit_quelity_img);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String bean) {
        if (TextUtils.isEmpty(bean)) {
            baseViewHolder.setImageResource(R.id.headIv, R.mipmap.upload_photos);
        } else {
            GlideUtil.setRoundImage(getContext(), bean, baseViewHolder.getView(R.id.headIv), 18);
        }
    }

}
