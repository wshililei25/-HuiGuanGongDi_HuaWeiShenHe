package com.huiguangongdi.adapter;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huiguangongdi.R;
import com.huiguangongdi.bean.QualityManagerDetailLogBean;
import com.huiguangongdi.utils.GlideEngine;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QualityManagerDetailAdapter extends BaseQuickAdapter<QualityManagerDetailLogBean, BaseViewHolder> {
    private Activity mActivity;

    public QualityManagerDetailAdapter(Activity activity) {
        super(R.layout.layout_item_quelity_manager_detail);
        mActivity = activity;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, QualityManagerDetailLogBean bean) {

        baseViewHolder.setText(R.id.nameTv, bean.getDesc());
        baseViewHolder.setText(R.id.dateTv, bean.getCreate_time());
        baseViewHolder.setText(R.id.replenishTv, bean.getQuestion_supple());
        RecyclerView recyclerView = baseViewHolder.getView(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        QualityManagerDetailImgAdapter adapter = new QualityManagerDetailImgAdapter();
        adapter.setNewInstance(Arrays.asList(bean.getImage()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
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
