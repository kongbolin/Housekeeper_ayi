package com.edu.xhu.housekeeper.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.edu.xhu.housekeeper.HousekeeperApp;
import com.edu.xhu.housekeeper.R;

/**
 * Created by skysoft on 2017/3/28.
 */
public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mIvBack;
    private RelativeLayout mRlUpdate;
    private RelativeLayout mRlPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_about_back);
        mRlPhone = (RelativeLayout) findViewById(R.id.about_rl_lianxi);
        mRlUpdate = (RelativeLayout) findViewById(R.id.about_rl_update);

        mIvBack.setOnClickListener(this);
        mRlPhone.setOnClickListener(this);
        mRlUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_about_back:
                this.finish();
                break;
            case R.id.about_rl_lianxi:
                new AlertDialog.Builder(this).setTitle("拨打电话")//设置对话框标题
                        .setMessage("请确认是否要拨打客服电话？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:10086"));
                                AboutUsActivity.this.startActivity(intent);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                    }
                }).show();
                break;
            case R.id.about_rl_update:
             //   Toast.makeText(HousekeeperApp.getInstance(),"获取版本中...",Toast.LENGTH_SHORT).show();
                Toast.makeText(HousekeeperApp.getInstance(),"当前已是最新版本",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
