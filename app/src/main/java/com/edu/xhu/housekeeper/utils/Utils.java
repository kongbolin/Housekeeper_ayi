package com.edu.xhu.housekeeper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.edu.xhu.housekeeper.HousekeeperApp;
import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.entity.Housekeeper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 张艳琴 on 2017/2/23.
 * 程序相关操作类
 */
public class Utils {

    private static Context mContext;

    private static Utils mUtils = null;

    public static Utils getInstance(Context context) {
        synchronized (Utils.class) {
            if (mContext == null) {
                mUtils = new Utils();
                mContext = context;
                return mUtils;
            }
        }
        return mUtils;
    }

    /**
     * 根据图片url找到图片
     *
     * @param imageName
     * @return
     */
    public static int getImgResource(String imageName) {
        int resId = mContext.getResources().getIdentifier(imageName, "mipmap", mContext.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }

    /**
     * 判断电话号码是否正确
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileNum(String mobile) {
        String regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(14[5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public void loadImgByVolley(String imgUrl, final ImageView imageView) {
        ImageRequest imgRequest = new ImageRequest(imgUrl,
                new Response.Listener<Bitmap>() {
                    /**
                     * 加载成功
                     * @param arg0
                     */
                    @Override
                    public void onResponse(Bitmap arg0) {
                        imageView.setImageBitmap(arg0);
                    }
                }, 300, 200, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    //加载失败
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        imageView.setImageResource(R.mipmap.user_center_head_icon);
                    }
                });
        //将图片加载放入请求队列中去
     //  HousekeeperApp. mQueue.add(imgRequest);
    }
}
