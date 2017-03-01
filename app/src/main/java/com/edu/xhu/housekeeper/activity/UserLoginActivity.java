package com.edu.xhu.housekeeper.activity;

import android.content.Context;
import android.content.Intent;
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
//    private ImageView mImgWX;
//    private ImageView mImgWB;

    private Context mContext;
    public static QQAuth mQQAuth;
    private UserInfo mInfo;
    private Tencent mTencent;

    //  private BaseUiListener baseUiListener;

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
        //   mTencent = Tencent.createInstance("1105929217", mContext);
        //    baseUiListener = new BaseUiListener();
        mIvBack = (ImageView) findViewById(R.id.image_rg_back);
        mEdtPhone = (EditText) findViewById(R.id.edit_name);
        mEdtPassword = (EditText) findViewById(R.id.edit_psw);
        mBtnLogin = (Button) findViewById(R.id.button_login);
        mTvRegister = (TextView) findViewById(R.id.tv_register);
        mImgQQ = (RelativeLayout) findViewById(R.id.button_qqlogin);
//        mImgWX = (ImageView) findViewById(R.id.img_weixin);
//        mImgWB = (ImageView) findViewById(R.id.img_sina);

        mIvBack.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mImgQQ.setOnClickListener(this);
//        mImgWX.setOnClickListener(this);
//        mImgWB.setOnClickListener(this);
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
                mBtnLogin.setClickable(false);
                final String phone = mEdtPhone.getText().toString();
                String password = mEdtPassword.getText().toString();

                //判空
                if (phone.equals("") || password.equals("")) {
                    Toast.makeText(mContext, "不能留空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //检查手机号码格式
                if (!Utils.isMobileNum(phone)) {
                    Toast.makeText(mContext, "手机号码格式错误!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //联网发送数据
                BmobQuery<User> query = new BmobQuery<User>();
                //查询playerName叫“比目”的数据
                query.addWhereEqualTo("phone", phone);
                //返回50条数据，如果不加上这条语句，默认返回10条数据
                query.setLimit(1);
                //执行查询方法
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> object, BmobException e) {
                        if (e == null) {
                            // toast("查询成功：共"+object.size()+"条数据。");
                            for (User user : object) {
                                //获得playerName的信息
                                user.getName();
                                //获得数据的objectId信息
                                user.getObjectId();
                                //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                                user.getCreatedAt();
                                Toast.makeText(mContext, "登录成功！name=" + user.getName(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(mContext, "登录失败！失败：" + e.getMessage() + "," + e.getErrorCode(), Toast.LENGTH_LONG).show();
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });

//                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//                RequestParams rp = new RequestParams();
//                rp.put("phone", phone);
//                rp.put("password", password);
//                asyncHttpClient.post(ZccApplication.IP_ADDRESS+"Login/user_login", rp, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onFailure(Throwable throwable, String s) {
//                        Toast.makeText(LoginActivity.this, "网络连接失败 :(", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onSuccess(int i, String s) {
//                        JSONObject jsonObject;
//                        try {
//                            jsonObject = new JSONObject(s);
//                            String result = jsonObject.getString("code");
//                            if (result.equals("success")) {
//                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//
//                                //存入SharePreference
//                                User user = DBHelper.getInstance(mContext).findFirst(Selector.from(User.class).where("phone","=",phone));
//                                ZccApplication.mUserId = user.getId()+"";
//                                ZccApplication.editor.putString(ZccApplication.USERID_KEY,ZccApplication.mUserId);
//                                ZccApplication.editor.commit();
//
//                                finish();
//                            } else {
//                                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
//                                mBtnLogin.setClickable(true);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } catch (DbException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
                break;
            //qq登录按钮
            case R.id.button_qqlogin:
                Toast.makeText(mContext, "QQ登录", Toast.LENGTH_LONG).show();
                onClickLogin();
                break;
//            //微信登录
//            case R.id.img_weixin:
//                startActivity(new Intent(this, RegisterActivity.class));
//                break;
//            //微博登录
//            case R.id.img_sina:
//                startActivity(new Intent(this, RegisterActivity.class));
//                break;
            //注册按钮
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
//                                    bitmap = Util.getbitmap(json
//                                            .getString("figureurl_qq_2"));
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


