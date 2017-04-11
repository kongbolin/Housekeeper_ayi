package com.edu.xhu.housekeeper.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by skysoft on 2017/2/20.
 */
public class Type extends BmobObject {
    private String typeName;
    private String infos;
    private BmobFile titleImg;
    private BmobFile contImg;
    private String danWei;
    private String price;
    private String price1;
    private String prices;

    public BmobFile getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(BmobFile titleImg) {
        this.titleImg = titleImg;
    }

    public BmobFile getContImg() {
        return contImg;
    }

    public void setContImg(BmobFile contImg) {
        this.contImg = contImg;
    }

    public String getDanWei() {
        return danWei;
    }

    public void setDanWei(String danWei) {
        this.danWei = danWei;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

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
