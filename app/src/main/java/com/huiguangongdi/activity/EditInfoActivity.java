package com.huiguangongdi.activity;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cy.dialog.BaseDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.adapter.SpecialtyAdapter;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.AreaBean;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.CityBean;
import com.huiguangongdi.bean.ProvinceBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.bean.UserBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.presenter.EditInfoPresenter;
import com.huiguangongdi.req.EditInfoReq;
import com.huiguangongdi.req.UserInfoByMobileReq;
import com.huiguangongdi.utils.SPUtil;
import com.huiguangongdi.view.EditInfoView;
import com.huiguangongdi.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EditInfoActivity extends BaseActivity<EditInfoPresenter> implements EditInfoView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.areaV)
    View mAreaV;
    @BindView(R.id.specialtyV)
    View mSpecialtyV;
    @BindView(R.id.btn)
    TextView mBtn;
    @BindView(R.id.areaTv)
    TextView mAreaTv;
    @BindView(R.id.specialtyTv)
    TextView mSpecialtyTv;
    @BindView(R.id.parentV)
    ViewGroup mParentV;
    @BindView(R.id.nameEt)
    EditText mNameEt;
    @BindView(R.id.companyEt)
    EditText mCompanyEt;
    @BindView(R.id.jobEt)
    EditText mJobEt;

    private OptionsPickerView mOptionsPickerView;
    private BaseDialog mSpecialtyDialog;

    private ArrayList<SpecialtyBean> mSpecialtyList;
    private List<ProvinceBean> mProvinceList = new ArrayList<>();
    private ArrayList<ArrayList<CityBean>> mCityList = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<AreaBean>>> mAreaList = new ArrayList<>();
    private ArrayList<ArrayList<String>> mCityStrList = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> mAreaStrList = new ArrayList<>();
    private String mSelectSpecialty;
    private int mSelectProvinceCode;
    private int mSelectCityCode;
    private int mSelectAreaCode;
    private int mSelectSpecialtyCode;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_edit_info;
    }

    @Override
    protected EditInfoPresenter getPresenter() {
        return new EditInfoPresenter();
    }

    @Override
    protected void initView() {
        initImmersionBar();
    }

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
        mBtn.setOnClickListener(this::onClick);
        mAreaV.setOnClickListener(this::onClick);
        mSpecialtyV.setOnClickListener(this::onClick);
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleBar)
                .statusBarDarkFont(true).init();
    }

    @Override
    protected void initData() {
        showProgress();
        String mobile = (String) SPUtil.get(this, Extra.SP_User_Mobile, "");
        if (TextUtils.isEmpty(mobile)) return;
        UserInfoByMobileReq req = new UserInfoByMobileReq();
        req.setMobile(mobile);
        mPresenter.getUserIfoByMobile(req);

        mPresenter.getProvince();
    }

    @Override
    public void getUserInfoByMobileSuccess(UserBean bean) {
        mSelectProvinceCode = bean.getProvince();
        mSelectCityCode = bean.getCity();
        mSelectAreaCode = bean.getDistrict();
        mSelectSpecialtyCode = bean.getMajor();
        mNameEt.setText(bean.getTruename());
        mCompanyEt.setText(bean.getCompany());
        mJobEt.setText(bean.getPosition());
        mAreaTv.setText(bean.getArea());
        mSpecialtyTv.setText(bean.getMajor_name());
    }

    @Override
    public void getProvinceSuccess(ArrayList<ProvinceBean> bean) {
        mPresenter.getSpecialty();
        mProvinceList = bean;
        for (int i = 0; i < bean.size(); i++) {//遍历省份
            ArrayList<CityBean> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<AreaBean>> areaList = new ArrayList<>();//该省的所有地区列表（第三极）

            ArrayList<String> cityStrList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> areaStrList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < bean.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                CityBean cityBean = bean.get(i).getCity().get(c);
                cityList.add(cityBean);//添加城市
                ArrayList<AreaBean> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                city_AreaList.addAll(bean.get(i).getCity().get(c).getArea());
                areaList.add(city_AreaList);//添加该省所有地区数据

                cityStrList.add(cityBean.getName());//添加城市
                ArrayList<String> city_AreaStrList = new ArrayList<>();
                for (int d = 0; d < bean.get(i).getCity().get(c).getArea().size(); d++) {
                    city_AreaStrList.add(city_AreaList.get(d).getName());//添加该省所有地区数据
                }
                areaStrList.add(city_AreaStrList);//添加该省所有地区数据
            }
            mCityList.add(cityList);
            mAreaList.add(areaList);

            mCityStrList.add(cityStrList);
            mAreaStrList.add(areaStrList);
        }

    }

    @Override
    public void getSpecialtySuccess(ArrayList<SpecialtyBean> bean) {
        dismissProgress();
        initSpecialtyDialog(bean);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.areaV:
                if (mProvinceList == null || mProvinceList.size() == 0)
                    return;
                showPickerView();
                break;
            case R.id.specialtyV:
                if (mSpecialtyList == null || mSpecialtyList.size() == 0 || mSpecialtyDialog.isShowing())
                    return;
                mSpecialtyDialog.show();
                break;
            case R.id.btn:
                if (mNameEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_name));
                    return;
                }
                if (mCompanyEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_company));
                    return;
                }
                if (mJobEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_job));
                    return;
                }
                if (mAreaTv.getText().toString().isEmpty()) {
                    showToast(getString(R.string.please_select_area));
                    return;
                }
                if (mSpecialtyTv.getText().toString().isEmpty()) {
                    showToast(getString(R.string.please_select_specialty));
                    return;
                }
                showProgress();
                EditInfoReq req = new EditInfoReq();
                req.setTruename(mNameEt.getText().toString().trim());
                req.setCompany(mCompanyEt.getText().toString().trim());
                req.setPosition(mJobEt.getText().toString().trim());
                req.setProvince(mSelectProvinceCode);
                req.setCity(mSelectCityCode);
                req.setDistrict(mSelectAreaCode);
                req.setMajor(mSelectSpecialtyCode);
                mPresenter.editUserInfo(req);
                break;
        }
    }

    @Override
    public void editUserInfoSuccess(BaseBean bean) {
        dismissProgress();
        finish();
    }

    @Override
    public void editUserInfoFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    private void showPickerView() {
        mOptionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = mProvinceList.size() > 0 ?
                        mProvinceList.get(options1).getPickerViewText() : "";
                mSelectProvinceCode = mProvinceList.get(options1).getCode();

                String opt2tx = mCityList.size() > 0
                        && mCityList.get(options1).size() > 0 ?
                        mCityList.get(options1).get(options2).getName() : "";
                mSelectCityCode = mCityList.get(options1).get(options2).getCode();

                String opt3tx = mCityList.size() > 0
                        && mAreaList.get(options1).size() > 0
                        && mAreaList.get(options1).get(options2).size() > 0 ?
                        mAreaList.get(options1).get(options2).get(options3).getName() : "";
                mSelectAreaCode = mAreaList.get(options1).get(options2).get(options3).getCode();

                String tx = opt1tx + opt2tx + opt3tx;
                mAreaTv.setText(tx);
            }
        })
                .setLayoutRes(R.layout.dialog_pickerview_area, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView btn = (TextView) v.findViewById(R.id.btn);
                        ImageView closeIv = (ImageView) v.findViewById(R.id.closeIv);
                        btn.setOnClickListener(v1 -> {
                            mOptionsPickerView.returnData();
                            mOptionsPickerView.dismiss();
                        });
                        closeIv.setOnClickListener(v12 -> mOptionsPickerView.dismiss());
                    }
                })
                .setContentTextSize(15)
                .setLineSpacingMultiplier(3.0f)
                .setItemVisibleCount(5)
                .setDividerColor(getResources().getColor(R.color.c_8C9AA3))
                .setTextColorOut(getResources().getColor(R.color.c_8C9AA3))
                .setTextColorCenter(getResources().getColor(R.color.c_0071BC))
                .setDecorView(mParentV)
                .build();

        mOptionsPickerView.setPicker(mProvinceList, mCityStrList, mAreaStrList);//三级选择器
        mOptionsPickerView.show();
    }

    private void initSpecialtyDialog(ArrayList<SpecialtyBean> list) {
        if (null == list || list.size() == 0) return;
        mSpecialtyList = list;
        mSpecialtyDialog = new BaseDialog(this);
        mSpecialtyDialog.contentView(R.layout.dialog_specialty)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        RecyclerView recyclerView = mSpecialtyDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SpecialtyAdapter adapter = new SpecialtyAdapter(list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mSelectSpecialty = list.get(position).getName();
                mSelectSpecialtyCode = list.get(position).getId();
                for (int i = 0; i < list.size(); i++) {
                    if (position == i) {
                        list.get(i).setSelect(true);
                    } else {
                        list.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        TextView btn = (TextView) mSpecialtyDialog.findViewById(R.id.btn);
        ImageView closeIv = (ImageView) mSpecialtyDialog.findViewById(R.id.closeIv);
        closeIv.setOnClickListener(v -> mSpecialtyDialog.dismiss());
        btn.setOnClickListener(v -> {
            mSpecialtyTv.setText(mSelectSpecialty);
            mSpecialtyDialog.dismiss();
        });
    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }
}