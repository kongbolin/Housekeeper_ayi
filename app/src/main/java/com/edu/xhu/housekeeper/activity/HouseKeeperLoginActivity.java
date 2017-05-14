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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.entity.Housekeeper;
import com.edu.xhu.housekeeper.entity.MyBmobInstallation;
import com.edu.xhu.housekeeper.entity.User;
import com.edu.xhu.housekeeper.utils.Utils;

import java.util.List;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by skysoft on 2017/2/21.
 */
public class HouseKeeperLoginActivity extends BaseActivity implements View.OnClickListener{
    private ImageView mIvBack;
    private EditText mEdtPhone;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private boolean isLogin;
    private SharedPreferences sharedPreferences;
    private String ayiId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_h);

        initView();
    }


    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.image_rg_back);
        mEdtPhone = (EditText) findViewById(R.id.edit_name);
        mEdtPassword = (EditText) findViewById(R.id.edit_psw);
        mBtnLogin = (Button) findViewById(R.id.button_login);
        mIvBack.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.image_rg_back:
                finish();
                break;
            case  R.id.button_login:
                mBtnLogin.setClickable(false);//禁止用户重复点击按钮进行登录
                final String phone = mEdtPhone.getText().toString().trim();
                final String password = mEdtPassword.getText().toString().trim();
                //判空
                if (phone.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "不能留空！", Toast.LENGTH_SHORT).show();
                    mBtnLogin.setClickable(true);
                    return;
                }
                //检查手机号码格式
                if (!Utils.isMobileNum(phone)) {
                    Toast.makeText(getApplicationContext(), "手机号码格式错误!", Toast.LENGTH_SHORT).show();
                    mBtnLogin.setClickable(true);
                    return;
                }
                BmobQuery<Housekeeper> query = new BmobQuery<Housekeeper>();
                query.addWhereEqualTo("phone", phone);
                query.setLimit(1);
                query.findObjects(new FindListener<Housekeeper>() {
                    @Override
                    public void done(List<Housekeeper> object, BmobException e) {
                        if (e == null) {
                            // toast("查询成功：共"+object.size()+"条数据。");
                            if (object.size() < 1) {
                                Toast.makeText(getApplicationContext(), "用户不存在", Toast.LENGTH_LONG).show();
                                mBtnLogin.setClickable(true);
                            } else {
                                for (Housekeeper housekeeper : object) {
                                    String psw1 = housekeeper.getPassword();
                                    if (password.equals(psw1)) {
                                        //保存登录数据
                                        SharedPreferences mSharedPreferences = getSharedPreferences("ayi", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                                        editor.putString("ayi_id", housekeeper.getObjectId() + "");
                                        editor.putString("ayi_mobile", housekeeper.getPhone() + "");
                                        editor.putString("ayi_name", housekeeper.getName() + "");
                                        editor.putString("ayi_img", housekeeper.getImg().getUrl() + "");
                                        editor.putString("ayi_ratio",housekeeper.getStars());
                                        editor.putBoolean("isLogin",true);
                                        editor.commit();
                                        ayiId = housekeeper.getObjectId();
                                        Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(HouseKeeperLoginActivity.this, HouseKeeperServiceActivity.class));
                                        updateBmobInstallation();
                                        HouseKeeperLoginActivity.this.finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "用户名与密码不匹配！", Toast.LENGTH_LONG).show();
                                        mBtnLogin.setClickable(true);
                                    }
                                }
                            }
                        } else {
                            mBtnLogin.setClickable(true);
                            Toast.makeText(getApplicationContext(), "登录失败！失败：" + e.getMessage() + "," + e.getErrorCode(), Toast.LENGTH_LONG).show();
                            Log.i("ayi", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                break;
        }
    }



    /**更新自定义的BmobInstallation字段
     * @return void
     * @exception
     */
    public void updateBmobInstallation(){
        BmobQuery<MyBmobInstallation> query = new BmobQuery<MyBmobInstallation>();
        query.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(this));
        query.findObjects(new FindListener<MyBmobInstallation>() {

            @Override
            public void done(List<MyBmobInstallation> object, BmobException e) {
                if(e==null){
                    if(object.size() > 0){
                        MyBmobInstallation mbi = object.get(0);
                        mbi.setUid(ayiId);
                        mbi.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Log.i("ayi", "更新成功");
                                }else{
                                }
                            }
                        });
                    }
                }else{
                }
            }
        });
    }
}
