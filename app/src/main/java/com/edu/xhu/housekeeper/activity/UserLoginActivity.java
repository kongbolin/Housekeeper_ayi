package com.edu.xhu.housekeeper.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.entity.User;
import com.edu.xhu.housekeeper.utils.Utils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by skysoft on 2017/2/21.
 */
public class UserLoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mIvBack;

    /**
     * 用户号码
     */
    private EditText mEdtPhone;

    /**
     * 用户密码
     */
    private EditText mEdtPassword;

    /**
     * 登录按钮
     */
    private Button mBtnLogin;

    /**
     * 注册按钮
     */
    private TextView mTvRegister;

    private RelativeLayout mImgQQ;

    private Context mContext;
    public static QQAuth mQQAuth;
    private UserInfo mInfo;
    private Tencent mTencent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQQAuth = QQAuth.createInstance("1105929217", mContext);
        mTencent = Tencent.createInstance("1105929217", mContext);
    }

    private void init() {
        mContext = this;
        mIvBack = (ImageView) findViewById(R.id.image_rg_back);
        mEdtPhone = (EditText) findViewById(R.id.edit_name);
        mEdtPassword = (EditText) findViewById(R.id.edit_psw);
        mBtnLogin = (Button) findViewById(R.id.button_login);
        mTvRegister = (TextView) findViewById(R.id.tv_register);
        mImgQQ = (RelativeLayout) findViewById(R.id.button_qqlogin);

        mIvBack.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mImgQQ.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回按钮
            case R.id.image_rg_back:
                finish();
                break;
            //登录按钮
            case R.id.button_login:
                mBtnLogin.setClickable(false);//禁止用户重复点击按钮进行登录
                final String phone = mEdtPhone.getText().toString().trim();
                final String password = mEdtPassword.getText().toString().trim();
                //判空
                if (phone.equals("") || password.equals("")) {
                    Toast.makeText(mContext, "不能留空！", Toast.LENGTH_SHORT).show();
                    mBtnLogin.setClickable(true);
                    return;
                }
                //检查手机号码格式
                if (!Utils.isMobileNum(phone)) {
                    Toast.makeText(mContext, "手机号码格式错误!", Toast.LENGTH_SHORT).show();
                    mBtnLogin.setClickable(true);
                    return;
                }

                BmobQuery<User> query = new BmobQuery<User>();
                //查询phone叫“比目”的数据
                query.addWhereEqualTo("phone", phone);
                query.addWhereEqualTo("password", password);
                //返回50条数据，如果不加上这条语句，默认返回10条数据
                query.setLimit(1);
                //执行查询方法
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> object, BmobException e) {
                        if (e == null) {
                            // toast("查询成功：共"+object.size()+"条数据。");
                            if (object.size() < 1) {
                                Toast.makeText(mContext, "用户不存在", Toast.LENGTH_LONG).show();
                                mBtnLogin.setClickable(true);
                            } else {
                                for (User user : object) {
                                    String psw1 = user.getPassword();
                                    if (password.equals(psw1)) {
                                        //保存登录数据
                                        SharedPreferences mSharedPreferences = getSharedPreferences("ayi", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                                        editor.putString("user_id", user.getObjectId()+"");
                                        editor.putString("user_mobile",user.getPhone()+"");
                                        editor.putString("user_name",user.getName()+"");
                                        editor.commit();
                                        Toast.makeText(mContext, "登录成功！name=" + user.getName(), Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(UserLoginActivity.this,MainActivity.class));
                                    } else {
                                        Toast.makeText(mContext, "用户名与密码不匹配！", Toast.LENGTH_LONG).show();
                                        mBtnLogin.setClickable(true);
                                    }
                                }
                            }
                        } else {
                            mBtnLogin.setClickable(true);
                            Toast.makeText(mContext, "登录失败！失败：" + e.getMessage() + "," + e.getErrorCode(), Toast.LENGTH_LONG).show();
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                break;
            //qq登录按钮
            case R.id.button_qqlogin:
                Toast.makeText(mContext, "QQ登录", Toast.LENGTH_LONG).show();
                onClickLogin();
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    public void logout() {
        mTencent.logout(this);
    }


    private void onClickLogin() {
        if (!mQQAuth.isSessionValid()) {
            IUiListener listener = new BaseUiListener() {
                @Override
                public void onComplete(Object o) {
                    super.onComplete(o);
                }

                @Override
                protected void doComplete(JSONObject values) {
                    Log.d("zyq", "=====>" + "hahaha" + values.toString());
                    updateUserInfo();
                }
            };
            mQQAuth.login(this, "all", listener);
            mTencent.login(this, "all", listener);
        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(mContext, "登录成功！", Toast.LENGTH_LONG).show();
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {
            Log.d("zyq", "000------>" + values.toString());//可以获取到openid
        }

        @Override
        public void onError(UiError e) {
            Toast.makeText(mContext, "登录失败！", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(mContext, "取消登录！", Toast.LENGTH_LONG).show();
        }
    }


    private void updateUserInfo() {
        if (mQQAuth != null && mQQAuth.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onComplete(final Object response) {
                    JSONObject json = (JSONObject) response;
                    if (json.has("figureurl")) {
                        Bitmap bitmap = null;
                        try {
                            Log.d("zyq", json.getString("figureurl_qq_2"));
                        } catch (JSONException e) {

                        }
                    }
                    if (json.has("nickname")) {
                        try {
                            Log.d("zyq", json.toString());
                            Log.d("zyq", json.getString("nickname"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            mInfo = new UserInfo(this, mQQAuth.getQQToken());
            mInfo.getUserInfo(listener);
        }
    }
}


