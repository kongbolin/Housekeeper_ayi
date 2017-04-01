package com.edu.xhu.housekeeper.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.activity.CityListActivity;
import com.edu.xhu.housekeeper.adapter.CommonListAdapter;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements View.OnClickListener {
    private TextView mTvCity;
    private ImageView mIvCity;
    public LocationClient mLocationClient = null;
    public MyLocationListenner myListener = new MyLocationListenner();
    private RollPagerView mRollViewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_main, null);
        initView(contentView);
        return contentView;
    }

    private void initView(View v) {
        mTvCity = (TextView) v.findViewById(R.id.tv_home_city);
        mTvCity.setOnClickListener(this);
        mIvCity = (ImageView) v.findViewById(R.id.iv_home_city);
        mIvCity.setOnClickListener(this);
        mLocationClient = new LocationClient(getActivity());
        mLocationClient.registerLocationListener(myListener);
        setLocationOption();
        mLocationClient.start();
        mRollViewPager = (RollPagerView) v.findViewById(R.id.roll_view_pager);
        mRollViewPager.setPlayDelay(3000);
        mRollViewPager.setAnimationDurtion(500);
        mRollViewPager.setAdapter(new TestNormalAdapter());
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW, Color.WHITE));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home_city:
            case R.id.iv_home_city:
                Intent intent = new Intent(getActivity(), CityListActivity.class);
                intent.putExtra("city", mTvCity.getText().toString());
                startActivityForResult(intent, 2);//表示可以返回结果
                break;
        }
    }

    //设置相关参数
//    public int getResource(String imageName) {
//        int resId = getResources().getIdentifier(imageName, "mipmap", getActivity().getPackageName());
//        //如果没有在"mipmap"下找到imageName,将会返回0
//        return resId;
//    }
    class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            // mTvCity.setText(location.getCity());
            final BDLocation location1 = location;
            new Thread() {
                @Override
                public void run() {
                    //...你的业务逻辑；
                    Message message = new Message();//发送一个消息，该消息用于在handleMessage中区分是谁发过来的消息；
                    Bundle bundle = new Bundle();
                    bundle.putString("city", location1.getCity());
                    message.setData(bundle);//bundle传值，耗时，效率低
                    handler.sendMessage(message);//发送message信息
                    message.what = 1;//标志是哪个线程传数据
                }
            }.start();
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    @Override
    public void onDestroy() {
        mLocationClient.stop();
        super.onDestroy();
    }

    //设置相关参数
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); //打开gps
        option.setServiceName("com.baidu.location.service_v2.9");
        //    option.setPoiExtraInfo(true);
        option.setAddrType("all");
        option.setPriority(LocationClientOption.NetWorkFirst);
        option.setPriority(LocationClientOption.GpsFirst);       //gps
        // option.setPoiNumber(10);
        option.disableCache(true);
        mLocationClient.setLocOption(option);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mTvCity.setText(msg.getData().getString("city"));
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (requestCode == 2) {
                String request = data.getStringExtra("city");//接收返回数据
                mTvCity.setText(String.valueOf(request));
            }
        }

    }

    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.mipmap.home_a,
                R.mipmap.home_b,
                R.mipmap.home_c,
                R.mipmap.home_d,
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }

}


