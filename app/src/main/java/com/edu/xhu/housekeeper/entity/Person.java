package com.edu.xhu.housekeeper.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by skysoft on 2017/2/6.
 */
public class Person extends BmobObject {//demo需要
    private String name;
    private String address;
    private BmobGeoPoint gpsAdd;

    public BmobGeoPoint getGpsAdd() {
        return gpsAdd;
    }

    public void setGpsAdd(BmobGeoPoint gpsAdd) {
        this.gpsAdd = gpsAdd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}