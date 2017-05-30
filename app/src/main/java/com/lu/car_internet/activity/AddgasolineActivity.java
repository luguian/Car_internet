package com.lu.car_internet.activity;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.lu.car_internet.R;
import com.lu.car_internet.adapters.AddgasolineAdapter;
import com.lu.car_internet.beans.GasolineBean;
import com.lu.car_internet.utils.HttpUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu on 2017/4/13.
 */

public class AddgasolineActivity extends Activity {
    private ListView addgasoline_listview;
    private AddgasolineAdapter addgasolineAdapter;
    private static String KEY = "6965eced61a69e02464c5e31ccf0063a";
    private boolean granted=true;
    private EditText city_location;
    private ImageView location_logo;
    private Dialog addgasolineDialog;
    private ImageButton choosestation_back;
    private String state="";
    private String sjson="";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordergasoline);
        addgasoline_listview = (ListView)findViewById(R.id.addgasoline_listview);
        choosestation_back = (ImageButton) findViewById(R.id.choosestation_back);
       // LayoutInflater inflater = LayoutInflater.from(AddgasolineActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_listviewtop, null);
       // addgasoline_listview.addHeaderView(view);
        addgasoline_listview.addHeaderView( view ,null,false);
        choosestation_back.setOnClickListener(new finishchoose());
        city_location =(EditText)view.findViewById(R.id.city_location);
        location_logo = (ImageView)view.findViewById(R.id.location_logo);
        location_logo.setOnClickListener(new changecity());
        addgasoline_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(AddgasolineActivity.this, ((GasolineBean)addgasolineAdapter.getItem(position-1)).getGasoline_station(), Toast.LENGTH_SHORT).show();
            }
        });
}
  protected void onResume(){
      super.onResume();
      openGPSSettings();
      initPermission();
      initListener();
//      getHandler.post(mRunnable);

  }
    private String getJson(String city){
        String gasolinemessage="";
        try {
            gasolinemessage = URLEncoder.encode(city, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String .format("http://apis.juhe.cn/oil/region?key=6fceb332950cde85e5cad06e89bf2c5b&city=%s", gasolinemessage);
        String Sp = HttpUtils.getJsonContent(url,"UTF-8");
        return Sp;
    }
    private List getData(String jsonString){
        List<GasolineBean> list = new ArrayList();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String result = jsonObject.getString("result");
            if(result.equals("null")){
                state ="没数据";
            }else{
                state ="有数据";
                jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i= 0;i<jsonArray.length();i++){
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    GasolineBean gasoline = new GasolineBean();
                    gasoline.setGasoline_station(jsonObject2.getString("areaname"));
                    gasoline.setGasoline_station_address(jsonObject2.getString("name"));
                    JSONObject jsonobject3=jsonObject2.getJSONObject("price");
                    gasoline.setE90price(jsonobject3.getString("E90"));
                    gasoline.setE93price(jsonobject3.getString("E93"));
                    gasoline.setE97price(jsonobject3.getString("E97"));
                    gasoline.setE0price(jsonobject3.getString("E0"));
                    list.add(gasoline);
                }
            }

        } catch (JSONException e) {

            Toast.makeText(AddgasolineActivity.this, state, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return list;
    }

    private void openGPSSettings() {
        LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            return;
        }
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        startActivityForResult(intent,0); //此为设置完成后返回到获取界面
    }
    private String getLocation()
    {
        // 获取位置管理服务
        LocationManager locationManager;
        Location location;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) this.getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); //低功耗
        String provider = locationManager.getBestProvider(criteria, true); //获取GPS信息
        try {
            //需要权限的操作
            if(provider != null){
                location = locationManager.getLastKnownLocation(provider);
                return updateToNewLocation(location);// 通过GPS获取位置
            }else{
     //           Toast.makeText( AddgasolineActivity.this, "没开GPS", Toast.LENGTH_SHORT).show();
                return updateToNewLocation(null);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return "-1";

        // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
        // locationManager.requestLocationUpdates(provider, 100 * 1000, 500, locationListener);
    }
    private String updateToNewLocation(Location location) {
        if (location != null) {
            double  latitude = location.getLatitude();
            double longitude= location.getLongitude();
            String la=Double.toString(latitude);
            String lb=Double.toString(longitude);
            return getcity(la,lb);
          //  return city;
        } else {
            //  show_city.setText("无法获取地理信息");
            return "正在获取地理信息";
        }

    }
    public String getcity(String latitude,String longitude){
        String latit="";
        String longitu="";
        String lat = latitude;
        String longit = longitude;
        try {
            latit = URLEncoder.encode(lat, "UTF-8");
            longitu = URLEncoder.encode(longit, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String url = String .format("http://restapi.amap.com/v3/geocode/regeo?location=%s,%s&key=%s",longitu,latit,KEY);
        /**
         * 起点转换经纬度
         */
        String Sp = HttpUtils.getJsonContent(url,"UTF-8");
        String data="";
        try {
            data=HttpUtils.jsoncity(Sp);
        } catch (Exception e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
        return data;
    }
    private void initPermission() {
        int permission = ContextCompat.checkSelfPermission(AddgasolineActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //需不需要解释的dialog
            if (shouldRequest()) return;
            //请求权限
            ActivityCompat.requestPermissions(AddgasolineActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }
    private boolean shouldRequest() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            //显示一个对话框，给用户解释

            return true;
        }else{
            ActivityCompat.requestPermissions(AddgasolineActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0) {
            granted = grantResults[0] == PackageManager.PERMISSION_GRANTED;//是否授权，可以根据permission作为标记
        }
    }
    private void initListener() {
        if (granted) {
            addgasolineDialog = new LoadingDialog(AddgasolineActivity.this,"正在查询....");
            addgasolineDialog.setCanceledOnTouchOutside(false);
            addgasolineDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                        Message msg = addgasolinehandler.obtainMessage();
                        msg.what = 0;
                        addgasolinehandler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } else {
            city_location.setText("正在获取位置");
  //          Toast.makeText(AddgasolineActivity.this, "你还没有获取位置权限", Toast.LENGTH_SHORT).show();
        }
    }
  private class changecity implements View.OnClickListener{

      @Override
      public void onClick(View view) {

          final String city = city_location.getText().toString();
          addgasolineDialog = new LoadingDialog(AddgasolineActivity.this,"正在查询....");
          addgasolineDialog.setCanceledOnTouchOutside(false);
          addgasolineDialog.show();
          new Thread(new Runnable(){
              @Override
              public void run() {
                  try {
                      Thread.sleep(1000);
                      sjson = getJson(city);
                      getData(sjson);
                      if(state.equals("没数据")){
                          Message msg = addgasolinehandler.obtainMessage();
                          msg.what = 1;
                          addgasolinehandler.sendMessage(msg);
                      }else{
                          Message msg = addgasolinehandler.obtainMessage();
                          msg.what = 2;
                          addgasolinehandler.sendMessage(msg);
                      }
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          }).start();
         // city_location.setText(city);


      }
  }


    @Override
    protected void onPause() {
//        getHandler.removeCallbacks(mRunnable);
//        mHandler.removeCallbacks(mThread);
//        addgasolineDialog.dismiss();
        super.onPause();
    }

    private class finishchoose implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }

    // 此方法在主线程中调用，可以更新UI
    Handler addgasolinehandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 处理消息时需要知道是成功的消息还是失败的消息
            switch (msg.what) {
                case 0:
                    addgasolineDialog.dismiss();
                    String city = getLocation();
                    city_location.setText(city);
                    String json = getJson(city);
                    addgasolineAdapter = new AddgasolineAdapter(AddgasolineActivity.this, getData(json));
                    addgasoline_listview.setAdapter(addgasolineAdapter);
                    break;
                case 1:
                    addgasolineDialog.dismiss();
                    Toast.makeText(AddgasolineActivity.this,"查询不到数据", Toast.LENGTH_SHORT).show();
                    addgasoline_listview.setAdapter(null);
                    break;
                case 2:
                   // String json1 =sjson;
                    addgasolineDialog.dismiss();
                 //   String cityother = getLocation();
                    addgasolineAdapter = new AddgasolineAdapter(AddgasolineActivity.this, getData(sjson));
                    addgasoline_listview.setAdapter(addgasolineAdapter);
                    break;
                default:
                    break;
            }

        }
    };
}
