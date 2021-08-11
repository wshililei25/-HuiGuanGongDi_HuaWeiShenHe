package com.huiguangongdi.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.ProjectNewsBean;

import org.jetbrains.annotations.NotNull;

public class ProjectNewsAdapter extends BaseQuickAdapter<ProjectNewsBean, BaseViewHolder> {

    private Context mContext;

    public ProjectNewsAdapter(Context context) {
        super(R.layout.layout_item_project_news);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ProjectNewsBean bean) {
        baseViewHolder.setText(R.id.nameTv, bean.getType());
        baseViewHolder.setText(R.id.dateTv, bean.getDate());
        baseViewHolder.setText(R.id.contentTv, bean.getContent());
    }

}
