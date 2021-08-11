package com.huiguangongdi.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.FindBean;

import org.jetbrains.annotations.NotNull;

public class FindAdapter extends BaseQuickAdapter<FindBean, BaseViewHolder> {

    private Context mContext;

    public FindAdapter(Context context) {
        super(R.layout.layout_item_find);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FindBean bean) {
        baseViewHolder.setImageResource(R.id.headIv, bean.getImage());
        baseViewHolder.setText(R.id.nameTv, bean.getName());

    }

}
