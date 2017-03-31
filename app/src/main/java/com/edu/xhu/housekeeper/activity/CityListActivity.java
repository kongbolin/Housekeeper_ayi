package com.edu.xhu.housekeeper.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.activity.BaseActivity;
import com.edu.xhu.housekeeper.adapter.CommonListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skysoft on 2017/3/29.
 */
public class CityListActivity extends BaseActivity implements View.OnClickListener {
    private ListView mLvCity;
    private CommonListAdapter commonAdapter;
    private List<String> StrCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citys);
        initView();
    }

    private void initView() {
        mLvCity = (ListView) findViewById(R.id.city_list);
        String[] strings = getResources().getStringArray(R.array.citys);
        StrCity = new ArrayList<String>();
        for (int i = 0; i < strings.length; i++) {
            StrCity.add(strings[i]);
        }
        commonAdapter = new CommonListAdapter(this, StrCity);
        mLvCity.setAdapter(commonAdapter);
       mLvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent();
               intent.putExtra("city",StrCity.get(position));
               setResult(2,intent);
               finish();
           }
       });
    }

    @Override
    public void onClick(View v) {

    }
}
