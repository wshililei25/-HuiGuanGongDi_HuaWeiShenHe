package com.huiguangongdi.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.utils.GlideUtil;

import org.jetbrains.annotations.NotNull;

public class ToDoImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ToDoImgAdapter() {
        super(R.layout.layout_item_todo_img);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String bean) {
        if (TextUtils.isEmpty(bean)) {
            baseViewHolder.setImageResource(R.id.headIv, R.mipmap.icon_null);
        } else {
            GlideUtil.setImageUrl(getContext(), bean, baseViewHolder.getView(R.id.headIv));
        }
    }

}
