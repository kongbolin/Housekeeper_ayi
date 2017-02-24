//package com.edu.xhu.housekeeper.activity;
//
//import android.app.Activity;
//import android.os.Bundle;
//
//import com.baidu.mapapi.SDKInitializer;
//import com.baidu.mapapi.map.MapView;
//import com.edu.xhu.housekeeper.R;
//
///**
// * Created by skysoft on 2017/2/20.
// */
//public class MapDemoActivity extends Activity {
//    MapView mMapView = null;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        SDKInitializer.initialize(getApplicationContext());
//        setContentView(R.layout.layout_map);
//        //获取地图控件引用
//        mMapView = (MapView) findViewById(R.id.bmapView);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
//        mMapView.onDestroy();
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
//        mMapView.onResume();
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
//        mMapView.onPause();
//    }
//}
