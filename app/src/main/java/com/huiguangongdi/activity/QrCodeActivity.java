package com.huiguangongdi.activity;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitmapUtils;
import com.huiguangongdi.R;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.presenter.QrCodePresenter;
import com.huiguangongdi.utils.SPUtil;
import com.huiguangongdi.view.QrCodeView;
import com.huiguangongdi.widget.TitleBar;

import butterknife.BindView;

/**
 * 项目二维码
 */
public class QrCodeActivity extends BaseActivity<QrCodePresenter> implements QrCodeView {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.qrIv)
    ImageView mQrIv;
    @BindView(R.id.projectNameTv)
    TextView mProjectNameTv;
    @BindView(R.id.projectCodeTv)
    TextView mProjectCodeTv;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected QrCodePresenter getPresenter() {
        return new QrCodePresenter();
    }

    @Override
    protected void initView() {
        String name = (String) SPUtil.get(this, Extra.SP_Project_Name, "");
        int id = (int) SPUtil.get(this, Extra.SP_Project_Id, -1);
        mProjectNameTv.setText(name);
        mProjectCodeTv.setText(String.valueOf(id));

//        mQrIv.setImageBitmap(QrCodeUtil.createQRCodeBitmap(String.valueOf(id), 800, 800, "UTF-8", "H", "1", Color.BLACK, Color.WHITE));
        Bitmap bitmap = null;
        try {
            bitmap = BitmapUtils.create2DCode(String.valueOf(id));//根据内容生成二维码
            mQrIv.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }

}