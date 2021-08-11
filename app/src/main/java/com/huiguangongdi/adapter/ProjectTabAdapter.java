package com.huiguangongdi.adapter;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.ProjectTabBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProjectTabAdapter extends BaseQuickAdapter<ProjectTabBean, BaseViewHolder> {

    public ProjectTabAdapter(List<ProjectTabBean> list) {
        super(R.layout.layout_item_project_tab, list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ProjectTabBean bean) {
        baseViewHolder.setImageResource(R.id.iv, bean.getImg());
        baseViewHolder.setText(R.id.nameTv, bean.getName());
    }

}
