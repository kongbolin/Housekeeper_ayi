package com.edu.xhu.housekeeper.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by skysoft on 2017/3/29.
 */
public class Feedback extends BmobObject {
    private String uid;
    private String phone;
    private String content;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
