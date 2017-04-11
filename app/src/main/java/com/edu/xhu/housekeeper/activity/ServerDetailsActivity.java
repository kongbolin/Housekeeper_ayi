package com.edu.xhu.housekeeper.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.entity.Type;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by skysoft on 2017/4/7.
 */
public class ServerDetailsActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtOrder;
    private ImageView mIvBack;
    private TextView mTvTitle;//ser_details_tv_title
    private ImageView mIvTitle;
    private ImageView mIvCont;
    private TextView mTvCont;
    private TextView mTvPric1;
    private TextView mTvPric;
    private LinearLayout mLlPhone;
    private String UserName;
    private String UserId;
    private String typename;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        mQueue = Volley.newRequestQueue(this);
        initView();
    }

    private void initView() {
        mBtOrder = (Button) findViewById(R.id.btn_ser_detail);
        mIvBack = (ImageView) findViewById(R.id.iv_ser_details_back);
        mLlPhone = (LinearLayout) findViewById(R.id.ll_ser_details);
        mTvTitle = (TextView) findViewById(R.id.ser_details_tv_title);
        mTvCont = (TextView) findViewById(R.id.ser_details_tv_cont);
        mIvCont = (ImageView) findViewById(R.id.ser_details_iv_cont);
        mIvTitle = (ImageView) findViewById(R.id.ser_details_iv_title);
        mTvPric = (TextView) findViewById(R.id.ser_details_tv_price);
        mTvPric1 = (TextView) findViewById(R.id.ser_details_tv_price1);
         typename=getIntent().getStringExtra("type");
       // typename = "地板打蜡";
        mTvTitle.setText(typename);
        SharedPreferences sp = getSharedPreferences("ayi", Context.MODE_PRIVATE);
        UserName = sp.getString("user_name", "未登录");
        UserId = sp.getString("user_id", "0");

        BmobQuery<Type> query = new BmobQuery<Type>();
        query.addWhereEqualTo("typeName", typename);
        query.setLimit(1);
        //  BmobQuery<Type> query = new BmobQuery<User>();
        query.findObjects(new FindListener<Type>() {
                              @Override
                              public void done(List<Type> object, BmobException e) {
                                  if (e == null) {
                                      for (Type type : object) {
                                        //  String psw1 = user.getPassword();
                                          mTvCont.setText(type.getInfos());
                                          mTvPric.setText(type.getPrices());
                                          mTvPric1.setText(type.getPrice1());
                                          mTvCont.setText(type.getInfos());
                                          loadImgByVolley(type.getTitleImg().getUrl(), mIvTitle);
                                          loadImgByVolley(type.getContImg().getUrl(), mIvCont);
                                      }
                                  } else {
                                      Toast.makeText(getApplicationContext(), "网络连接失败" + e.getMessage() + "," + e.getErrorCode(), Toast.LENGTH_LONG).show();
                                      Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                  }
                              }
                          }

        );

        mBtOrder.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
        mLlPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ser_detail:
                //进入创建订单界面
                startOrder(typename);
                break;
            case R.id.iv_ser_details_back:
                this.finish();
                break;
            case R.id.ll_ser_details:
                new AlertDialog.Builder(this).setTitle("拨打电话")//设置对话框标题
                        .setMessage("请确认是否要拨打客服电话？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1008611"));
                                ServerDetailsActivity.this.startActivity(intent);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                    }
                }).show();
                break;
        };
    }

    private void startOrder(String typename) {
        Intent intent =new Intent();
        intent.putExtra("type",typename);
        intent.setClass(this,CreatOrderActivity.class);
        startActivity(intent);
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
                }, 900, 600, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    //加载失败
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        imageView.setImageResource(R.mipmap.details_shafa);
                    }
                });
        //将图片加载放入请求队列中去
        mQueue.add(imgRequest);
    }
}
