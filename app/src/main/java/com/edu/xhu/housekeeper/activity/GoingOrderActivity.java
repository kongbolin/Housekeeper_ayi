package com.edu.xhu.housekeeper.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.entity.Order;
import com.edu.xhu.housekeeper.entity.Type;
import com.edu.xhu.housekeeper.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.b.I;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by skysoft on 2017/4/21.
 */
public class GoingOrderActivity extends BaseActivity implements View.OnClickListener {
    private String orderId;
    private String userId;
    private TextView customName;
    private TextView customNum;
    private TextView categoryText;
    private TextView addrText;
    private TextView ordertype;
    private Button button;
    private String telephoneNum;
    private ImageView telephoneIcon;
    private TextView textView;
    private long startTime;
    private SharedPreferences sharedPreferences;
    private long firstComeTime;
    private long totalTime = 0;
    private boolean isStart = false;
    private Timer timer1;
    private TimerTask timerTask;
    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonig_order);
        sharedPreferences = getSharedPreferences("time", Context.MODE_PRIVATE);
        startTime = sharedPreferences.getLong("startTime", 0);
        isStart = sharedPreferences.getBoolean("isStart", false);

        orderId = getIntent().getStringExtra("orderId");
        initView();
        if (isStart) {
            button.setText("停止计时");
        }
        queryOrderInfo();
    }

    public void initView() {
        textView = (TextView) findViewById(R.id.timer);
        customName = (TextView) findViewById(R.id.order_custom_name);
        customNum = (TextView) findViewById(R.id.order_custom_num);
        categoryText = (TextView) findViewById(R.id.order_details_type_tv);
        ordertype = (TextView) findViewById(R.id.order_money);
        addrText = (TextView) findViewById(R.id.order_addr);
        button = (Button) findViewById(R.id.button);
        telephoneIcon = (ImageView) findViewById(R.id.teleponeIcon);
        mIvBack= (ImageView) findViewById(R.id.go_iv_back);
        mIvBack.setOnClickListener(this);
        telephoneIcon.setOnClickListener(this);
        button.setOnClickListener(this);
//        chronometer.setFormat();
    }

    public void queryOrderInfo() {
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.getObject(orderId, new QueryListener<Order>() {
            @Override
            public void done(Order object, BmobException e) {
                if (e == null) {
                    String orderType = object.getTid();
                    if (orderType != null)
                        categoryText.setText(orderType.trim());
                    queryOrderTypeInfo(orderType);
                    String address = object.getAddrId();
                    if (address != null)
                        addrText.setText(address.trim());
                    userId = object.getUid();
                    telephoneNum = object.getPhone();
                    queryUserInfo(userId);
                    customNum.setText(telephoneNum);
                } else {
                    Log.e("ayi", e + "");
                }
            }
        });
    }

    public void queryUserInfo(String uid) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.getObject(uid, new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if (e == null) {
                    String userName = object.getName();
                    customName.setText(userName);
                } else {
                    Log.e("ayi", e + "");
                }
            }
        });
    }

    public void queryOrderTypeInfo(String typeId) {
        BmobQuery<Type> query = new BmobQuery<Type>();
        query.addWhereEqualTo("typeName", typeId);
        query.setLimit(10);
        query.findObjects(new FindListener<Type>() {
            @Override
            public void done(List<Type> object, BmobException e) {
                if (object.size() > 0) {
                    ordertype.setText(object.get(0).getPrice1() + "," + object.get(0).getPrices());
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStart) {
            firstComeTime = System.currentTimeMillis();
            Log.d("Debug", "firstComeTime:" + firstComeTime);
            totalTime = firstComeTime - startTime;
            totalTime = (long) totalTime / 1000;
            Log.d("Debug", "totalTimeL" + totalTime);
            startClick();
        }
    }

    public void startClick() {
        timer1 = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(getStringTime((int) totalTime++));
                    }
                });
            }
        };
        timer1.schedule(timerTask, 0, 1000);
    }

    private String getStringTime(int cnt) {
        int hour = cnt / 3600;
        int min = cnt % 3600 / 60;
        int second = cnt % 60;
        return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, min, second);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                if (button.getText().equals("开始计时")) {
                    Order order = new Order();
                    isStart = true;
                    startTime = System.currentTimeMillis();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String str = formatter.format(curDate);
                    order.setStartTime(str);
                    order.setState("2");
                    order.update(orderId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                    if (isStart == true) {

                    }
                    totalTime = 0;
                    startClick();
                    Log.d("Debug", "startTime:" + startTime);
                    button.setText("停止计时");
                } else {
                    isStart = false;
                    timer1.cancel();
                    button.setText("已停止计时");
                    button.setClickable(false);


                    new AlertDialog.Builder(this).setTitle("停止计时")//设置对话框标题
                            .setMessage("请确认是否完成工作并停止计时？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Order order1 = new Order();
                                    isStart = true;
                                    startTime = System.currentTimeMillis();
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                                    String str = formatter.format(curDate);
                                    order1.setEndTime(str);
                                    order1.setState("3");
                                    order1.update(orderId, new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            isStart = false;
                                            timer1.cancel();
                                            button.setText("已停止计时");
                                            button.setClickable(false);
                                        }
                                    });
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                        }
                    }).show();
                }
                break;
            case R.id.teleponeIcon:
                new AlertDialog.Builder(this).setTitle("拨打电话")//设置对话框标题
                        .setMessage("请确认是否要拨打用户电话？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephoneNum));
                                startActivity(intent);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                    }
                }).show();
                break;
            case R.id.go_iv_back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("time", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("startTime", startTime);
        editor.putBoolean("isStart", isStart);
        editor.commit();
    }
}
