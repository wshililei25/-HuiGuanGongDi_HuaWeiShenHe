package com.huiguangongdi.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.ProjectMemberBean;
import com.huiguangongdi.utils.GlideUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TurnOverMemberAdapter extends BaseQuickAdapter<ProjectMemberBean, BaseViewHolder> {

    public TurnOverMemberAdapter(List<ProjectMemberBean> list) {
        super(R.layout.layout_item_turn_over_member, list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ProjectMemberBean bean) {
        baseViewHolder.setText(R.id.nameTv, bean.getTruename());
        baseViewHolder.setText(R.id.specialtyTv, bean.getWname());
        if (TextUtils.isEmpty(bean.getTag())) {
            baseViewHolder.setVisible(R.id.tagTv, false);
        } else {
            baseViewHolder.setVisible(R.id.tagTv, true);
            baseViewHolder.setText(R.id.tagTv, bean.getTag());
        }

        if (!TextUtils.isEmpty(bean.getAvatar())) {
            GlideUtil.setCirclePic(getContext(), bean.getAvatar(), baseViewHolder.getView(R.id.headIv));
        }else {
            baseViewHolder.setBackgroundResource(R.id.headIv, R.mipmap.icon_null);
        }
    }

}
