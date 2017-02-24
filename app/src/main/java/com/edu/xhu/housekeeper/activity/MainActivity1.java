package com.edu.xhu.housekeeper.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.xhu.housekeeper.R;
import com.edu.xhu.housekeeper.entity.Person;
import com.edu.xhu.housekeeper.entity.share;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity1 extends Activity implements View.OnClickListener {
    private Button button_add;
    private Button button_push;
    private Button button_reg;
    private TextView textView_yzm;
    private EditText editText_phone;
    private EditText editText_psw;
    private EditText editText_yzm;
    private String Appid = "2b2a345973e3da8c190b83680dfa5101";
    private TextView tv_show;
    private LocationManager locationManager;
    private String locationProvider;
    private static int CAMERA_REQUEST_CODE = 2;
    private static int GALLERY_REQUEST_CODE = 3;
    private static int CROP_REQUEST_CODE = 4;
    private CircleImageView img;
    private Button upload;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //提供以下两种方式进行初始化操作：
        //第一：默认初始化
        Bmob.initialize(this, Appid);
        BmobSMS.initialize(this, Appid);
        // BmobSMS.initialize(this, Appid, new MySMSCodeListener());
        button_add = (Button) findViewById(R.id.button_add);
        button_reg = (Button) findViewById(R.id.button_reg);
        textView_yzm = (TextView) findViewById(R.id.yanzm);
        editText_yzm = (EditText) findViewById(R.id.yanz);
        editText_phone = (EditText) findViewById(R.id.phone);
        editText_psw = (EditText) findViewById(R.id.psw);
        button_push = (Button) findViewById(R.id.button_push);
        img= (CircleImageView) findViewById(R.id.img);
        upload= (Button) findViewById(R.id.upload);
        button_add.setOnClickListener(this);
        button_push.setOnClickListener(this);
        button_reg.setOnClickListener(this);
        textView_yzm.setOnClickListener(this);
        upload.setOnClickListener(this);

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);

        tv_show = (TextView) findViewById(R.id.tv_show);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }
        //获取Location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the User grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            //不为空,显示地理位置经纬度
            showLocation(location);
        }
        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
                Person p2 = new Person();
                p2.setName("lucky");
                p2.setAddress("北京海淀");
                p2.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "添加数据成功，返回objectId为：" + objectId, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "创建数据失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case R.id.button_push:
                Toast.makeText(getApplicationContext(), "开始推送消息", Toast.LENGTH_LONG).show();
                BmobInstallation.getCurrentInstallation().save();
                BmobPush.startWork(this);
                break;
            case R.id.button_reg:
                BmobSMS.verifySmsCode(MainActivity1.this, "18408243864", editText_yzm.getText().toString().trim() + "", new VerifySMSCodeListener() {

                    @Override
                    public void done(cn.bmob.sms.exception.BmobException e) {
                        if (e == null) {//短信验证码已验证成功
                            Log.i("bmob", "验证通过");
                        } else {
                            Log.i("bmob", "验证失败：code =" + e.getErrorCode() + ",msg = " + e.getLocalizedMessage());
                        }
                    }
                });

                break;
            case R.id.yanzm:
                BmobSMS.requestSMSCode(MainActivity1.this, "18408243864", "阿姨帮", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer smsId, cn.bmob.sms.exception.BmobException e) {
                        if (e == null) {//验证码发送成功
                            Log.i("bmob", "短信id：" + smsId);//用于查询本次短信发送详情
                        }
                    }
                });
                break;
            case R.id.upload:
              //  updateimage();//上传图片
               // selectById();//通过ID查找单条信息
                break;
        }
    }

    private void selectById() {
        BmobQuery<share> query = new BmobQuery<share>();
        query.getObject("235bd0e41a", new QueryListener<share>() {

            @Override
            public void done(share object, BmobException e) {
                if(e==null){
                    //获得playerName的信息
                    object.getIcon().getUrl();
                    //获得数据的objectId信息
                    object.getObjectId();
                    //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                    object.getCreatedAt();
                    Toast.makeText(MainActivity1.this, object.getIcon().getUrl(),Toast.LENGTH_LONG).show();
                    Log.i("zyq","URL："+ object.getIcon().getUrl()+"");
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
    }

    /**
     * 显示地理位置经度和纬度信息
     *
     * @param location
     */
    private void showLocation(Location location) {
        String locationStr = "维度：" + location.getLatitude() + "\n"
                + "经度：" + location.getLongitude();
        tv_show.setText(locationStr);
    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            showLocation(location);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //移除监听器
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the User grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(locationListener);
        }
    }

    private void updateimage() {
        // TODO Auto-generated method stub
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        startActivityForResult(intent, 1);

        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        startActivityForResult(intent, 1);

    }

//    private Uri saveBitmap(Bitmap bm) {
//        File tmpDir = new File(Environment.getExternalStorageDirectory()
//                + "/com.Tsstar.zyq");
//        if (!tmpDir.exists()) {
//            tmpDir.mkdir();
//        }
//        File img = new File(tmpDir.getAbsolutePath() + "avater.png");
//
//        url=tmpDir.getAbsolutePath() + "avater.png";
//        Log.d("zyq","URLsss:"+url);
//        try {
//            FileOutputStream fos = new FileOutputStream(img);
//            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
//            fos.flush();
//            fos.close();
//            return Uri.fromFile(img);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    private Uri convertUri(Uri uri) {
//        InputStream is = null;
//        try {
//            is = getContentResolver().openInputStream(uri);
//            Bitmap bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//            return saveBitmap(bitmap);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    private void startImageZoom(Uri uri) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", 300);
//        intent.putExtra("outputY", 300);
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, CROP_REQUEST_CODE);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////        if (resultCode == 1) {
////            // 可从data中取出修改个性签名界面返回的值
////            String info = data.getStringExtra("infos");
////            text_info.setText(info);
////        }
//        if (requestCode == CAMERA_REQUEST_CODE) {
//            if (data == null) {
//                return;
//            } else {
//                Bundle extras = data.getExtras();
//                if (extras != null) {
//                    Bitmap bm = extras.getParcelable("data");
//                    Uri uri = saveBitmap(bm);
//                    Log.d("zyq","URI:"+uri);
//                    startImageZoom(uri);
//                  //  url=getRealFilePath(MainActivity1.this,uri );
//                    Log.d("zyq","URL:"+getRealFilePath(MainActivity1.this,uri ));
//                }
//            }
//        } else if (requestCode == GALLERY_REQUEST_CODE) {
//            if (data == null) {
//                return;
//            }
//            Uri uri;
//            uri = data.getData();
//            Uri fileUri = convertUri(uri);
//            Log.d("zyq","URI111:"+uri);
//           // url=getRealFilePath(MainActivity1.this,uri );
//            Log.d("zyq","URL1111:"+getRealFilePath(MainActivity1.this,uri ));
//            startImageZoom(fileUri);
//        } else if (requestCode == CROP_REQUEST_CODE) {
//            if (data == null) {
//                return;
//            }
//            Bundle extras = data.getExtras();
//            if (extras == null) {
//                return;
//            }
//            Bitmap bm = extras.getParcelable("data");
//            // ImageView imageView = (ImageView)findViewById(R.id.);
//           img.setImageBitmap(bm);
//           // sendImage(bm);
//           submit(url);
//        }
//
//    }



    private void submit(  String path) {
        //上传图片
      //  String path="sdcard/Download/shareone.jpg";
        final BmobFile file=new BmobFile(new File(path)); //创建BmobFile对象，转换为Bmob对象
      //  BmobFile file=new BmobFile(Susername,null,new File(path).toString());
        file.upload( new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                share share = new share();
                share.setName("ShareTwo");
                share.setAge(22);
                share.setIcon(file);  //设置图片
                share.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        Toast.makeText(MainActivity1.this, "上传成功", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
//        file.upload(MainActivity1.this,new UploadFileListener() {
//            @Override
//            public void onSuccess() {
//                share share = new share();
//                share.setName("ShareOne");
//                share.setAge(20);
//                share.setIcon(file);  //设置图片
//                share.save(MainActivity1.this, new SaveListener() {
//                    @Override
//                    public void onSuccess() {
//                        Toast.makeText(MainActivity1.this, "上传成功", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//                        Toast.makeText(MainActivity1.this, "上传失败" + s, Toast.LENGTH_LONG).show();
//
//                    }
//                });
//            }
//        }
        }

//    public static String getRealFilePath( final Context context, final Uri uri ) {
//        if ( null == uri ) return null;
//        final String scheme = uri.getScheme();
//        String data = null;
//        if ( scheme == null )
//            data = uri.getPath();
//        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
//            data = uri.getPath();
//        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
//            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
//            if ( null != cursor ) {
//                if ( cursor.moveToFirst() ) {
//                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
//                    if ( index > -1 ) {
//                        data = cursor.getString( index );
//                    }
//                }
//                cursor.close();
//            }
//        }
//        return data;
//    }
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
        Uri uri = data.getData();
        url = getImagePath(uri, null);
        ContentResolver cr = this.getContentResolver();
        try {
            Log.e("qwe", url.toString());
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                /* 将Bitmap设定到ImageView */
            img.setImageBitmap(bitmap);
            submit(url);
        } catch (FileNotFoundException e) {
            Log.e("qwe", e.getMessage(),e);
        }
    }
    super.onActivityResult(requestCode, resultCode, data);
}
    private String getImagePath(Uri uri, String seletion) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, seletion, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();

        }
        return path;

    }
}
