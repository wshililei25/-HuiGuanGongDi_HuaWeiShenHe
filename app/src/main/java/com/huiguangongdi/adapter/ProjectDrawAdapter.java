package com.huiguangongdi.adapter;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.ProjectBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.utils.SPUtil;

import org.jetbrains.annotations.NotNull;

public class ProjectDrawAdapter extends BaseQuickAdapter<ProjectBean, BaseViewHolder> {

    public ProjectDrawAdapter() {
        super(R.layout.layout_item_project_draw);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ProjectBean bean) {
        baseViewHolder.setText(R.id.typeTv, bean.getName().substring(0, 1));
        baseViewHolder.setText(R.id.nameTv, bean.getName());
        int id = (int) SPUtil.get(getContext(), Extra.SP_Project_Id, -1);
        if (bean.getId() == id) {
            baseViewHolder.setBackgroundColor(R.id.itemV, ContextCompat.getColor(getContext(), R.color.c_E5F0F8));
            baseViewHolder.setBackgroundResource(R.id.typeTv, R.drawable.shape_oval_0071bc_22);
            baseViewHolder.setTextColor(R.id.nameTv, ContextCompat.getColor(getContext(), R.color.c_0071BC));
        } else {
            baseViewHolder.setBackgroundColor(R.id.itemV, ContextCompat.getColor(getContext(), R.color.white));
            baseViewHolder.setBackgroundResource(R.id.typeTv, R.drawable.shape_oval_8c9aa3_22);
            baseViewHolder.setTextColor(R.id.nameTv, ContextCompat.getColor(getContext(), R.color.c_8C9AA3));
        }
    }

}
