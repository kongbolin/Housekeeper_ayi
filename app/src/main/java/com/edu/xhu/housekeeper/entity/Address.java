package com.edu.xhu.housekeeper.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by skysoft on 2017/2/20.
 */
public class Address extends BmobObject{
    private String uid;
    private BmobGeoPoint gpsAdd;//位置信息
    private String info;

    public BmobGeoPoint getGpsAdd() {
        return gpsAdd;
    }

    public void setGpsAdd(BmobGeoPoint gpsAdd) {
        this.gpsAdd = gpsAdd;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
