package com.edu.xhu.housekeeper.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.edu.xhu.housekeeper.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by skysoft on 2017/4/11.
 */
public class PersonalSettingsActivity extends BaseActivity implements View.OnClickListener{
    private ImageView personalSettings;
    private ImageView mBack;
    private CircleImageView mUsericon;
    private SharedPreferences sharedPreferences;
    private String userIconUrl;
    private RequestQueue mQueue;
    private TextView houseKeeperName;
    private RatingBar ratingBar;
    private TextView tvOrder;
    private RelativeLayout updateInfoLayout;
    private RelativeLayout updatePasswordLayout;
    private RelativeLayout settingsLayout;
    private RelativeLayout suggestLayout;
    private RelativeLayout exitLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        sharedPreferences = getSharedPreferences("ayi",Context.MODE_PRIVATE);
        initView();
        initData();

    }
    public void initData(){
        userIconUrl = sharedPreferences.getString("ayi_img","");
        houseKeeperName.setText(sharedPreferences.getString("ayi_name",""));
        ratingBar.setRating(Float.parseFloat(sharedPreferences.getString("ayi_ratio","1")));
        showUserIcon();
    }
    public void initView(){
        mBack  = (ImageView)findViewById(R.id.back);
        mUsericon = (CircleImageView) findViewById(R.id.Usericon);
        houseKeeperName = (TextView)findViewById(R.id.usercenter_name) ;
        ratingBar = (RatingBar)findViewById(R.id.ayi_rate);
        tvOrder = (TextView)findViewById(R.id.tvOrder);
        updateInfoLayout = (RelativeLayout)findViewById(R.id.fg_me_wanshan_rl) ;
        updatePasswordLayout = (RelativeLayout)findViewById(R.id.fg_me_psw_rl) ;
        settingsLayout = (RelativeLayout)findViewById(R.id.fg_me_set_rl) ;
        suggestLayout = (RelativeLayout)findViewById(R.id.fg_me_addr_rl) ;
        exitLayout = (RelativeLayout)findViewById(R.id.fg_me_exit_rl) ;
        mBack.setOnClickListener(this);
        mUsericon.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
        updateInfoLayout.setOnClickListener(this);
        updatePasswordLayout.setOnClickListener(this);
        settingsLayout.setOnClickListener(this);
        suggestLayout.setOnClickListener(this);
        exitLayout.setOnClickListener(this);
    }
    public void loadImgByVolley(String imgUrl, final ImageView imageView) {
        ImageRequest imgRequest = new ImageRequest(imgUrl,
                new Response.Listener<Bitmap>() {
                    /**
                     * 加载成功
                     * @param arg0
                     */
                    @Override
                    public void onResponse(Bitmap arg0) {
                        imageView.setImageBitmap(arg0);
                    }
                }, 300, 200, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    //加载失败
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        imageView.setImageResource(R.mipmap.user_center_head_icon);
                    }
                });
        //将图片加载放入请求队列中去
        mQueue.add(imgRequest);
    }
    private void showUserIcon(){
        loadImgByVolley(userIconUrl,mUsericon);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tvOrder:
                intent.setClass(getApplicationContext(),MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.fg_me_wanshan_rl:
                intent.setClass(getApplicationContext(),AyiInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.fg_me_psw_rl:
                intent.setClass(getApplicationContext(),UpdateAyiPswActivity.class);
                startActivity(intent);
                break;
            case R.id.fg_me_set_rl:
                intent.setClass(getApplicationContext(),SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.fg_me_addr_rl:
                intent.setClass(getApplicationContext(),FeedBacckActivity.class);
                startActivity(intent);
                break;
            case R.id.fg_me_exit_rl:
                new AlertDialog.Builder(PersonalSettingsActivity.this).setTitle("退出登录")//设置对话框标题
                        .setMessage("请确认是否要退出登录？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences mSharedPreferences = getSharedPreferences("ayi", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mSharedPreferences.edit();
                                editor.putString("ayi_id","");
                                editor.putString("ayi_mobile","");
                                editor.putString("ayi_name", "");
                                editor.putString("ayi_img", "");
                                editor.putString("ayi_ratio","0");
                                editor.putBoolean("isLogin",false);
                                editor.commit();
                                finish();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                    }
                }).show();
                break;
        }

    }
}
