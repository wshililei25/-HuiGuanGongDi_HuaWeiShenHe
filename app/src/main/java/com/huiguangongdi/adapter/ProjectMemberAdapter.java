package com.huiguangongdi.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.ProjectMemberBean;
import com.huiguangongdi.event.UpdateMemberEvent;
import com.huiguangongdi.utils.GlideUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

public class ProjectMemberAdapter extends BaseQuickAdapter<ProjectMemberBean, BaseViewHolder> {

    private Context mContext;
    private int mRole;

    public ProjectMemberAdapter(Context context) {
        super(R.layout.layout_item_project_member);
        mContext = context;
    }

    public void setRole(int role) {
        mRole = role;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ProjectMemberBean bean) {
        baseViewHolder.setText(R.id.nameTv, bean.getTruename());
        baseViewHolder.setText(R.id.companyTv, bean.getCname() + getContext().getString(R.string.whippletree) + bean.getWname());
        if (!TextUtils.isEmpty(bean.getAvatar())) {
            GlideUtil.setCirclePic(getContext(), bean.getAvatar(), baseViewHolder.getView(R.id.headIv));
        } else {
            baseViewHolder.setBackgroundResource(R.id.headIv, R.mipmap.icon_null);
        }

        if (bean.getRole() == 1) {
            baseViewHolder.setGone(R.id.iv, true);
        } else {
            if (mRole == 1 || mRole == 2) {
                baseViewHolder.setGone(R.id.iv, false);
            } else {
                baseViewHolder.setGone(R.id.iv, true);
            }
        }

        if (TextUtils.isEmpty(bean.getTag())) {
            baseViewHolder.setGone(R.id.tagTv, true);
        } else {
            baseViewHolder.setGone(R.id.tagTv, false);
            baseViewHolder.setText(R.id.tagTv, bean.getTag());
        }

        //暂时注释 缺显示自己的逻辑

        switch (bean.getRole()) {
            case 1:
                baseViewHolder.setText(R.id.roleTv, mContext.getString(R.string.super_manager));
                break;
            case 2:
                baseViewHolder.setText(R.id.roleTv, mContext.getString(R.string.manager));
                break;
            case 3:
                baseViewHolder.setText(R.id.roleTv, mContext.getString(R.string.executant));
                break;
            case 4:
                baseViewHolder.setText(R.id.roleTv, mContext.getString(R.string.viewer));
                break;
        }
        baseViewHolder.getView(R.id.iv).setOnClickListener(view -> {
            EventBus.getDefault().post(new UpdateMemberEvent(baseViewHolder.getAdapterPosition()));
        });
    }

}
