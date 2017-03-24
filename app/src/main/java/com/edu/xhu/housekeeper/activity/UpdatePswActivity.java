package com.edu.xhu.housekeeper.activity;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by skysoft on 2017/3/16.
 */
public class UpdatePswActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mIvBack;
    private EditText mEtPaw1;
    private EditText mEtPaw2;
    private EditText mEtPaw3;
    private Button mBtSave;
    private Context mContext;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_psw);
        mContext = this;
        SharedPreferences sp = getSharedPreferences("ayi", Context.MODE_PRIVATE);
        //取得user_id和手机号
        UserId = sp.getString("user_id", "");//如果取不到值就取后面的""
        initView();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_update_psw_update);
        mEtPaw1 = (EditText) findViewById(R.id.et_update_psw1);
        mEtPaw2 = (EditText) findViewById(R.id.et_update_psw2);
        mEtPaw3 = (EditText) findViewById(R.id.et_update_psw3);
        mBtSave = (Button) findViewById(R.id.button_update_psw);
        mIvBack.setOnClickListener(this);
        mBtSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_update_psw_update:
                this.finish();
                break;
            case R.id.button_update_psw:
                final String psw1 = mEtPaw1.getText().toString().trim();
                final String psw2 = mEtPaw2.getText().toString().trim();
                final String psw3 = mEtPaw3.getText().toString().trim();
                if (psw1.isEmpty() || psw2.isEmpty() || psw3.isEmpty()) {
                    Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    if (psw2.equals(psw3)) {
                        BmobQuery<User> query = new BmobQuery<User>();
                        query.getObject(UserId, new QueryListener<User>() {
                            @Override
                            public void done(User object, BmobException e) {
                                if (e == null) {
                                    String psw = object.getPassword();
                                    if (psw.equals(psw1)) {
                                        //开始更新密码
                                        User user1 = new User();
                                        user1.setPassword(psw2);
                                        user1.update(UserId, new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    UpdatePswActivity.this.finish();
                                                    Toast.makeText(mContext, "密码修改成功!", Toast.LENGTH_SHORT).show();
                                                    Log.i("bmob", "密码修改成功！");
                                                } else {
                                                    Log.i("bmob", "密码修改失败：" + e.getMessage() + "," + e.getErrorCode());
                                                }
                                            }
                                        });

                                    } else {
                                        Toast.makeText(mContext, "原始密码不正确！", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }

                        });
                    } else {
                        Toast.makeText(mContext, "新密码两次输入不相同！", Toast.LENGTH_LONG).show();
                    }
                }

                break;
        }

    }
}
