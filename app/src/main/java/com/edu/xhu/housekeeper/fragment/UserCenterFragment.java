package com.edu.xhu.housekeeper.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.edu.xhu.housekeeper.activity.SettingActivity;
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
        mImgComment= (ImageView) convertView.findViewById(R.id.imgComment);
        mImgOrder= (ImageView) convertView.findViewById(R.id.imgOrder);
        mRlWans = (RelativeLayout) convertView.findViewById(R.id.fg_me_wanshan_rl);
        mRlAddr = (RelativeLayout) convertView.findViewById(R.id.fg_me_addr_rl);
        mRlPsw = (RelativeLayout) convertView.findViewById(R.id.fg_me_psw_rl);
        mRlSet = (RelativeLayout) convertView.findViewById(R.id.fg_me_set_rl);
        mRlExit = (RelativeLayout) convertView.findViewById(R.id.fg_me_exit_rl);

        circleImageView.setOnClickListener(this);
      //  mUsernameTv.setOnClickListener(this);
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
        //用户登录
//        if (!ZccApplication.mUserId.equals("-1")) {
//            //从数据库找出对象
//            try {
//                User user = DBHelper.getInstance(getActivity()).findFirst(Selector.from(User.class).where("id", "=", ZccApplication.mUserId));
//                mUserHeadView.setImageResource(R.mipmap.touxiang);
//                mUsernameTv.setText(user.getName());
//            } catch (DbException e) {
//                e.printStackTrace();
//            }
//        } else {
//            mUserHeadView.setImageResource(R.mipmap.aliuser_place_holder);
//            mUsernameTv.setText("请登录");
//        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Usericon:
                startActivity(new Intent(getActivity(), UserLoginActivity.class));
                break;
            case R.id.tvOrder:
            case R.id.imgOrder:
                //进入我的历史订单列表Activity
                break;
            case R.id.tvComment:
            case R.id.imgComment:
                //进入我的历史评价列表Activity
                break;
            case R.id.fg_me_addr_rl:
                //进去我的地址列表，默认地址设置
                startActivity(new Intent(getActivity(),AddrListActivity.class));
                break;
            case R.id.fg_me_set_rl:
                //进去设置，默认地址设置
                startActivity(new Intent(getActivity(),SettingActivity.class));
                break;
            case R.id.fg_me_wanshan_rl:
                startActivity(new Intent(getActivity(),UserInfoActivity.class));
                //进去我的个人信息详情，默认地址设置
                break;
            case R.id.fg_me_psw_rl:
                //进去修改密码，默认地址设置
                break;
            case R.id.fg_me_exit_rl:
                Toast.makeText(getActivity(),"tuic",Toast.LENGTH_LONG).show();
                //弹出退出登录对话框
                new AlertDialog.Builder(getActivity()).setTitle("退出登录")//设置对话框标题
                        .setMessage("请确认是否要退出登录？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //确定按钮的响应事件
                   //         }
                                //this.finish();
//                                try {
//                                    DBHelper.getInstance(getActivity()).deleteAll(userInfo.class);
//                                    logined = 0;
//                                    SharedPreferences spso = getActivity().getSharedPreferences("news", getActivity().MODE_PRIVATE);
//                                    SharedPreferences.Editor edqo = spso.edit();
//                                    edqo.putString("qq", "未绑定");
//                                    edqo.commit();
//                                } catch (DbException e) {
//                                    e.printStackTrace();
//                                }
//                                user_icon.setImageResource(R.mipmap.user_center_head_icon);
//                                tv_name.setText(getContext().getResources().getString(R.string.userpage_login));
//                                userinfos.clear();
                           }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                     //   logined = 1;
                        // TODO Auto-generated method stub
                        Log.i("alertdialog", " 请保存数据！");
                    }
                }).show();//在按键响应事件中显示此对话框
                break;

        }
    }
}
