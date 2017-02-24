package com.edu.xhu.housekeeper.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.bmob.push.PushConstants;

/**
 * Created by skysoft on 2017/2/7.
 */
public class MyPushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.d("bmob", "msg1113");
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Log.d("bmob", "content is"+intent.getStringExtra("msg"));
            Log.d("bmob", "msghhh");
            Log.d("bmob", "BmobPushDemo recived is"+intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
        }
    }

}