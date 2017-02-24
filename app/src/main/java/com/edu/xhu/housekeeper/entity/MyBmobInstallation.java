package com.edu.xhu.housekeeper.entity;

import cn.bmob.v3.BmobInstallation;

/**
 * Created by skysoft on 2017/2/9.
 */
public class MyBmobInstallation extends BmobInstallation {

    /**
     * 用户id-这样可以将设备与用户之间进行绑定
     */
    private String uid;

    public MyBmobInstallation(String uid) {
        super();
        setUid(uid);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
