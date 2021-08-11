package com.huiguangongdi.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.ProjectMemberBean;
import com.huiguangongdi.utils.GlideUtil;

import org.jetbrains.annotations.NotNull;

public class QualityHandleImgAdapter extends BaseQuickAdapter<ProjectMemberBean, BaseViewHolder> {

    public QualityHandleImgAdapter() {
        super(R.layout.layout_item_quelity_handle_img);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ProjectMemberBean bean) {
        if (TextUtils.isEmpty(bean.getAvatar())) {
            baseViewHolder.setImageResource(R.id.headIv, R.mipmap.icon_null);
        } else {
            GlideUtil.setCirclePic(getContext(), bean.getAvatar(), baseViewHolder.getView(R.id.headIv));
        }
    }

}
