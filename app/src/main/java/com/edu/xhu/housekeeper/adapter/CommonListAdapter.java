package com.edu.xhu.housekeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.edu.xhu.housekeeper.R;

import java.util.List;

/**
 * Created by skysoft on 2017/3/29.
 */
public class CommonListAdapter extends BaseAdapter {
    List<String> stringList = null;
    Context mContext;
    LayoutInflater inflater;

    public CommonListAdapter(Context context, List<String> safeList) {
        this.stringList = safeList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
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
            convertView = inflater.inflate(R.layout.item_string, parent, false);
            holder = new ViewHolder();
            holder.city = (TextView) convertView.findViewById(R.id.tv_item_city);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.city.setText(stringList.get(position));
        return convertView;
    }

    public class ViewHolder {
        TextView city;
    }

}