package com.edu.xhu.housekeeper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.adapter.HisOrderListAdapter;
import com.edu.xhu.housekeeper.adapter.OrderListAdapter;
import com.edu.xhu.housekeeper.entity.Order;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by skysoft on 2017/4/21.
 */
public class MyOrderActivity extends BaseActivity implements View.OnClickListener{
    private List<Order> orderList;
    private String UserID;
    private HisOrderListAdapter orderAdapter;
    private ListView mLvOrder;
    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hisorder);
        initView();
        initData();
    }

    public void initView(){
        mLvOrder = (ListView)findViewById(R.id.listView);
        mBack = (ImageView)findViewById(R.id.iv_back) ;
        mBack.setOnClickListener(this);
    }

    private void initData() {
        orderList = new ArrayList<Order>();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("ayi", Context.MODE_PRIVATE);
        UserID = sp.getString("ayi_id", "0");
        BmobQuery<Order> query = new BmobQuery<Order>();
        //查询phone叫“比目”的数据
        query.addWhereEqualTo("hid", UserID);
        Log.d("Debug",UserID);
        query.addWhereEqualTo("state","3");
        query.order("-updatedAt");
        query.setLimit(10);
        //执行查询方法
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> object, BmobException e) {
                if (e == null) {
                    // toast("查询成功：共"+object.size()+"条数据。");
                    if (object.size() < 1) {
                        //  Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                    } else {
                        orderList = object;
                        orderAdapter = new HisOrderListAdapter(getApplicationContext(), orderList);
                        mLvOrder.setAdapter(orderAdapter);
                        mLvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent=new Intent(getApplicationContext(),HisOrderDetailsActivity.class);
                                intent.putExtra("orderId",orderList.get(position).getObjectId());
                                startActivity(intent);
                            }
                        });
                        for (Order order : object) {
                            //    String psw1 = order.getPassword();
                        }
                    }
                } else {

                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
