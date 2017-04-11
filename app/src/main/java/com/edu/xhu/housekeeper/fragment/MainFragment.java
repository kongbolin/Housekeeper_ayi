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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.activity.AyiDetailsActivity;
import com.edu.xhu.housekeeper.activity.CityListActivity;
import com.edu.xhu.housekeeper.activity.ServerDetailsActivity;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;



public class MainFragment extends Fragment implements View.OnClickListener {
    private TextView mTvCity;
    private ImageView mIvCity;
    public LocationClient mLocationClient = null;
    public MyLocationListenner myListener = new MyLocationListenner();
    private RollPagerView mRollViewPager;
    private View relativeLayout;
    private View relativeLayout1;
    private RelativeLayout mRlDaosao;
    private RelativeLayout mRlDala;
    private RelativeLayout mRlBx;
    private RelativeLayout mRlXinju;
    private RelativeLayout mRlBoli;
    private RelativeLayout mRlShafa;
    private TestNormalAdapter viewpagerAdapter;

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
        viewpagerAdapter=new TestNormalAdapter();
        mRollViewPager.setAdapter(viewpagerAdapter);
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW, Color.WHITE));
        relativeLayout = v.findViewById(R.id.ayi_1);
        relativeLayout1 = v.findViewById(R.id.ayi_2);
        relativeLayout.setOnClickListener(this);
        relativeLayout1.setOnClickListener(this);
        mRlDaosao = (RelativeLayout) v.findViewById(R.id.home_rl_dasao);
        mRlDaosao.setOnClickListener(this);

        mRlDala = (RelativeLayout) v.findViewById(R.id.home_rl_dala);
        mRlDala.setOnClickListener(this);

        mRlShafa = (RelativeLayout) v.findViewById(R.id.home_rl_shafa);
        mRlShafa.setOnClickListener(this);

        mRlXinju = (RelativeLayout) v.findViewById(R.id.home_rl_xinju);
        mRlXinju.setOnClickListener(this);

        mRlBx = (RelativeLayout) v.findViewById(R.id.home_rl_bx);
        mRlBx.setOnClickListener(this);

        mRlBoli = (RelativeLayout) v.findViewById(R.id.home_rl_boli);
        mRlBoli.setOnClickListener(this);
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
            case R.id.ayi_1:
                startActivity(new Intent(getActivity(), AyiDetailsActivity.class));
                break;
            case R.id.ayi_2:
                startActivity(new Intent(getActivity(), AyiDetailsActivity.class));
                break;
            case R.id.home_rl_dasao:
                startDetails("日常保洁");
                break;
            case R.id.home_rl_boli:
                startDetails("擦玻璃");
                break;
            case R.id.home_rl_dala:
                startDetails("地板打蜡");
                break;
            case R.id.home_rl_bx:
                startDetails("冰箱清洗");
                break;
            case R.id.home_rl_xinju:
                startDetails("新居开荒");
                break;
            case R.id.home_rl_shafa:
                startDetails("真皮沙发保养");
                break;
        }
    }

    private void startDetails(String types) {
        Intent intent1 = new Intent();
        intent1.putExtra("type", types);
        intent1.setClass(getActivity(), ServerDetailsActivity.class);
        startActivity(intent1);
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
        public View getView(ViewGroup container, final int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position){
                        case 0:
                            startDetails("日常保洁");
                            break;
                        case 1:
                            startDetails("地板打蜡");
                            break;
                        case 2:
                            startDetails("新居开荒");
                            break;
                        case 3:
                            startDetails("擦玻璃");
                            break;
                    }

                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }

}


