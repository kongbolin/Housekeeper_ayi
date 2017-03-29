package com.edu.xhu.housekeeper.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.xhu.housekeeper.HousekeeperApp;
import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.entity.Housekeeper;
import com.edu.xhu.housekeeper.utils.CleanMessageUtil;

/**
 * Created by skysoft on 2017/3/17.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mIvBack;
    private RelativeLayout mRlHello;
    private RelativeLayout mRlXie;
    private RelativeLayout mRlAbout;
    private RelativeLayout mRlClean;
    private TextView mTvCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initView();

    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_set_back);
        mRlHello = (RelativeLayout) findViewById(R.id.set_rl_hello);
        mRlXie = (RelativeLayout) findViewById(R.id.set_rl_xieyi);
        mRlAbout = (RelativeLayout) findViewById(R.id.set_rl_about);
        mRlClean = (RelativeLayout) findViewById(R.id.set_rl_clean);
        mTvCache = (TextView) findViewById(R.id.set_tv_cache);

        mIvBack.setOnClickListener(this);
        mRlHello.setOnClickListener(this);
        mRlAbout.setOnClickListener(this);
        mRlXie.setOnClickListener(this);
        mRlClean.setOnClickListener(this);
        try {
            mTvCache.setText(CleanMessageUtil.getTotalCacheSize(HousekeeperApp.getInstance()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_set_back:
                this.finish();
                break;
            case R.id.set_rl_about:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.set_rl_hello:
                startActivity(new Intent(this, GuideActivity.class));
                break;
            case R.id.set_rl_clean:
                //清除缓存
                if (mTvCache.getText().toString().trim().equals("0K")) {
                    Toast.makeText(this, "已经非常干净啦！", Toast.LENGTH_LONG).show();
                    return;
                }
                new AlertDialog.Builder(this).setTitle("请了缓存")//设置对话框标题
                        .setMessage("请确认是否要清理应用的缓存数据？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CleanMessageUtil.clearAllCache(HousekeeperApp.getInstance());
                                Toast.makeText(SettingActivity.this, "缓存清理成功！", Toast.LENGTH_LONG).show();
                                try {
                                    mTvCache.setText(CleanMessageUtil.getTotalCacheSize(HousekeeperApp.getInstance()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                break;
            case R.id.set_rl_xieyi:
                startActivity(new Intent(this, XieyiActivity.class));
                break;
        }

    }
}
