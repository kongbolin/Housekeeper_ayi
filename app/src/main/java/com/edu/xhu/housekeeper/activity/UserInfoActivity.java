package com.edu.xhu.housekeeper.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.entity.User;
import com.edu.xhu.housekeeper.utils.Utils;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by skysoft on 2017/3/17.
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mIvBack;
    private EditText mEdName;
    private EditText mEdSex;
    private EditText mEdPhone;
    private EditText mEdAge;
    private Button mBtSave;
    private String UserName;
    private String UserPhone;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_update_info_back);
        mEdName = (EditText) findViewById(R.id.et_update_info_name);
        mEdSex = (EditText) findViewById(R.id.et_update_info_sex);
        mEdPhone = (EditText) findViewById(R.id.et_update_info_phone);
        mEdAge = (EditText) findViewById(R.id.et_update_info_age);
        mBtSave = (Button) findViewById(R.id.button_update_info);
        mBtSave.setOnClickListener(this);
        mIvBack.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences("ayi", Context.MODE_PRIVATE);
        UserName = sp.getString("user_name", "未登录");
        mEdName.setText(UserName);
        UserId = sp.getString("user_id", "0");
        BmobQuery<User> query = new BmobQuery<User>();
        query.getObject(UserId, new QueryListener<User>() {
            @Override
            public void done(User object, BmobException e) {
                if (e == null) {
                    String sex = object.getSex();
                    if (sex != null)
                        mEdSex.setText(sex);
                    String phone = object.getPhone();
                    if (phone != null)
                        mEdPhone.setText(phone);
                    String age = object.getAge();
                    if (age != null)
                        mEdAge.setText(age);
                    String name = object.getName();
                    if (name != null)
                        mEdName.setText(name);
                } else {
                    Log.e("bmob", e + "");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update_info:
                final User user1 = new User();
                String sex = mEdSex.getText().toString().trim();
                String phone = mEdPhone.getText().toString().trim();
                String age = mEdAge.getText().toString().trim();
                String name = mEdName.getText().toString().trim();
                if (sex != null)
                    user1.setSex(sex);
                if (phone != null && Utils.isMobileNum(phone)) {
                    user1.setPhone(phone);
                }else {
                    Toast.makeText(this,"电话号码格式不正确！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (age != null)
                    user1.setAge(age);
                if (name != null)
                    user1.setName(name);
                user1.update(UserId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            UserInfoActivity.this.finish();
                            SharedPreferences mSharedPreferences = getSharedPreferences("ayi", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mSharedPreferences.edit();
                            editor.putString("user_mobile", user1.getPhone() + "");
                            editor.putString("user_name", user1.getName() + "");
                            editor.commit();
                            Toast.makeText(UserInfoActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
                            Log.i("bmob", "密码修改成功！");
                        } else {
                            Log.i("bmob", "密码修改失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                break;
            case R.id.iv_update_info_back:
                this.finish();
                break;
        }

    }
}
