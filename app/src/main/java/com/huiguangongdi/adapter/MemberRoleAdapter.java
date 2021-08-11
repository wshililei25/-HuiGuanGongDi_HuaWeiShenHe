package com.huiguangongdi.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.MemberRoleBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MemberRoleAdapter extends BaseQuickAdapter<MemberRoleBean, BaseViewHolder> {

    public MemberRoleAdapter(List<MemberRoleBean> list) {
        super(R.layout.layout_item_member_role, list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MemberRoleBean bean) {
        baseViewHolder.setText(R.id.nameTv, bean.getName());
        baseViewHolder.setText(R.id.contentTv, bean.getJurisdiction());
    }

}
