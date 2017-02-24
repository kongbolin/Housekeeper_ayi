package com.edu.xhu.housekeeper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.xhu.housekeeper.R;

/**
 * Created by heyzqt on 2016/9/6.
 * <p>
 * 用户中心Fragment
 */
public class UserCenterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_usercenter, null);
        //   initView(contentView);
        return contentView;
    }

//    private void initView(View convertView) {
//        mUserHeadView = (RoundImageView) convertView.findViewById(R.id.img_user_head);
//        mUsernameTv = (TextView) convertView.findViewById(R.id.tv_user_head);
//        mAddressRy = (RelativeLayout) convertView.findViewById(R.id.ry_address);
//        mOrderRy = (RelativeLayout) convertView.findViewById(R.id.ry_order);
//        mCollectRy = (RelativeLayout) convertView.findViewById(R.id.ry_collect);
//        mUserInfoRy = (RelativeLayout) convertView.findViewById(R.id.ry_userinfo);
//        mUserHeadView.setOnClickListener(this);
//        mAddressRy.setOnClickListener(this);
//        mOrderRy.setOnClickListener(this);
//        mCollectRy.setOnClickListener(this);
//        mUserInfoRy.setOnClickListener(this);
//    }

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

}
