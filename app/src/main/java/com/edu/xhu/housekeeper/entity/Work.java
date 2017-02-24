package com.edu.xhu.housekeeper.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by skysoft on 2017/2/21.
 */
public class Work extends BmobObject {
    private String hid;
    private String tid;
    private String rule;
    private String infos;

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
