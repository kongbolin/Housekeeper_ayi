package com.edu.xhu.housekeeper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.xhu.housekeeper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skysoft on 2017/2/21.
 */
public class GuideActivity extends BaseActivity {
    private ViewPager viewPager;
    private List<View> mImageViews; // 滑动的图片集合
    private int[] imageResId; // 图片ID

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        imageResId = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3, R.mipmap.guide_4};


        mImageViews = new ArrayList<View>();

        for (int j = 0; j < imageResId.length; j++) {
            LayoutInflater viewInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 0
            View convertView = viewInflater.inflate(R.layout.item_splash, null);
            RelativeLayout layout = (RelativeLayout) convertView
                    .findViewById(R.id.guide_item);
            layout.setBackgroundResource(imageResId[j]);
            if (j == (imageResId.length - 1)) {
                TextView btn = (TextView) convertView.findViewById(R.id.start);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        GoToMainActivity();
                    }
                });
            }
            mImageViews.add(layout);
        }
        // 初始化图片资源
        viewPager = (ViewPager) findViewById(R.id.guide_view);
        viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }


    /**
     * 进入主界面
     */
    void GoToMainActivity() {
        Intent i = new Intent(GuideActivity.this, HouseKeeperServiceActivity.class);
        startActivity(i);
        finish();
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {


        public void onPageSelected(int position) {
         //   currentItem = position;
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    /**
     * 填充ViewPager页面的适配器
     */
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageResId.length;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mImageViews.get(arg1));
            return mImageViews.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GoToMainActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}