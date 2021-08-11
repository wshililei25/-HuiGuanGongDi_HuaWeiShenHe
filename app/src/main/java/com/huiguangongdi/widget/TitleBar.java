package com.huiguangongdi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huiguangongdi.R;

public class TitleBar extends FrameLayout {

    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mRightTv;

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        String title = typedArray.getString(R.styleable.TitleBar_title);
        String rightText = typedArray.getString(R.styleable.TitleBar_rightText);
        boolean isShowLine = typedArray.getBoolean(R.styleable.TitleBar_isShowLine, false);
        boolean isShowBack = typedArray.getBoolean(R.styleable.TitleBar_isShowBack, true);
        typedArray.recycle();

        View view = View.inflate(context, R.layout.layout_titlebar, null);
        mBackIv = view.findViewById(R.id.backIv);
        mTitleTv = view.findViewById(R.id.titleTv);
        mRightTv = view.findViewById(R.id.rightTv);
        View line = view.findViewById(R.id.line);
        mTitleTv.setText(title);
        mRightTv.setText(rightText);
        if (!isShowLine) {
            line.setVisibility(View.GONE);
        }
        if (!isShowBack) {
            mBackIv.setVisibility(View.GONE);
        }
        this.addView(view);
    }

    public ImageView getBackIv() {
        return mBackIv;
    }

    public TextView getTitleTv() {
        return mTitleTv;
    }

    public TextView getRightTv() {
        return mRightTv;
    }
}
