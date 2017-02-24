package com.edu.xhu.housekeeper.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by skysoft on 2017/2/20.
 */
public class Housekeeper extends BmobObject {
    private String name;
    private String phone;
    private BmobGeoPoint gpsAdd;//位置信息
    private String password;
    private String IdCard;
    private BmobFile img;//头像文件
    private BmobFile IDimg;//头像文件
    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public BmobGeoPoint getGpsAdd() {
        return gpsAdd;
    }

    public void setGpsAdd(BmobGeoPoint gpsAdd) {
        this.gpsAdd = gpsAdd;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public BmobFile getImg() {
        return img;
    }

    public void setImg(BmobFile img) {
        this.img = img;
    }

    public BmobFile getIDimg() {
        return IDimg;
    }

    public void setIDimg(BmobFile IDimg) {
        this.IDimg = IDimg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
