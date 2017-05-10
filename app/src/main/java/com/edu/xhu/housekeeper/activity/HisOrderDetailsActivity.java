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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.entity.Order;
import com.edu.xhu.housekeeper.entity.Type;
import com.edu.xhu.housekeeper.entity.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by skysoft on 2017/4/11.
 */
public class HisOrderDetailsActivity extends BaseActivity implements View.OnClickListener{
    private ImageView back;
    private CircleImageView photoImage;
    private ImageView telephoneImage;
    private TextView nameText;
    private TextView categoryText;
    private TextView orderText;
    private TextView addressText;
    private TextView releaseTimeText;
    private Button grabButton;
    private String orderId;
    private RequestQueue mQueue;
    private String telephoneNum;
    private String userId;
    private String Appid = "2b2a345973e3da8c190b83680dfa5101";
    private boolean grabSuccess = false;
    private Order order;
    private RatingBar ratingBar;
    private TextView mComment;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_his_details);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        initView();
        orderId = getIntent().getStringExtra("orderId");
        order = new Order();
        queryOrderInfo();
    }

    public void initView(){
        back = (ImageView)findViewById(R.id.iv_order_details_back);
        photoImage = (CircleImageView)findViewById(R.id.order_ayi_icon);
        nameText = (TextView)findViewById(R.id.order_name_tv);
        categoryText = (TextView)findViewById(R.id.order_details_type_tv);
        orderText = (TextView)findViewById(R.id.order_details_money_tv);
        addressText = (TextView)findViewById(R.id.order_details_addr_tv);
        releaseTimeText = (TextView)findViewById(R.id.order_details_time_tv);
        ratingBar = (RatingBar)findViewById(R.id.order_details_rate) ;
        mComment = (TextView)findViewById(R.id.order_details_comment_tv) ;
        back.setOnClickListener(this);
    }

    public void initData(){

    }
    @Override
    protected void onResume() {
        super.onResume();

    }


    public void queryOrderInfo(){
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.getObject(orderId, new QueryListener<Order>() {
            @Override
            public void done(Order object, BmobException e) {
                if (e == null) {
                    String orderType = object.getTid();
                    if(orderType!=null)
                        categoryText.setText(orderType.trim());
                    String money = object.getMoney();
                    if(money != null){
                        orderText.setText(money);
                    }
                    String endTimeTime = object.getEndTime();
                    if(endTimeTime!=null)
                        releaseTimeText.setText(endTimeTime.trim());
                    String address = object.getAddrId();
                    if(address!=null)
                        addressText.setText(address.trim());
                    userId = object.getUid();
                    queryUserInfo(userId);
                    String comment = object.getComment();
                    if(comment != null)
                        mComment.setText(comment);
                    String ration = object.getRate();
                    ratingBar.setRating(Float.parseFloat(ration));
                } else {
                    Log.e("ayi", e + "");
                }
            }
        });
    }

    public void queryUserInfo(String uid){
        BmobQuery<User> query = new BmobQuery<User>();
        query.getObject(uid, new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if (e == null) {
                    String potoUrl = object.getImg().getUrl();
                    String userName = object.getName();
                    nameText.setText(userName);
                    loadImgByVolley(potoUrl,photoImage);
                } else {
                    Log.e("ayi", e + "");
                }
            }
        });
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
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_order_details_back:
                finish();
                break;
        }
    }
}
