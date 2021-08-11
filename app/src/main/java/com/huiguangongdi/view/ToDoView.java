package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.ToDoBean;

import java.util.ArrayList;

public interface ToDoView extends BaseContract.BaseView {

    void getToDoListSuccess(ArrayList<ToDoBean> list);

    void getToDoListFail(String msg);

    void getToDoSafeListSuccess(ArrayList<ToDoBean> list);

    void getToDoSafeListFail(String msg);

}
