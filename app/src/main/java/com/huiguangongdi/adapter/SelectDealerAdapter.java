package com.huiguangongdi.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.ProjectMemberBean;
import com.huiguangongdi.event.DeleteDealerEvent;
import com.huiguangongdi.utils.GlideUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

public class SelectDealerAdapter extends BaseQuickAdapter<ProjectMemberBean, BaseViewHolder> {

    public SelectDealerAdapter() {
        super(R.layout.layout_item_select_dealer);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ProjectMemberBean bean) {
        baseViewHolder.setText(R.id.nameTv, bean.getTruename());
        baseViewHolder.setText(R.id.specialtyTv, bean.getCname() + getContext().getString(R.string.whippletree) + bean.getWname());
        if (TextUtils.isEmpty(bean.getTag())) {
            baseViewHolder.setVisible(R.id.tagTv, false);
        } else {
            baseViewHolder.setVisible(R.id.tagTv, true);
            baseViewHolder.setText(R.id.tagTv, bean.getTag());
        }
        GlideUtil.setCirclePic(getContext(), bean.getAvatar(), baseViewHolder.getView(R.id.headIv));
        baseViewHolder.getView(R.id.deleteIv).setOnClickListener(v -> {
            EventBus.getDefault().post(new DeleteDealerEvent(baseViewHolder.getAdapterPosition()));
        });
    }

}
