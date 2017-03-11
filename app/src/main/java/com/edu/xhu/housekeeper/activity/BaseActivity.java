package com.edu.xhu.housekeeper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
//update by zyq_03_11
/**
 * Created by skysoft on 2017/2/21.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
}
