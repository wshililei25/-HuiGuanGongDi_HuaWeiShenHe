package com.huiguangongdi.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.ArrayList;

public class ProvinceBean implements IPickerViewData {
    private int code;
    private String name;
    private ArrayList<CityBean> city;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public ArrayList<CityBean> getCity() {
        return city;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.name;
    }
}
