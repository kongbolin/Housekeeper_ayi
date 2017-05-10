package com.edu.xhu.housekeeper.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.activity.HouseKeeperServiceActivity;
import com.edu.xhu.housekeeper.entity.Order;
import com.edu.xhu.housekeeper.entity.User;
import com.edu.xhu.housekeeper.utils.Utils;

import java.util.List;
import java.util.logging.LogRecord;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by skysoft on 2017/3/29.
 */
public class OrderListAdapter extends BaseAdapter {
    List<Order> orderList = null;
    Context mContext;
    LayoutInflater inflater;
    private HouseKeeperServiceActivity activity;
    Handler handler= new Handler(){
        public void handleMessage(Message m){
                   int b = m.what;
                    Log.d("Debug","position1111111:"+b);
                    activity.refreshList(b);
        }
    };
    public OrderListAdapter(Context context, List<Order> orderList, HouseKeeperServiceActivity activity) {
        this.orderList = orderList;
        mContext = context;
        this.activity = activity;
    }
    public void setOrderList( List<Order> orderList){
        this.orderList = orderList;
    }
    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            inflater = LayoutInflater.from(mContext);
            // 按当前所需的样式，确定new的布局
            convertView = inflater.inflate(R.layout.order_item, parent, false);
            holder = new ViewHolder();
            holder.circleImage = (ImageView) convertView.findViewById(R.id.order_circle);
            holder.categoryText = (TextView)convertView.findViewById(R.id.category);
            holder.addressText = (TextView)convertView.findViewById(R.id.address);
            holder.teleponeIcon = (ImageView) convertView.findViewById(R.id.itemicon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
            holder.categoryText.setText(orderList.get(position).getTid().trim());
            holder.addressText.setText(orderList.get(position).getAddrId().trim());
            holder.teleponeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Debug","pzosition:"+position);
//                    activity.refreshDeleteItem(position);
                    BmobSMS.requestSMSCode(mContext, orderList.get(position).getPhone(), "抢单成功", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer smsId, cn.bmob.sms.exception.BmobException e) {
                            if (e == null) {//验证码发送成功
                                updateService(orderList.get(position));
                                Log.i("ayi", "短信id：" + smsId);//用于查询本次短信发送详情
                            }
                        }
                    });
                    orderList.remove(position);
                    notifyDataSetChanged();
                    handler.sendEmptyMessageDelayed(position,1000);

                }
            });
            switch (orderList.get(position).getTid().trim()) {
                case "真皮沙发保养":
                    holder.circleImage.setImageResource(R.mipmap.home_shafa);
                    break;
                case "地板打蜡":
                    holder.circleImage.setImageResource(R.mipmap.home_dala);
                    break;
                case "擦玻璃":
                    holder.circleImage.setImageResource(R.mipmap.home_boli);
                    break;
                case "日常保洁":
                    holder.circleImage.setImageResource(R.mipmap.home_richang);
                    break;
                case "新居开荒":
                    holder.circleImage.setImageResource(R.mipmap.home_xinju);
                    break;
                case "冰箱清洗":
                    holder.circleImage.setImageResource(R.mipmap.home_bingxiang);
                    break;
            }
        return convertView;
    }

    public class ViewHolder {
        ImageView circleImage;
        TextView categoryText;
        TextView addressText;
        ImageView teleponeIcon;
    }


    public void updateService(Order order){
        order.setState("1");
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ayi",Context.MODE_PRIVATE);
        String ayiId = sharedPreferences.getString("ayi_id","");
        order.setHid(ayiId);
        order.update(order.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(mContext,"抢单成功",Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("ayi", "抢单失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}