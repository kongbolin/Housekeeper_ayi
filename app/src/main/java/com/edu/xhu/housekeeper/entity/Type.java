package com.edu.xhu.housekeeper.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by skysoft on 2017/2/20.
 */
public class Type extends BmobObject {
    private String typeName;
    private String infos;

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
