package com.edu.xhu.housekeeper.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.adapter.OrderListAdapter;
import com.edu.xhu.housekeeper.entity.Housekeeper;
import com.edu.xhu.housekeeper.entity.Order;
import com.edu.xhu.housekeeper.entity.Type;
import com.edu.xhu.housekeeper.entity.User;

import java.util.List;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by skysoft on 2017/4/11.
 */
public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener{
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        initView();
        orderId = getIntent().getStringExtra("orderId");
        order = new Order();
        queryOrderInfo();
    }

    public void initView(){
        back = (ImageView)findViewById(R.id.iv_order_details_back);
        photoImage = (CircleImageView)findViewById(R.id.order_ayi_icon);
        telephoneImage = (ImageView)findViewById(R.id.teleponeIcon);
        nameText = (TextView)findViewById(R.id.order_name_tv);
        categoryText = (TextView)findViewById(R.id.order_details_type_tv);
        orderText = (TextView)findViewById(R.id.order_details_money_tv);
        addressText = (TextView)findViewById(R.id.order_details_addr_tv);
        releaseTimeText = (TextView)findViewById(R.id.order_details_time_tv);
        grabButton = (Button) findViewById(R.id.button_grab);
        telephoneImage.setOnClickListener(this);
        grabButton.setOnClickListener(this);
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
                    queryOrderTypeInfo(orderType);
                    String releaseTime = object.getCreatedAt();
                    if(releaseTime!=null)
                        releaseTimeText.setText(releaseTime.trim());
                    String address = object.getAddrId();
                    if(address!=null)
                        addressText.setText(address.trim());
                    userId = object.getUid();
                    telephoneNum = object.getPhone();
                    queryUserInfo(userId);
                } else {
                    Log.e("bmob", e + "");
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
                    Log.e("bmob", e + "");
                }
            }
        });
    }

    public void queryOrderTypeInfo(String typeId){
        BmobQuery<Type> query = new BmobQuery<Type>();
        query.addWhereEqualTo("typeName", typeId);
        query.setLimit(10);
        query.findObjects(new FindListener<Type>() {
            @Override
            public void done(List<Type> object, BmobException e) {
                if(object.size()>0) {
                    orderText.setText(object.get(0).getPrice1() + "," + object.get(0).getPrices());
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
                        imageView.setImageResource(R.mipmap.img_share_sina);
                    }
                });
        //将图片加载放入请求队列中去
        mQueue.add(imgRequest);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.teleponeIcon:
                new AlertDialog.Builder(this).setTitle("拨打电话")//设置对话框标题
                        .setMessage("请确认是否要拨打用户电话？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+telephoneNum));
                                startActivity(intent);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                    }
                }).show();
                break;
            case R.id.button_grab:
                Log.d("Debug","onclick");
                updateService();
//                BmobSMS.requestSMSCode(getApplicationContext(), telephoneNum, "抢单成功", new RequestSMSCodeListener() {
//                    @Override
//                    public void done(Integer smsId, cn.bmob.sms.exception.BmobException e) {
//                        if (e == null) {//验证码发送成功
//                            updateService();
//                            Log.i("bmob", "短信id：" + smsId);//用于查询本次短信发送详情
//                        }
//                    }
//                });
                break;
            case R.id.iv_order_details_back:
                setResult(1, new Intent());
                finish();
                break;
        }
    }

    public void updateService(){
        order.setState("1");
        SharedPreferences sharedPreferences = getSharedPreferences("ayi", Context.MODE_PRIVATE);
        String ayiId = sharedPreferences.getString("ayi_id","");
        order.setHid(ayiId);
        order.update(orderId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    grabSuccess = true;
                    Intent mIntent = new Intent();
                    mIntent.putExtra("grabSuccess",grabSuccess);
                    setResult(0, mIntent);
                    finish();
                    Toast.makeText(getApplicationContext(),"抢单成功",Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("bmob", "抢单失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            setResult(1, new Intent());
        }
        return super.onKeyDown(keyCode, event);

    }
}
