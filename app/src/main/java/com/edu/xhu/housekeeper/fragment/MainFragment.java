package com.edu.xhu.housekeeper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.activity.UserLoginActivity;

public class MainFragment extends Fragment {

    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_main, null);
        initView(contentView);
        return contentView;
    }

    private void initView(View v) {
        textView = (TextView) v.findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserLoginActivity.class));
            }
        });
    }


//    public int getResource(String imageName) {
//        int resId = getResources().getIdentifier(imageName, "mipmap", getActivity().getPackageName());
//        //如果没有在"mipmap"下找到imageName,将会返回0
//        return resId;
//    }
}
