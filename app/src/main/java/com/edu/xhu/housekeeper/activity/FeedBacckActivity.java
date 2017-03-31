package com.edu.xhu.housekeeper.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.entity.Feedback;
import com.edu.xhu.housekeeper.utils.Utils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by skysoft on 2017/3/29.
 */
public class FeedBacckActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private EditText ed_phone;
    private EditText ed_content;
    private Button submit;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        SharedPreferences sp = getSharedPreferences("ayi", Context.MODE_PRIVATE);
        UserId = sp.getString("user_id", "0");
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iv_feed_back);
        submit = (Button) findViewById(R.id.bt_back_submit);
        ed_phone = (EditText) findViewById(R.id.edit_back_phone);
        ed_content = (EditText) findViewById(R.id.edit_back_content);

        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_feed_back: {
                this.finish();
                break;
            }
            case R.id.bt_back_submit: {//提交
                submit.setClickable(false);
                String tel = ed_phone.getText().toString().trim();
                String content = ed_content.getText().toString().trim();
                if (content.isEmpty()) {
                    submit.setClickable(true);
                    Toast.makeText(getApplicationContext(), "内容不能为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tel.isEmpty()) {
                    submit.setClickable(true);
                    Toast.makeText(getApplicationContext(), "电话号码不能为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!Utils.isMobileNum(tel)) {
                    Toast.makeText(this, "电话号码格式不正确", Toast.LENGTH_LONG).show();
                    ed_phone.setText("");
                    submit.setClickable(true);
                    return;
                }
                Feedback feedback = new Feedback();
                feedback.setPhone(tel);
                feedback.setContent(content);
                feedback.setUid(UserId);
                feedback.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {//注册成功！
                            Toast.makeText(getApplicationContext(), "反馈成功！感谢您的意见！", Toast.LENGTH_LONG).show();
                            FeedBacckActivity.this.finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "反馈失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            }
        }

    }
}
