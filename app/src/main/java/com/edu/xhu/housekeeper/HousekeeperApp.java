package com.edu.xhu.housekeeper;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tencent.tauth.Tencent;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;

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
        Bmob.initialize(this, Appid);
        BmobSMS.initialize(this, Appid);
//        mQueue = Volley.newRequestQueue(this);
    }
}
