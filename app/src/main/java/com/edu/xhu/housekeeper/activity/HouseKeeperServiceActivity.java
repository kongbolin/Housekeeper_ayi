package com.edu.xhu.housekeeper.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.adapter.OrderListAdapter;
import com.edu.xhu.housekeeper.entity.MyBmobInstallation;
import com.edu.xhu.housekeeper.entity.Order;
import com.edu.xhu.housekeeper.entity.Person;
import com.edu.xhu.housekeeper.lib.XListView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.LogRecord;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by skysoft on 2017/4/11.
 */
public class HouseKeeperServiceActivity extends BaseActivity implements View.OnClickListener{
    private ImageView personalSettings;
    private SharedPreferences sharedPreferences;
    private boolean isLogin;
    private XListView orderListView;
    private List<Order> orderList;
    private OrderListAdapter orderAdapter;
    private Handler mHandler;
    private int start = 0;
    private List<Order> tempOrder ;
    private List<Order> refreshOrder;
    private boolean firstCome = true;
    private int position = 0;
    private RelativeLayout nowRelative;
    private String ayiId;
    private Handler handler = new Handler() {
        public void handleMessage(Message m){
            switch (m.what){
                case 0:
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(),HouseKeeperLoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 1:
                    initListView();
                    break;
                case 2:
                    orderAdapter = new OrderListAdapter(getApplicationContext(), geneItems(orderList),HouseKeeperServiceActivity.this);
                    orderListView.setAdapter(orderAdapter);
                    break;
                case 4:
                    orderAdapter = new OrderListAdapter(getApplicationContext(), tempOrder,HouseKeeperServiceActivity.this);
                    orderListView.setAdapter(orderAdapter);
                    break;
            }

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ayi);
        SharedPreferences mSharedPreferences = getSharedPreferences("ayi", Context.MODE_PRIVATE);
        ayiId = mSharedPreferences.getString("ayi_id","");
        mHandler = new Handler();
        tempOrder = new ArrayList<Order>();
        initView();
        initData();
    }


    public void initView(){
        nowRelative = (RelativeLayout)findViewById(R.id.rl_order_now) ;
        personalSettings = (ImageView)findViewById(R.id.iv_personal);
        orderListView = (XListView) findViewById(R.id.order_list);
        orderListView.setPullLoadEnable(true);
        nowRelative.setOnClickListener(this);
        personalSettings.setOnClickListener(this);
    }

    public void initData(){
        BmobQuery<Order> query = new BmobQuery<Order>();
        //查询phone叫“比目”的数据
        query.addWhereEqualTo("state", "0");
//        Log.d("Debug","start:"+start);
        query.setSkip(start);
        query.setLimit(10);
        //执行查询方法
        query.order("-createdAt");
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> object, BmobException e) {
                if (e == null) {
                    // toast("查询成功：共"+object.size()+"条数据。");

                    if (object.size() < 1) {
                        Toast.makeText(getApplicationContext(), "没有订单", Toast.LENGTH_LONG).show();
                    } else {
                        orderList = object;
                        if(firstCome) {
                            handler.sendEmptyMessage(1);
                        }else {
                            handler.sendEmptyMessage(2);
                        }
                    }
                } else {

                }
            }
        });
    }


    public void appendOrder(){
        BmobQuery<Order> query = new BmobQuery<Order>();
        List<BmobQuery<Order>> and = new ArrayList<BmobQuery<Order>>();
//大于00：00：00
        BmobQuery<Order> q1 = new BmobQuery<Order>();
        final String startTime = tempOrder.get(0).getCreatedAt();
//        Log.d("Debug",startTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date  = null;
        try {
            date = sdf.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        q1.addWhereGreaterThan("createdAt",new BmobDate(date));
        q1.addWhereEqualTo("state", "0");
        and.add(q1);

        q1.order("createdAt");
        q1.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
//                    Log.d("Debug","list.size:"+list.size());
                    if(list.size()!=0) {
                        list.remove(0);
                        for (int i = 0; i < list.size(); i++) {
                            tempOrder.add(0, list.get(i));
                        }
                        start += list.size();
                        start = tempOrder.size();
                    }
                    handler.sendEmptyMessage(4);
            }
        });
    }
    public void initListView(){
        orderAdapter = new OrderListAdapter(getApplicationContext(), geneItems(orderList),HouseKeeperServiceActivity.this);
        orderListView.setAdapter(orderAdapter);
        orderListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        firstCome = false;
                        appendOrder();
                        onLoad();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                firstCome = false;
                initData();

                onLoad();
            }
        });
        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position;
                Intent intent=new Intent(getApplicationContext(),OrderDetailsActivity.class);
                intent.putExtra("orderId",tempOrder.get(position-1).getObjectId());
                startActivityForResult(intent,0);
            }
        });
    }
    private List<Order> geneItems(List<Order> orderList) {
//        Log.d("Debug","orderList.size:"+orderList.size()+","+"tempOrder.size:"+tempOrder.size());
        for (int i = 0; i<orderList.size(); ++i) {
            tempOrder.add(orderList.get(i));
//            Log.d("Debug",orderList.get(i).getCreatedAt()+"");
        }
        start = tempOrder.size();
//        Log.d("Debug","tempOrder.size:"+tempOrder.size());
        return  tempOrder;
    }


    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("ayi", Context.MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean("isLogin",false);
        if(isLogin == true){

        }else {
            handler.sendEmptyMessageDelayed(0,500);
        }
    }

    @Override

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_personal:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),PersonalSettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_order_now:
                goingOrders();
                break;
        }
    }


    public void goingOrders() {
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereEqualTo("hid",ayiId );
        query.addWhereLessThan("state", "3");
        query.order("-updatedAt");
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> object, BmobException e) {
                if (e == null) {
                    // toast("查询成功：共"+object.size()+"条数据。");
                    if (object.size() > 0) {
                        Intent intent = new Intent(getApplicationContext(), GoingOrderActivity.class);
                        intent.putExtra("orderId", object.get(0).getObjectId());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "没有进行中的订单", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    private void onLoad() {
        orderListView.stopRefresh();
        orderListView.stopLoadMore();
        orderListView.setRefreshTime("刚刚");
    }


    public void refreshList(int  b){
        Log.d("Debug","tempOrder.size():"+tempOrder.size()+",b:"+b);
//        tempOrder.remove(b);
//        start = tempOrder.size();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Debug","onActivityResult");
        switch (requestCode) {
            case 0:
                boolean  grabSuccess = data.getBooleanExtra("grabSuccess",false);
                // 根据上面发送过去的请求吗来区别
                Log.d("Debug","position:"+position+",grabSuccess"+grabSuccess);
              if(grabSuccess){
                  tempOrder.remove(position);
                  orderAdapter.setOrderList(tempOrder);
                  orderAdapter.notifyDataSetChanged();
              }
                break;
            default:
                break;
        }
    }
}
