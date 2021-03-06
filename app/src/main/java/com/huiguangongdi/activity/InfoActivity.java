package com.huiguangongdi.activity;

import android.content.Intent;
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
import com.huiguangongdi.presenter.InfoPresenter;
import com.huiguangongdi.req.UserInfoByMobileReq;
import com.huiguangongdi.req.UserInfoReq;
import com.huiguangongdi.utils.SPUtil;
import com.huiguangongdi.view.InfoView;
import com.huiguangongdi.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class InfoActivity extends BaseActivity<InfoPresenter> implements InfoView, View.OnClickListener {

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
        return R.layout.activity_info;
    }

    @Override
    protected InfoPresenter getPresenter() {
        return new InfoPresenter();
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
        mPresenter.getProvince();
    }

    @Override
    public void getProvinceSuccess(ArrayList<ProvinceBean> bean) {
        mPresenter.getSpecialty();
        mProvinceList = bean;
        for (int i = 0; i < bean.size(); i++) {//????????????
            ArrayList<CityBean> cityList = new ArrayList<>();//????????????????????????????????????
            ArrayList<ArrayList<AreaBean>> areaList = new ArrayList<>();//??????????????????????????????????????????

            ArrayList<String> cityStrList = new ArrayList<>();//????????????????????????????????????
            ArrayList<ArrayList<String>> areaStrList = new ArrayList<>();//??????????????????????????????????????????

            for (int c = 0; c < bean.get(i).getCity().size(); c++) {//??????????????????????????????
                CityBean cityBean = bean.get(i).getCity().get(c);
                cityList.add(cityBean);//????????????
                ArrayList<AreaBean> city_AreaList = new ArrayList<>();//??????????????????????????????
                city_AreaList.addAll(bean.get(i).getCity().get(c).getArea());
                areaList.add(city_AreaList);//??????????????????????????????

                cityStrList.add(cityBean.getName());//????????????
                ArrayList<String> city_AreaStrList = new ArrayList<>();
                for (int d = 0; d < bean.get(i).getCity().get(c).getArea().size(); d++) {
                    city_AreaStrList.add(city_AreaList.get(d).getName());//??????????????????????????????
                }
                areaStrList.add(city_AreaStrList);//??????????????????????????????
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
                UserInfoReq userInfoReq = new UserInfoReq();
                userInfoReq.setMobile(getIntent().getStringExtra(Extra.Mobile));
                userInfoReq.setTruename(mNameEt.getText().toString().trim());
                userInfoReq.setCompany(mCompanyEt.getText().toString().trim());
                userInfoReq.setPosition(mJobEt.getText().toString().trim());
                userInfoReq.setProvince(mSelectProvinceCode);
                userInfoReq.setCity(mSelectCityCode);
                userInfoReq.setDistrict(mSelectAreaCode);
                userInfoReq.setMajor(mSelectSpecialtyCode);
                mPresenter.userInfo(userInfoReq);
                break;
        }
    }

    @Override
    public void userInfoSuccess(BaseBean bean) {
        UserInfoByMobileReq req = new UserInfoByMobileReq();
        req.setMobile(getIntent().getStringExtra(Extra.Mobile));
        mPresenter.getUserIfoByMobile(req);
    }

    @Override
    public void userInfoFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    public void getUserInfoByMobileSuccess(UserBean bean) {
        dismissProgress();
        SPUtil.put(this, Extra.SP_IS_Login, true);
        SPUtil.put(this, Extra.SP_User_Mobile, bean.getMobile());
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showPickerView() {
        mOptionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //?????????????????????????????????????????????
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

        mOptionsPickerView.setPicker(mProvinceList, mCityStrList, mAreaStrList);//???????????????
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