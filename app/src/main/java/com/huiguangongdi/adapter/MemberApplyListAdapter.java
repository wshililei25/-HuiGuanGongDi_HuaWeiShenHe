package com.huiguangongdi.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.MemberApplyListBean;
import com.huiguangongdi.enums.MemberApplyType;
import com.huiguangongdi.event.MemberApplyEvent;
import com.huiguangongdi.utils.GlideUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

public class MemberApplyListAdapter extends BaseQuickAdapter<MemberApplyListBean, BaseViewHolder> {

    private Context mContext;

    public MemberApplyListAdapter(Context context) {
        super(R.layout.layout_item_member_apply_list);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MemberApplyListBean bean) {
        if (!TextUtils.isEmpty(bean.getAvatar())) {
            GlideUtil.setCirclePic(getContext(), bean.getAvatar(), baseViewHolder.getView(R.id.headIv));
        }
        baseViewHolder.setText(R.id.nameTv, bean.getTruename());
        baseViewHolder.setText(R.id.companyTv, bean.getCompany() + getContext().getString(R.string.whippletree) + bean.getWork_type());
        if (TextUtils.isEmpty(bean.getReason())) {
            baseViewHolder.setGone(R.id.reasonTv, true);
        } else {
            baseViewHolder.setGone(R.id.reasonTv, false);
            baseViewHolder.setText(R.id.reasonTv, bean.getReason());
        }

        baseViewHolder.getView(R.id.refuseTv).setOnClickListener(view -> {
            EventBus.getDefault().post(new MemberApplyEvent(baseViewHolder.getAdapterPosition(), MemberApplyType.REFUSE.getValue()));
        });
        baseViewHolder.getView(R.id.agreeTv).setOnClickListener(view -> {
            EventBus.getDefault().post(new MemberApplyEvent(baseViewHolder.getAdapterPosition(), MemberApplyType.AGREE.getValue()));
        });
    }

}
