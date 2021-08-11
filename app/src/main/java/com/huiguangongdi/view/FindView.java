package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.BannerBean;
import com.huiguangongdi.bean.UploadPhotoBean;
import com.huiguangongdi.bean.UserBean;

import java.util.ArrayList;

public interface FindView extends BaseContract.BaseView {

    void getBannerSuccess(ArrayList<BannerBean> list);


}
