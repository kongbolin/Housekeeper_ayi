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
    private BmobGeoPoint gpsAddr;//位置信息
    private String password;
    private String IdCard;
    private BmobFile img;//头像文件
    private BmobFile IDimg;//头像文件
    private String age;
    private String location;//籍贯
    private String favor;//意向，类型
    private String joinTime;//加入时间
    private  String stars;//评分
    private  String comment;//自我评价
    private  String learn;//学历
    private  String mingzu;//名族
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFavor() {
        return favor;
    }

    public void setFavor(String favor) {
        this.favor = favor;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLearn() {
        return learn;
    }

    public void setLearn(String learn) {
        this.learn = learn;
    }

    public String getMingzu() {
        return mingzu;
    }

    public void setMingzu(String mingzu) {
        this.mingzu = mingzu;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public BmobGeoPoint getGpsAdd() {
        return gpsAddr;
    }

    public void setGpsAdd(BmobGeoPoint gpsAdd) {
        this.gpsAddr = gpsAdd;
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
