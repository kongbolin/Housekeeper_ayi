package com.edu.xhu.housekeeper.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.activity.AddrListActivity;
import com.edu.xhu.housekeeper.activity.MainActivity;
import com.edu.xhu.housekeeper.activity.SettingActivity;
import com.edu.xhu.housekeeper.activity.UpdatePswActivity;
import com.edu.xhu.housekeeper.activity.UserInfoActivity;
import com.edu.xhu.housekeeper.activity.UserLoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 用户中心Fragment
 */
public class UserCenterFragment extends Fragment implements View.OnClickListener {

    private CircleImageView circleImageView;
    private TextView mUsernameTv;
    private TextView mTvOrder;
    private TextView mTvComment;
    private ImageView mImgOrder;
    private ImageView mImgComment;
    private RelativeLayout mRlWans;
    private RelativeLayout mRlAddr;
    private RelativeLayout mRlPsw;
    private RelativeLayout mRlSet;
    private RelativeLayout mRlExit;
    private String UserName;
    private String UserID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_usercenter, null);
        initView(contentView);
        return contentView;
    }

    private void initView(View convertView) {
        circleImageView = (CircleImageView) convertView.findViewById(R.id.Usericon);
        mUsernameTv = (TextView) convertView.findViewById(R.id.usercenter_name);
        mTvComment = (TextView) convertView.findViewById(R.id.tvComment);
        mTvOrder = (TextView) convertView.findViewById(R.id.tvOrder);
        mImgComment = (ImageView) convertView.findViewById(R.id.imgComment);
        mImgOrder = (ImageView) convertView.findViewById(R.id.imgOrder);
        mRlWans = (RelativeLayout) convertView.findViewById(R.id.fg_me_wanshan_rl);
        mRlAddr = (RelativeLayout) convertView.findViewById(R.id.fg_me_addr_rl);
        mRlPsw = (RelativeLayout) convertView.findViewById(R.id.fg_me_psw_rl);
        mRlSet = (RelativeLayout) convertView.findViewById(R.id.fg_me_set_rl);
        mRlExit = (RelativeLayout) convertView.findViewById(R.id.fg_me_exit_rl);

        circleImageView.setOnClickListener(this);
        mTvComment.setOnClickListener(this);
        mTvOrder.setOnClickListener(this);
        mImgComment.setOnClickListener(this);
        mImgOrder.setOnClickListener(this);
        mRlWans.setOnClickListener(this);
        mRlAddr.setOnClickListener(this);
        mRlPsw.setOnClickListener(this);
        mRlSet.setOnClickListener(this);
        mRlExit.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        SharedPreferences sp = getActivity().getSharedPreferences("ayi", Context.MODE_PRIVATE);
        UserName = sp.getString("user_name", "未登录");
        mUsernameTv.setText(UserName);
        UserID = sp.getString("user_id", "0");
        if (UserID.equals("0")) {
            mUsernameTv.setText("未登录");
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Usericon:
                if (UserID.equals("0")) {
                    showToast();
                }
                break;
            case R.id.tvOrder:
            case R.id.imgOrder:
                if (UserID.equals("0")) {
                    showToast();
                }
                //进入我的历史订单列表Activity
                break;
            case R.id.tvComment:
            case R.id.imgComment:
                if (UserID.equals("0")) {
                    showToast();
                }
                //进入我的历史评价列表Activity
                break;
            case R.id.fg_me_addr_rl:
                if (UserID.equals("0")) {
                    showToast();
                } else {
                    //进去我的地址列表，默认地址设置
                    startActivity(new Intent(getActivity(), AddrListActivity.class));
                }
                break;
            case R.id.fg_me_set_rl:
                //进去设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.fg_me_wanshan_rl:
                //进去我的个人信息详情
                if (UserID.equals("0")) {
                    showToast();
                } else {
                    startActivity(new Intent(getActivity(), UserInfoActivity.class));
                }
                break;
            case R.id.fg_me_psw_rl:
                //进去修改密码
                if (UserID.equals("0")) {
                    showToast();
                } else {
                    startActivity(new Intent(getActivity(), UpdatePswActivity.class));
                }
                break;
            case R.id.fg_me_exit_rl:
                if (UserID.equals("0")) {
                    showToast();
                } else {
                    //弹出退出登录对话框
                    new AlertDialog.Builder(getActivity()).setTitle("退出登录")//设置对话框标题
                            .setMessage("请确认是否要退出登录？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("ayi", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                                    editor.putString("user_id", "0");
                                    editor.putString("user_mobile", "0");
                                    UserID = "0";
                                    editor.commit();
                                    mUsernameTv.setText("未登录");
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                        }
                    }).show();
                }
                break;
        }
    }

    private void showToast() {
        Toast.makeText(getActivity(), "请先登录...", Toast.LENGTH_SHORT).show();
        getActivity().startActivity(new Intent(getActivity(), UserLoginActivity.class));
    }
}
