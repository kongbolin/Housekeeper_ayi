package com.edu.xhu.housekeeper.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.adapter.ViewPagerAdapter;
import com.edu.xhu.housekeeper.fragment.MainFragment;
import com.edu.xhu.housekeeper.fragment.OrderFragment;
import com.edu.xhu.housekeeper.fragment.UserCenterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skysoft on 2017/2/21.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private ViewPager mViewPager;

    /**
     * 首页
     */
    private RelativeLayout mMainRy;

    private ImageView mMainImg;

    private TextView mMainTv;

    /**
     * 分类
     */
    private RelativeLayout mOrderRy;

    private ImageView mOrderImg;

    private TextView mOrderTv;


    /**
     * 个人中心
     */
    private RelativeLayout mUserCenterRy;

    private ImageView mUserCenterImg;

    private TextView mUserCenterTv;

    /**
     * viewpager的数据源
     */
    private List<Fragment> mfragmentLists = new ArrayList<>();

    /**
     * viewpager的适配器
     */
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        //首页
        mMainRy = (RelativeLayout) findViewById(R.id.rl_main);
        mMainImg = (ImageView) findViewById(R.id.img_main);
        mMainTv = (TextView) findViewById(R.id.tv_main);
        //分类
        mOrderRy = (RelativeLayout) findViewById(R.id.rl_type);
        mOrderImg = (ImageView) findViewById(R.id.img_type);
        mOrderTv = (TextView) findViewById(R.id.tv_type);
        //个人中心
        mUserCenterRy = (RelativeLayout) findViewById(R.id.rl_usercenter);
        mUserCenterImg = (ImageView) findViewById(R.id.img_usercenter);
        mUserCenterTv = (TextView) findViewById(R.id.tv_usercenter);

        mMainRy.setOnClickListener(this);
        mOrderRy.setOnClickListener(this);
        mUserCenterRy.setOnClickListener(this);

        //初始化viewpager
        FragmentManager fragmentManager = getSupportFragmentManager();
        mfragmentLists.add(new MainFragment());
        mfragmentLists.add(new OrderFragment());
        mfragmentLists.add(new UserCenterFragment());
        mViewPagerAdapter = new ViewPagerAdapter(fragmentManager, mfragmentLists);
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    //首页
                    case 0:
                        clearBottomView();
                        mMainImg.setImageResource(R.mipmap.home_fill);
                        mMainTv.setTextColor(getResources().getColor(R.color.colorMain));
                        break;
                    //订单
                    case 1:
                        clearBottomView();
                        mOrderImg.setImageResource(R.mipmap.order_fill);
                        mOrderTv.setTextColor(getResources().getColor(R.color.colorMain));
                        break;
                    //个人中心
                    case 2:
                        clearBottomView();
                        mUserCenterImg.setImageResource(R.mipmap.my_fill);
                        mUserCenterTv.setTextColor(getResources().getColor(R.color.colorMain));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //启动app时，底部导航栏的图标显示
        clearBottomView();
        mMainImg.setImageResource(R.mipmap.home_fill);
        mMainTv.setTextColor(ContextCompat.getColor(this, R.color.colorMain));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //首页
            case R.id.rl_main:
                mViewPager.setCurrentItem(0);
                clearBottomView();
                mMainImg.setImageResource(R.mipmap.home_fill);
                mMainTv.setTextColor(ContextCompat.getColor(this, R.color.colorMain));
                break;
            //订单
            case R.id.rl_type:
                mViewPager.setCurrentItem(1);
                clearBottomView();
                mOrderImg.setImageResource(R.mipmap.order_fill);
                mOrderTv.setTextColor(getResources().getColor(R.color.colorMain));
                break;
            //个人中心
            case R.id.rl_usercenter:
                mViewPager.setCurrentItem(3);
                clearBottomView();
                mUserCenterImg.setImageResource(R.mipmap.my_fill);
                mUserCenterTv.setTextColor(getResources().getColor(R.color.colorMain));
                break;
        }
    }

    /**
     * 清除图片文字效果
     */
    private void clearBottomView() {
        mMainImg.setImageResource(R.mipmap.home);
        mOrderImg.setImageResource(R.mipmap.order);
        mUserCenterImg.setImageResource(R.mipmap.my);
        mMainTv.setTextColor(getResources().getColor(R.color.colorTool));
        mOrderTv.setTextColor(getResources().getColor(R.color.colorTool));
        mUserCenterTv.setTextColor(getResources().getColor(R.color.colorTool));
    }

}
