package com.edu.xhu.housekeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.entity.Order;

import java.util.List;

/**
 * Created by skysoft on 2017/3/29.
 */
public class HisOrderListAdapter extends BaseAdapter {
    List<Order> orderList = null;
    Context mContext;
    LayoutInflater inflater;

    public HisOrderListAdapter(Context context, List<Order> orderList) {
        this.orderList = orderList;
        mContext = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            inflater = LayoutInflater.from(mContext);
            // 按当前所需的样式，确定new的布局
            convertView = inflater.inflate(R.layout.item_order, parent, false);
            holder = new ViewHolder();
            holder.type = (TextView) convertView.findViewById(R.id.order_type_tv);
            holder.time = (TextView) convertView.findViewById(R.id.order_start_tv);
            holder.address = (TextView) convertView.findViewById(R.id.order_addr_tv);
            holder.money = (TextView) convertView.findViewById(R.id.order_money_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.type.setText(orderList.get(position).getTid());
        holder.time.setText(orderList.get(position).getStartTime());
        holder.address.setText(orderList.get(position).getAddrId());
        holder.money.setText(orderList.get(position).getMoney());
        return convertView;
    }

    public class ViewHolder {
        TextView type;
        TextView address;
        TextView money;
        TextView time;
    }

}