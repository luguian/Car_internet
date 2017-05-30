package com.lu.car_internet.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.lu.car_internet.R;
import com.lu.car_internet.activity.BrandActivity;
import com.lu.car_internet.activity.LicenseActivity;
import com.lu.car_internet.activity.MainActivity;
import com.lu.car_internet.activity.PlaningActivity;
import com.lu.car_internet.activity.RouteActivity;
import com.lu.car_internet.activity.SearchActivity;
import com.lu.car_internet.adapters.Adapter_GridView;
import com.lu.car_internet.utils.HttpUtils;
import com.lu.car_internet.utils.Utils;
import com.lu.car_internet.view.AbOnItemClickListener;
import com.lu.car_internet.view.AbSlidingPlayView;
import com.netease.scan.IScanModuleCallBack;
import com.netease.scan.QrScan;
import com.netease.scan.ui.CaptureActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by lu on 2017/2/7.
 */

public class HomeFragment extends Fragment {
    private static String KEY = "6965eced61a69e02464c5e31ccf0063a";
    //分类的九宫格
    private GridView gridView_classify;
    private Adapter_GridView adapter_GridView_classify;
    private TextView show_city;
    private TextView temperature;
    private TextView currentday_temperaturevalue;
    private TextView update_timevalue;
    private TextView today_weather;
    private RelativeLayout weather_bg;
    private ImageView temperature_img;
    //首页轮播
    private AbSlidingPlayView viewPager;
    // 分类九宫格的资源文件
    private int[] pic_path_classify = {  R.drawable.menu_guide_1, R.drawable.menu_guide_2, R.drawable.menu_guide_3, R.drawable.menu_guide_4, R.drawable.menu_guide_5, R.drawable.menu_guide_6, R.drawable.menu_guide_7, R.drawable.menu_guide_8 };
    /**存储首页轮播的界面*/
    private ArrayList<View> allListView;
    /**首页轮播的界面的资源*/
    private int[] resId = { R.drawable.show_m1, R.drawable.menu_viewpager_1, R.drawable.menu_viewpager_2, R.drawable.menu_viewpager_3, R.drawable.menu_viewpager_4, R.drawable.menu_viewpager_5 };
    /**扫描二维码*/
    private CaptureActivity mCaptureContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_home, container, false);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        initView(view);
        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        if(!Utils.isNetworkAvailable(getActivity())){
            Utils.showDialogTip(4,getActivity());
        }
        else {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    //从网络上获取图片
                    openGPSSettings();
                    if (getLocation().equals("无法获取地理信息")) {
                        weather_bg.post(new Runnable() {
                            @Override
                            public void run() {
                                weather_bg.setVisibility(View.GONE);
                                Utils.showDialogTip(0, getActivity());

                            }
                        });
                    } else {
                        if (Utils.isNetworkAvailable(getActivity())) {
                            final String[] ary = getTemperature(getLocation()).split(",");
                            //  getLocation();
                            //   getTemperature(getLocation());
                            //发送一个Runnable对象
                            weather_bg.post(new Runnable() {
                                @Override
                                public void run() {
                                    weather_bg.setVisibility(View.VISIBLE);//在ImageView中显示从网络上获取到的图片
                                }

                            });
                            show_city.post(new Runnable() {
                                @Override
                                public void run() {
                                    show_city.setText(getLocation());//在ImageView中显示从网络上获取到的图片
                                }

                            });
                            temperature.post(new Runnable() {
                                @Override
                                public void run() {
                                    temperature.setText(ary[1] + "°C");//在ImageView中显示从网络上获取到的图片
                                }
                            });
                            currentday_temperaturevalue.post(new Runnable() {
                                @Override
                                public void run() {
                                    currentday_temperaturevalue.setText(ary[0]);//在ImageView中显示从网络上获取到的图片
                                }
                            });
                            update_timevalue.post(new Runnable() {
                                @Override
                                public void run() {
                                    update_timevalue.setText(ary[2]);//在ImageView中显示从网络上获取到的图片
                                }
                            });
                            today_weather.post(new Runnable() {
                                @Override
                                public void run() {
                                    today_weather.setText(ary[3]);//在ImageView中显示从网络上获取到的图片
                                }

                            });
                            temperature_img.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (ary[3].indexOf("晴") != -1) {
                                        temperature_img.setImageResource(R.drawable.qing);//在ImageView中显示从网络上获取到的图片
                                    }
                                    if (ary[3].indexOf("雨") != -1) {
                                        temperature_img.setImageResource(R.drawable.dayu);//在ImageView中显示从网络上获取到的图片
                                    }
                                    if (ary[3].indexOf("多云") != -1) {
                                        temperature_img.setImageResource(R.drawable.duoyun);//在ImageView中显示从网络上获取到的图片
                                    }
                                    if (ary[3].indexOf("阴") != -1) {
                                        temperature_img.setImageResource(R.drawable.yin);//在ImageView中显示从网络上获取到的图片
                                    }

                                }

                            });
                            weather_bg.post(new Runnable() {
                                @Override
                                public void run() {
                                    Drawable Drawable;
                                    Resources resources = getContext().getResources();
                                    if (ary[3].indexOf("雨") != -1) {
                                        Drawable = resources.getDrawable(R.drawable.rain);//在ImageView中显示从网络上获取到的图片
                                        weather_bg.setBackground(Drawable);//在ImageView中显示从网络上获取到的图片
                                    }
                                    if (ary[3].indexOf("晴") != -1) {
                                        Drawable = resources.getDrawable(R.drawable.qingtian);//在ImageView中显示从网络上获取到的图片
                                        weather_bg.setBackground(Drawable);//在ImageView中显示从网络上获取到的图片
                                    }
                                    if (ary[3].indexOf("多云") != -1) {
                                        Drawable = resources.getDrawable(R.drawable.duoyuntianqi);//在ImageView中显示从网络上获取到的图片
                                        weather_bg.setBackground(Drawable);//在ImageView中显示从网络上获取到的图片
                                    }
                                    if (ary[3].indexOf("阴") != -1) {
                                        Drawable = resources.getDrawable(R.drawable.yintian);//在ImageView中显示从网络上获取到的图片
                                        weather_bg.setBackground(Drawable);//在ImageView中显示从网络上获取到的图片
                                    }


                                }

                            });
                        } else {
                            Utils.showDialogTip(4, getActivity());
                        }

                    }


                }
            }).start();//开启线程
        }

    }
    private void initView(View view){
        gridView_classify = (GridView) view.findViewById(R.id.my_gridview);
        gridView_classify.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter_GridView_classify = new Adapter_GridView(getActivity(), pic_path_classify);
        gridView_classify.setAdapter(adapter_GridView_classify);
        viewPager = (AbSlidingPlayView) view.findViewById(R.id.viewPager_menu);
        show_city = (TextView)view.findViewById(R.id.city_show);
        temperature = (TextView)view.findViewById(R.id.temperature );
        currentday_temperaturevalue =(TextView)view.findViewById(R.id.currentday_temperaturevalue);
        update_timevalue = (TextView)view.findViewById(R.id.update_timevalue);
        today_weather = (TextView)view.findViewById(R.id.today_weather);
        weather_bg = (RelativeLayout)view.findViewById(R.id.weather_bg);
        temperature_img = (ImageView)view.findViewById(R.id.temperature_img);
        //设置播放方式为顺序播放
        viewPager.setPlayType(1);
        //设置播放间隔时间
        viewPager.setSleepTime(3000);
        gridView_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                switch(arg2){
                    case 0:
                        Intent intentnav = new Intent(getActivity(), RouteActivity.class);
                        startActivity(intentnav);
                        break;
                    case 1:
                        Intent intentsearch = new Intent(getActivity(), SearchActivity.class);
                        startActivity(intentsearch);
                        break;
                    case 2:
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        Intent intentLicense = new Intent(getActivity(), LicenseActivity.class);
                        startActivity(intentLicense);
                        break;
                    case 4:
                        Intent intentplann = new Intent(getActivity(), PlaningActivity.class);
                        startActivity(intentplann);
                        break;
                    case 5:

                        break;
                    case 6:
                        QrScan.getInstance().launchScan(getActivity(), new IScanModuleCallBack() {
                            @Override
                            public void OnReceiveDecodeResult(final Context context, String result) {
                                mCaptureContext = (CaptureActivity)context;

                                AlertDialog dialog = new AlertDialog.Builder(mCaptureContext)
                                        .setMessage(result)
                                        .setCancelable(false)
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                QrScan.getInstance().restartScan(mCaptureContext);
                                            }
                                        })
                                        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                QrScan.getInstance().finishScan(mCaptureContext);
                                            }
                                        })
                                        .create();
                                dialog.show();
                            }
                        });
                        break;
                    default:
                        Intent intentBrnd = new Intent(getActivity(), BrandActivity.class);
                        startActivity(intentBrnd);
                        break;
                }

            }
        });
        initViewPager();
    }
    private void initViewPager() {

        if (allListView != null) {
            allListView.clear();
            allListView = null;
        }
        allListView = new ArrayList<View>();

        for (int i = 0; i < resId.length; i++) {
            //导入ViewPager的布局
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_picture_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
            imageView.setImageResource(resId[i]);
            allListView.add(view);
        }


        viewPager.addViews(allListView);
        //开始轮播
        viewPager.startPlay();
        viewPager.setOnItemClickListener(new AbOnItemClickListener() {

            @Override
            public void onClick(int position) {

            }
        });
    }
    private void openGPSSettings() {
        LocationManager alm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (alm
                .isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getActivity(), "GPS模块正常", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Toast.makeText(getActivity(), "请开启GPS！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        startActivityForResult(intent,0); //此为设置完成后返回到获取界面

    }
    private String getLocation()
    {
        // 获取位置管理服务
        LocationManager locationManager;
        Location location;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getActivity().getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        try {
            //需要权限的操作
            if(provider != null){
                location = locationManager.getLastKnownLocation(provider);
                return updateToNewLocation(location);// 通过GPS获取位置
            }else{
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
          //  getcity(la,lb);
           // show_city.setText("维度：" +  latitude+ "\n经度" + longitude);
         //   show_city.setText(getcity(la,lb));

            return getcity(la,lb);
        } else {
          //  show_city.setText("无法获取地理信息");
            return "无法获取地理信息";
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
    public String getTemperature(String city){
        String cityjson="";
        String cityvalue = city;

        try {
            cityjson = URLEncoder.encode(cityvalue, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

            e1.printStackTrace();
        }
        String url = String .format("http://v.juhe.cn/weather/index?cityname=%s&key=0cddbbaefc86bb3c36ef3f5bbadcaef3", cityjson);
        /**
         * 起点转换经纬度
         */
        String Sp = HttpUtils.getJsonContent(url,"UTF-8");
        String data="";
        try {
            data=HttpUtils.jsontemperature(Sp);
        } catch (Exception e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
        return data;
    }



}
