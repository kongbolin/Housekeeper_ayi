package com.edu.xhu.housekeeper;

import android.app.Application;
import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;

/**
 * Created by skysoft on 2017/2/21.
 */
public class HousekeeperApp extends Application {
    private static HousekeeperApp instance;
    private String Appid = "2b2a345973e3da8c190b83680dfa5101";
//    private RequestQueue mQueue;
    public static HousekeeperApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;

        BmobSMS.initialize(this, Appid);
        // 使用推送服务时的初始化操作
        Bmob.initialize(this, Appid);
        // 启动推送服务
//        mQueue = Volley.newRequestQueue(this);
    }
}
