package com.huiguangongdi.adapter;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.QualityManagerBean;
import com.huiguangongdi.utils.GlideEngine;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QualityManagerAdapter extends BaseQuickAdapter<QualityManagerBean, BaseViewHolder> {

    private Activity mActivity;

    public QualityManagerAdapter(Activity activity) {
        super(R.layout.layout_item_quelity_manager);
        mActivity = activity;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, QualityManagerBean bean) {

        baseViewHolder.setText(R.id.statusTv, bean.getStatus());
        baseViewHolder.setText(R.id.nameTv, bean.getName());
        baseViewHolder.setText(R.id.contentTv, getContext().getString(R.string.dealerss) + bean.getHandle_people_list()[0]);
        baseViewHolder.setText(R.id.timeTv, getContext().getString(R.string.create_time) + bean.getCreate_time());
        if (bean.getStatus().equals(getContext().getString(R.string.in_hand))) {
            baseViewHolder.setBackgroundColor(R.id.line, ContextCompat.getColor(getContext(), R.color.c_FF673D));
            baseViewHolder.setTextColor(R.id.statusTv, ContextCompat.getColor(getContext(), R.color.c_FF673D));
        } else if (bean.getStatus().equals(getContext().getString(R.string.verification))) {
            baseViewHolder.setBackgroundColor(R.id.line, ContextCompat.getColor(getContext(), R.color.c_FDC200));
            baseViewHolder.setTextColor(R.id.statusTv, ContextCompat.getColor(getContext(), R.color.c_FDC200));
        } else if (bean.getStatus().equals(getContext().getString(R.string.off_stocks))) {
            baseViewHolder.setBackgroundColor(R.id.line, ContextCompat.getColor(getContext(), R.color.c_00D092));
            baseViewHolder.setTextColor(R.id.statusTv, ContextCompat.getColor(getContext(), R.color.c_00D092));
        }
        RecyclerView recyclerView = baseViewHolder.getView(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager1);
        ToDoImgAdapter mToDoImgAdapter = new ToDoImgAdapter();
        mToDoImgAdapter.setNewInstance(Arrays.asList(bean.getImage()).subList(0, 1));
        recyclerView.setAdapter(mToDoImgAdapter);
        mToDoImgAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                List<LocalMedia> medias = new ArrayList<>();
                for (int i = 0; i < bean.getImage().length; i++) {
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setPath(bean.getImage()[i]);
                    medias.add(localMedia);
                }
                PictureSelector.create(mActivity)
                        .themeStyle(R.style.picture_default_style) // xml设置主题
                        .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                        .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                        .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                        .openExternalPreview(position, medias);
            }
        });
    }

}
