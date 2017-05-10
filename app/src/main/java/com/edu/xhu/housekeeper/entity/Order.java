package com.edu.xhu.housekeeper.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by skysoft on 2017/2/20.
 */
public class Order extends BmobObject {
    private String uid;//用户ID
    private String addrId;//地址ID
    private String hid;//阿姨ID
    private String Tid;//类型ID
    private String hour;//时长
    private String money;
    private String startTime;
    private String endTime;
    private String others;//备注
    private String phone;//备注
    private String Comment;//备注
    private String rate;//备注
    private String  state;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    private String payId;//备注

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddrId() {
        return addrId;
    }

    public void setAddrId(String addrId) {
        this.addrId = addrId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
