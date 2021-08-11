package com.huiguangongdi.pop;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.RecyclerView;

import com.huiguangongdi.R;

public class QualityStatusPopup extends PopupWindow {

    private LayoutInflater inflater;
    private Activity activity;
    public PopupWindow popupWindow = null;
    protected boolean isScroll = false;
    protected View view = null;
    private int mHeight;
    private RecyclerView mRecyclerView;

    public QualityStatusPopup(int height) {
        mHeight = height;
    }

    public void initPopupWindow(final Activity activity, View view, int layoutID) {
        this.view = view;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        View dialogView = inflater.inflate(layoutID, null);
        createPopupWindow(dialogView);
        dialogView.findViewById(R.id.parentV).setOnClickListener(new ViewClickListener());
        mRecyclerView = dialogView.findViewById(R.id.recyclerView);

        popupWindow = new PopupWindow(dialogView, ViewGroup.LayoutParams.MATCH_PARENT
                , mHeight);
        // 此处必须设置，否则点击事件无效，选择不了
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置边缘点击可消失
        popupWindow.setOutsideTouchable(true);
        // 设置pop出来后，软键盘的属性，避免pop挡住软键盘，以及pop获取焦点后软键盘会自动隐藏问题
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        // 使用该属性时，在滑动pop的时候不会自动弹出软键盘
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
        // 为了设置返回按钮关闭弹层
        popupWindow.setFocusable(true);
        dialogView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    hidden();
                    popupWindow.setFocusable(false);
                    popupWindow.update();
                    return true; // 消费掉该事件
                }
                return false;
            }
        });
        /**
         * PopupWindow消失时事件
         */
        // 手触碰到pop时，获取焦点，以实现点击事件
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // 刚进入pop界面，listveiw滚动标识设为false，pop界面焦点设为true。
                isScroll = false;
                popupWindow.setFocusable(true);
                popupWindow.update();
                return false;
            }
        });
    }

    private void createPopupWindow(View dialogView) {
        initView(dialogView);
        initData();
        setListener();
    }

    private void initView(View dialogView) {

    }

    public void initData() {
       /* coditionAdapter = new CoditionAdapter(activity,gvCoditionList);
        coditionAdapter.setData(valueBeanList);
        gvCoditionList.setAdapter(coditionAdapter);
        btConfirm.setBackgroundColor(activity.getResources().getColor(R.color.c_d82828));*/
    }


    private void setListener() {
      /*  btReset.setOnClickListener(new ViewClickListener());
        btConfirm.setOnClickListener(new ViewClickListener());*/
    }

    /**
     * 返回当前popupWindow是否显示状态
     */
    public boolean hasShowing() {
        return null == popupWindow ? false : popupWindow.isShowing();
    }

    /**
     * 显示PopupWindow界面
     */
    public void show() {
        if (hasShowing()) {
            return;
        }
        if (null != activity && !activity.isFinishing()) {
            if (null == view) {
                view = activity.getWindow().getDecorView();
            }
            popupWindow.showAsDropDown(view, 0, 0);
        }
    }

    /**
     * 隐藏PopupWindow界面
     */
    public void hidden() {
        if (null == popupWindow) {
            return;
        }
        popupWindow.dismiss();
    }

    /**
     * 按钮点击事件
     *
     * @author XiongJie
     * @Description
     * @date 2016年11月6日 下午4:12:27
     */
    class ViewClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                /*case R.id.bt_reset:
                    //重置
                    if(null != valueBeanList && valueBeanList.size() > 0){
                        for(ValueBean valueBean : valueBeanList){
                            valueBean.setIsChoose("1002");
                        }
                        coditionAdapter.setData(valueBeanList);
                    }
                    break;
                case R.id.bt_confirm:
                    //确认
                    Editor editor = priorityPre.edit();
                    editor.putInt("searchType", searchType);
                    editor.putBoolean("isClickConfirm", true);
                    editor.commit();// 提交修改
                    hidden();
                    break;*/
                case R.id.parentV:
                    hidden();
                    break;
            }
        }
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }
}
