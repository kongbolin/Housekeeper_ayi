package com.edu.xhu.housekeeper.fragment;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.activity.FeedBacckActivity;
import com.edu.xhu.housekeeper.activity.SettingActivity;
import com.edu.xhu.housekeeper.activity.UpdatePswActivity;
import com.edu.xhu.housekeeper.activity.UserInfoActivity;
import com.edu.xhu.housekeeper.activity.UserLoginActivity;
import com.edu.xhu.housekeeper.entity.User;

import java.io.File;
import java.io.FileNotFoundException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
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
    private String UserImg;
    private String url;
    private RequestQueue mQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_usercenter, null);
        mQueue = Volley.newRequestQueue(getActivity());
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
        SharedPreferences sp = getActivity().getSharedPreferences("ayi", Context.MODE_PRIVATE);
        UserName = sp.getString("user_name", "未登录");
        mUsernameTv.setText(UserName);
        UserID = sp.getString("user_id", "0");
        UserImg = sp.getString("user_img", "0");
        if (UserID.equals("0")) {
            mUsernameTv.setText("未登录");
        }
        if (UserImg.equals("0")) {
            circleImageView.setImageResource(R.mipmap.img_share_sina);
        } else {
            loadImgByVolley(UserImg, circleImageView);
        }
    }

    @Override
    public void onStart() {
        if (UserID.equals("0")) {
            mUsernameTv.setText("未登录");
        } else {
            SharedPreferences sp = getActivity().getSharedPreferences("ayi", Context.MODE_PRIVATE);
            UserName = sp.getString("user_name", "未登录");
        }
        if (UserImg.equals("0")) {
            circleImageView.setImageResource(R.mipmap.img_share_sina);
        }
        super.onStart();
    }

    @Override
    public void onResume() {
        if (UserID.equals("0")) {
            mUsernameTv.setText("未登录");
        } else {
            SharedPreferences sp = getActivity().getSharedPreferences("ayi", Context.MODE_PRIVATE);
            UserName = sp.getString("user_name", "未登录");
        }
        if (UserImg.equals("0")) {
            circleImageView.setImageResource(R.mipmap.img_share_sina);
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Usericon:
                //修改头像
                if (UserID.equals("0")) {
                    showToast();
                } else {
                    updateimage();
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
                    startActivity(new Intent(getActivity(), FeedBacckActivity.class));
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
                                    editor.putString("user_img", "0");
                                    UserID = "0";
                                    UserImg = "0";
                                    editor.commit();
                                    circleImageView.setImageResource(R.mipmap.img_share_sina);
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


    private void updateimage() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        startActivityForResult(intent, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            Uri uri = data.getData();
            url = getImagePath(uri, null);
            ContentResolver cr = getActivity().getContentResolver();
            try {
                Log.e("qwe", url.toString());
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 将Bitmap设定到ImageView */
                circleImageView.setImageBitmap(bitmap);
                submit(url);
            } catch (FileNotFoundException e) {
                Log.e("qwe", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getImagePath(Uri uri, String seletion) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, seletion, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void submit(String path) {
        //上传图片
        final BmobFile file = new BmobFile(new File(path)); //创建BmobFile对象，转换为Bmob对象
        file.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {

                User user1 = new User();
                user1.setImg(file);
                user1.update(UserID, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            UserImg = file.getUrl();
                            SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("ayi", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mSharedPreferences.edit();
                            editor.putString("user_img", UserImg);
                            editor.commit();
                            loadImgByVolley(UserImg, circleImageView);
                            Log.i("bmob", "头像修改成功！");
                        } else {
                            Log.i("bmob", "头像修改失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
            }
        });
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
                        // Toast.makeText(getActivity(), "头像修改成功!", Toast.LENGTH_SHORT).show();
                    }
                }, 300, 200, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    //加载失败
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        imageView.setImageResource(R.mipmap.img_share_sina);
                    }
                });
        //将图片加载放入请求队列中去
        mQueue.add(imgRequest);
    }
}
