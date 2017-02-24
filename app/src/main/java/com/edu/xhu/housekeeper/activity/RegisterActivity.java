package com.edu.xhu.housekeeper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.entity.Person;
import com.edu.xhu.housekeeper.entity.User;
import com.edu.xhu.housekeeper.utils.Utils;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by skysoft on 2017/2/23.
 */
public class RegisterActivity extends BaseActivity {
    private ImageView mIvBack;

    private EditText mEdtName;

    private EditText mEdtPhone;

    private EditText mEdtPassword;

    private EditText mEdtYzm;

    private Button mBtnRegister;

    private TextView mTextYzm;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }


    private void init() {
        mContext = this;
        mIvBack = (ImageView) findViewById(R.id.image_rg_back);
        mEdtName = (EditText) findViewById(R.id.edit_reg_name);
        mEdtPhone = (EditText) findViewById(R.id.edit_reg_phone);
        mEdtPassword = (EditText) findViewById(R.id.edit_reg_password);
        mEdtYzm = (EditText) findViewById(R.id.edit_reg_yzm);
        mBtnRegister = (Button) findViewById(R.id.button_register);
        mTextYzm = (TextView) findViewById(R.id.text_reg_yzm);
        mTextYzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = mEdtPhone.getText().toString();
                if (!Utils.isMobileNum(phone)) {
                    Toast.makeText(RegisterActivity.this, "手机号码格式错误!", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobSMS.requestSMSCode(mContext, phone, "阿姨帮", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer smsId, cn.bmob.sms.exception.BmobException e) {
                        if (e == null) {//验证码发送成功
                            Toast.makeText(RegisterActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                            Log.i("bmob", "短信id：" + smsId);//用于查询本次短信发送详情
                        }
                    }
                });

            }
        });
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//还差一个查重的操作
                final String name = mEdtName.getText().toString();
                final String phone = mEdtPhone.getText().toString();
                final String password = mEdtPassword.getText().toString();
                String passwordcheck = mEdtYzm.getText().toString();
                if (name.equals("") || phone.equals("") || password.equals("") || passwordcheck.equals("")) {
                    Toast.makeText(RegisterActivity.this, "不能留空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Utils.isMobileNum(phone)) {
                    Toast.makeText(RegisterActivity.this, "手机号码格式错误!", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobSMS.verifySmsCode(mContext, phone, mEdtYzm.getText().toString().trim() + "", new VerifySMSCodeListener() {

                    @Override
                    public void done(cn.bmob.sms.exception.BmobException e) {
                        if (e == null) {//短信验证码已验证成功
                            Log.i("bmob", "验证通过");
                            //开始注册，增加用户信息
                            User user = new User();
                            user.setPhone(phone);
                            user.setName(name);
                            user.setPassword(password);
                            user.save(new SaveListener<String>() {
                                @Override
                                public void done(String objectId, BmobException e) {
                                    if (e == null) {//注册成功！
                                        startActivity(new Intent(mContext,MainActivity.class));
                                        Toast.makeText(getApplicationContext(), "添加数据成功，返回objectId为：" + objectId, Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "创建数据失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(mContext, "验证失败", Toast.LENGTH_LONG).show();
                            Log.i("bmob", "验证失败：code =" + e.getErrorCode() + ",msg = " + e.getLocalizedMessage());
                        }
                    }
                });
//                else {//开始注册，增加用户信息
//                    AsyncHttpClient asyncHttp = new AsyncHttpClient();
//                    RequestParams rp = new RequestParams();
//                    rp.put("name", name);
//                    rp.put("phone", phone);
//                    rp.put("password", password);
//                    rp.put("sex", "未知");
//                    asyncHttp.post(ZccApplication.IP_ADDRESS + "Login/user_reg", rp, new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int i, String s) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(s);
//                                String result = jsonObject.getString("code");
//                                if (result.equals("success")) {
//                                    Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
//                                    //保存user对象
//                                    User user = new User();
//                                    user.setName(name);
//                                    user.setImgUrl("aliuser_place_holder");
//                                    user.setPassword(password);
//                                    user.setPhone(phone);
//                                    user.setSex("未知");
//                                    DBHelper.getInstance(mContext).save(user);
//
//                                    //将userId保存
//                                    ZccApplication.mUserId = user.getId()+"";
//                                    ZccApplication.editor.putString(ZccApplication.USERID_KEY, ZccApplication.mUserId);
//                                    ZccApplication.editor.commit();
//                                    finish();
//                                } else {
//                                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            } catch (DbException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Throwable throwable, String s) {
//                            Toast.makeText(RegisterActivity.this, "网络连接失败 :(", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
            }
        });
    }

}