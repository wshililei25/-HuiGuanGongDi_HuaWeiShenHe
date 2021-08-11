package com.huiguangongdi.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.CreateProjectHandBean;
import com.huiguangongdi.event.DeleteManualEvent;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

public class CreateProjectManualCreationAdapter extends BaseQuickAdapter<CreateProjectHandBean, BaseViewHolder> {

    public CreateProjectManualCreationAdapter() {
        super(R.layout.layout_item_create_project_manual);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CreateProjectHandBean bean) {
        baseViewHolder.setText(R.id.nameTv, bean.getName());
        baseViewHolder.getView(R.id.clearIv).setOnClickListener(view ->
                EventBus.getDefault().post(new DeleteManualEvent(baseViewHolder.getAdapterPosition())));
    }

}
