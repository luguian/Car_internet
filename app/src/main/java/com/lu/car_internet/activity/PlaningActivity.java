package com.lu.car_internet.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.overlay.BusRouteOverlay;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviStaticInfo;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.routepoisearch.RoutePOIItem;
import com.amap.api.services.routepoisearch.RoutePOISearch;
import com.amap.api.services.routepoisearch.RoutePOISearch.OnRoutePOISearchListener;
import com.amap.api.services.routepoisearch.RoutePOISearch.RoutePOISearchType;
import com.amap.api.services.routepoisearch.RoutePOISearchQuery;
import com.amap.api.services.routepoisearch.RoutePOISearchResult;
import com.autonavi.tbt.NaviStaticInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.lu.car_internet.R;
import com.lu.car_internet.utils.HttpUtils;
import com.lu.car_internet.utils.Utils;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PlaningActivity extends Activity implements OnClickListener,LocationSource,
 OnMapClickListener,OnInfoWindowClickListener, InfoWindowAdapter,OnRoutePOISearchListener,OnMarkerClickListener,OnRouteSearchListener,AMapLocationListener,AMapNaviListener{
	private static String KEY = "6965eced61a69e02464c5e31ccf0063a"; 
	private AMap aMap;
    private MapView mapView;
    private AMapNavi mAMapNavi;
   
    private StringBuffer buffer;
    /**
     * 公交按钮,驾车按钮,步行按钮
     */  
    private ImageButton transitBtn,drivingBtn,walkBtn;
    /**
     * 输入起点,终点框
     */
   // private AutoCompleteTextView editpoint_et,editdestination_et;
	private TextView editpoint_et,editdestination_et;
    private ListView pointlist,destinationlist;
    private int busMode=RouteSearch.BusDefault;//公交默认模式
    private int drivingMode=RouteSearch.DrivingDefault;//驾车默认模式
    private int walkMode=RouteSearch.WalkDefault;//步行默认模式
	private RouteSearch routeSearch;
	LatLonPoint startPoint;
	LatLonPoint endPoint;
	private BusRouteResult busRouteResult;//公交模式查询结果
	private DriveRouteResult driveRouteResult;//驾车模式查询结果
	private WalkRouteResult walkRouteResult;//步行模式查询结果
	
	
//新增
	private Context mContext;
	private ProgressDialog progDialog = null;//搜索时进度条
	private myRoutePoiOverlay overlay;
	private TextView gasbtn,ATMbtn,Maibtn,Toibtn;
	  //定位发起端
	public  AMapLocationClient mLocationClient=null;  
	  //在主线程获得地图对象AMap，b并设置定位监听且实现LocationSource接口
	  //定位监听器
    private OnLocationChangedListener mListener;
	  //定位参数
	private AMapLocationClientOption mLocationOption=null;
	  //标识
	private boolean isFirstLoc=true;
	private String city = "";
	private ImageView planningexchange_position;

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		ActivityCompat.requestPermissions(PlaningActivity.this, new String[]{
				Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    	setContentView(R.layout.activity_road);
    	mapView=(MapView) findViewById(R.id.planroute_map);
    	mapView.onCreate(savedInstanceState);

	  
    }
    private void  initialise(){
		closeStrictMode();
		transitBtn = (ImageButton) findViewById(R.id.imagebtn_roadsearch_tab_transit);
		drivingBtn = (ImageButton) findViewById(R.id.imagebtn_roadsearch_tab_driving);
		walkBtn = (ImageButton) findViewById(R.id.imagebtn_roadsearch_tab_walk);
		editpoint_et = (TextView)findViewById(R.id.planningpoint_et);
		editdestination_et = (TextView)findViewById(R.id.planningdestion_et);
		planningexchange_position = (ImageView)findViewById(R.id.planningexchange_position);
		planningexchange_position.setOnClickListener(this);
		editpoint_et.setOnClickListener(this);
		editdestination_et.setOnClickListener(this);
		SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
		String pointvalue=sPreferences.getString("point", "");
		if(!pointvalue.equals("")){
			editpoint_et.setText(pointvalue);
		}
		String destvalue  = sPreferences.getString("destion", "");
		if(!destvalue.equals("")){
			editdestination_et.setText(destvalue);
		}
//		editpoint_et=(AutoCompleteTextView) findViewById(R.id.editpoint_et);
//		editdestination_et=(AutoCompleteTextView) findViewById(R.id.editdestination_et);
//		editpoint_et.setOnFocusChangeListener(new pointFocuschange());
//		pointlist=(ListView)findViewById(R.id.pointlist);
//		destinationlist=(ListView)findViewById(R.id.destinationlist);
//		editpoint_et.addTextChangedListener(this);
//		editdestination_et.addTextChangedListener(this);
//		pointlist.setOnItemClickListener(new PointOnItemClickListener());
//		destinationlist.setOnItemClickListener(new DestOnItemClickListener());
		gasbtn = (TextView) findViewById(R.id.gasbtn);
		ATMbtn = (TextView) findViewById(R.id.ATMbtn);
		Maibtn = (TextView) findViewById(R.id.Maibtn);
		Toibtn = (TextView) findViewById(R.id.Toibtn);
  /*    gasbtn.setOnClickListener(this);
        ATMbtn.setOnClickListener(this);
        Maibtn.setOnClickListener(this);
        Toibtn.setOnClickListener(this);
   */

		routeSearch=new RouteSearch(this);
		routeSearch.setRouteSearchListener(this);
		// aMap = mapView.getMap();
		init();
		UiSettings settings=aMap.getUiSettings();
		aMap.setLocationSource(this);
		settings.setMyLocationButtonEnabled(true);
		aMap.setMyLocationEnabled(true);
		mContext = this.getApplication();
		registerListener();
	}



	
  /**
   * 初始化AMap对象
   */
    private void init(){
    	if(aMap==null){
    		aMap=mapView.getMap();	
    	}
    	//mContext = this.getApplicationContext();
    	transitBtn.setOnClickListener(this);
    	drivingBtn.setOnClickListener(this);
    	walkBtn.setOnClickListener(this);
    	//设置地图可视缩放大小
    	aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();  
    }
    /**
     * 注册监听新增
     */
    private void registerListener(){
    	aMap.setOnMapClickListener(PlaningActivity.this);
    	aMap.setOnMarkerClickListener(PlaningActivity.this);
    	aMap.setOnInfoWindowClickListener(PlaningActivity.this);
    	aMap.setInfoWindowAdapter(PlaningActivity.this);
    }
    
    /**
     * 方法必须重写
     */
    @Override
    protected void onResume(){
    	super.onResume();
    	mapView.onResume();
		if(Utils.isNetworkAvailable(this)){
			initialise();
		}else{
			Utils.showDialogTip(4,PlaningActivity.this);
		}

    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onPause(){
    	super.onPause();
    	mapView.onPause();
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState){
    	super.onSaveInstanceState(outState);
    	mapView.onSaveInstanceState(outState);
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	mapView.onDestroy();
    }
    
	@Override
	public void onClick(View v) {
		/**
		 * 根据用户输入地址转换经纬度
		 */
		switch(v.getId()){	
		case R.id.imagebtn_roadsearch_tab_transit:
			//初始化
			ScaleAnimation scaleAnimation = new ScaleAnimation(1,2.0f,1,2.0f,
					Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            //设置动画时间
			scaleAnimation.setDuration(500);
			transitBtn.startAnimation(scaleAnimation);
			String number="";
			String Startpoint1=editpoint_et.getText().toString();
			String Endpoint1= editdestination_et.getText().toString();
			number=routePanning(Startpoint1);
			if(number!=null && !Startpoint1.equals("")){
				String r[]=number.split(",");
				String number2=routePanning(Endpoint1);
				if(number2!=null && !Endpoint1.equals("")){
					String r1[]=number2.split(",");
					startPoint = new LatLonPoint(Double.parseDouble(r[2]),Double.parseDouble(r[1]));
					endPoint = new LatLonPoint(Double.parseDouble(r1[2]), Double.parseDouble(r1[1]));
					final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
					BusRouteQuery query = new BusRouteQuery(fromAndTo, busMode, r[0], 1);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
					
					//showToast("未知错误，请稍后重试!错误码为" +mode);
					routeSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询								
				}else{
					showToast("终点无效,请重新输入");
				}				
			}else{
				showToast("起点无效,请重新输入");
			}		
			break;
		case R.id.imagebtn_roadsearch_tab_driving:
			ScaleAnimation scaleAnimationdriving = new ScaleAnimation(1,2.0f,1,2.0f,
					Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
			//设置动画时间
			scaleAnimationdriving.setDuration(500);
			drivingBtn.startAnimation(scaleAnimationdriving);
            String number3="";
            String Startpoint2=editpoint_et.getText().toString();
            String Endpoint2= editdestination_et.getText().toString();
            number3=routePanning(Startpoint2);
            if(number3!=null && !Startpoint2.equals("")){
				String r2[] = number3.split(",");
				String number4 = routePanning(Endpoint2);
				if (number4 != null && !Endpoint2.equals("")) {
					String r3[] = number4.split(",");
					startPoint = new LatLonPoint(Double.parseDouble(r2[2]),
							Double.parseDouble(r2[1]));
					endPoint = new LatLonPoint(Double.parseDouble(r3[2]),
							Double.parseDouble(r3[1]));
					final RouteSearch.FromAndTo fromAndTo1 = new RouteSearch.FromAndTo(
							startPoint, endPoint);
					DriveRouteQuery query1 = new DriveRouteQuery(fromAndTo1,
							drivingMode, null, null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路

					// showToast("未知错误，请稍后重试!错误码为" +mode);
					routeSearch.calculateDriveRouteAsyn(query1);// 异步路径规划公交模式查询

				} else {
					showToast("终点无效,请重新输入");
				}

			} else {
				showToast("起点无效,请重新输入");
			}	
			break;
		case R.id.imagebtn_roadsearch_tab_walk:
			ScaleAnimation scaleAnimationwalk = new ScaleAnimation(1,2.0f,1,2.0f,
					Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
			//设置动画时间
			scaleAnimationwalk .setDuration(500);
			walkBtn.startAnimation(scaleAnimationwalk);
			String number5 = "";
			String Startpoint3 = editpoint_et.getText().toString();
			String Endpoint3 = editdestination_et.getText().toString();
			number5 = routePanning(Startpoint3);
			if (number5 != null && !Startpoint3.equals("")) {
				String r4[] = number5.split(",");
				String number6 = routePanning(Endpoint3);
				if (number6 != null && !Endpoint3.equals("")) {
					String r5[] = number6.split(",");
					startPoint = new LatLonPoint(Double.parseDouble(r4[2]),
							Double.parseDouble(r4[1]));
					endPoint = new LatLonPoint(Double.parseDouble(r5[2]),
							Double.parseDouble(r5[1]));
					final RouteSearch.FromAndTo fromAndTo2 = new RouteSearch.FromAndTo(
							startPoint, endPoint);
					WalkRouteQuery query2 = new WalkRouteQuery(fromAndTo2,
							walkMode);

					// showToast("未知错误，请稍后重试!错误码为" +mode);
					routeSearch.calculateWalkRouteAsyn(query2);// 异步路径规划公交模式查询

				} else {
					showToast("终点无效,请重新输入");
				}

			} else {
				showToast("起点无效,请重新输入");
			}
			break;
			case R.id.planningpoint_et:
				Intent intentpoint = new Intent(getApplicationContext(), SearchTipActivity.class);
				intentpoint.putExtra("pointordestion","point");
				startActivity(intentpoint);
				break;
			case R.id.planningdestion_et:
				Intent intentdestion = new Intent(getApplicationContext(), SearchTipActivity.class);
				intentdestion.putExtra("pointordestion","destion");
				startActivity(intentdestion);
				break;
			case R.id.planningexchange_position:
				Animation animation = new RotateAnimation(0f,180f,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
				animation.setDuration(250);
				animation.setFillAfter(true);//设置为true，动画转化结束后被应用
                planningexchange_position.startAnimation(animation);//开始动画
				String p_value = editpoint_et.getText().toString();
				String d_value = editdestination_et.getText().toString();
				editpoint_et.setText(d_value);
				editdestination_et.setText(p_value);
				break;

	
		
		}
		
		
	}
	public void ongasClick(View view) {
		searchRoutePOI(RoutePOISearchType.TypeGasStation);
		gasbtn.setTextColor(Color.BLUE);
		ATMbtn.setTextColor(Color.GRAY);
		Maibtn.setTextColor(Color.GRAY);
		Toibtn.setTextColor(Color.GRAY);
	}

	public void onATMClick(View view) {
		searchRoutePOI(RoutePOISearchType.TypeATM);
		gasbtn.setTextColor(Color.GRAY);
		ATMbtn.setTextColor(Color.BLUE);
		Maibtn.setTextColor(Color.GRAY);
		Toibtn.setTextColor(Color.GRAY);
	}

	public void onMaiClick(View view) {
		searchRoutePOI(RoutePOISearchType.TypeMaintenanceStation);
		gasbtn.setTextColor(Color.GRAY);
		ATMbtn.setTextColor(Color.GRAY);
		Maibtn.setTextColor(Color.BLUE);
		Toibtn.setTextColor(Color.GRAY);
	}
	
	public void onToiClick(View view) {
		searchRoutePOI(RoutePOISearchType.TypeToilet);
		gasbtn.setTextColor(Color.GRAY);
		ATMbtn.setTextColor(Color.GRAY);
		Maibtn.setTextColor(Color.GRAY);
		Toibtn.setTextColor(Color.BLUE);
	}
	private void searchRoutePOI(RoutePOISearchType type) {
		if (overlay != null) {
			overlay.removeFromMap();
		}
		RoutePOISearchQuery query = new RoutePOISearchQuery(startPoint,endPoint,1, type, 400);
		//showToast("未知错误，请稍后重试!错误码为" +mode);
		
		final RoutePOISearch search = new RoutePOISearch(this, query);
		search.setPoiSearchListener(this);
		search.searchRoutePOIAsyn();
		
	}

	@Override
	public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnUpdateTrafficFacility(TrafficFacilityInfo arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideCross() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideLaneInfo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyParallelRoad(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onArriveDestination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onArriveDestination(NaviStaticInfo arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onArriveDestination(AMapNaviStaticInfo arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onArrivedWayPoint(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCalculateMultipleRoutesSuccess(int[] arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCalculateRouteFailure(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCalculateRouteSuccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEndEmulatorNavi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetNavigationText(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGpsOpenStatus(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInitNaviFailure() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInitNaviSuccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChange(AMapNaviLocation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNaviInfoUpdate(NaviInfo arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNaviInfoUpdated(AMapNaviInfo arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReCalculateRouteForTrafficJam() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReCalculateRouteForYaw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartNavi(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTrafficStatusUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showCross(AMapNaviCross arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showLaneInfo(AMapLaneInfo[] arg0, byte[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAimlessModeStatistics(AimLessModeStat arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBusRouteSearched(BusRouteResult result, int rCode) {
		if (rCode == 1000) {
			if (result != null && result.getPaths() != null && result.getPaths().size() > 0) {
				busRouteResult = result;
				BusPath busPath = busRouteResult.getPaths().get(0);
				aMap.clear();// 清理地图上的所有覆盖物
				BusRouteOverlay routeOverlay = new BusRouteOverlay(this, aMap, busPath, busRouteResult.getStartPos(), busRouteResult.getTargetPos());
				routeOverlay.removeFromMap();
				routeOverlay.addToMap();
				routeOverlay.zoomToSpan();
			} else {
				showToast("对不起，没有搜索到相关数据！");
			}
		} else if (rCode == 27) {
			showToast("搜索失败,请检查网络连接！");
		} else if (rCode == 32) {
			showToast("key验证无效！");
		} else {
			showToast("未知错误，请稍后重试!错误码为" + rCode);
		}
		
	}

	@Override
	public void onDriveRouteSearched(DriveRouteResult result, int  rCode) {
		if (rCode == 1000) {
			if (result != null && result.getPaths() != null && result.getPaths().size() > 0) {
				driveRouteResult = result;
				DrivePath drivePath = driveRouteResult.getPaths().get(0);
				aMap.clear();// 清理地图上的所有覆盖物
				DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(this, aMap, drivePath, driveRouteResult.getStartPos(), driveRouteResult.getTargetPos());
				drivingRouteOverlay.removeFromMap();
				drivingRouteOverlay.addToMap();
				drivingRouteOverlay.zoomToSpan();
			} else {
				showToast("对不起，没有搜索到相关数据！");
			}
		} else if (rCode == 27) {
			showToast("搜索失败,请检查网络连接！");
		} else if (rCode == 32) {
			showToast("key验证无效！");
		} else {
			showToast("未知错误，请稍后重试!错误码为" + rCode);
		}
		
	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult result, int rCode) {
		if (rCode == 1000) {
			if (result != null && result.getPaths() != null && result.getPaths().size() > 0) {
				walkRouteResult = result;
				WalkPath walkPath = walkRouteResult.getPaths().get(0);
				aMap.clear();// 清理地图上的所有覆盖物
				WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(this, aMap, walkPath, walkRouteResult.getStartPos(), walkRouteResult.getTargetPos());
				walkRouteOverlay.removeFromMap();
				walkRouteOverlay.addToMap();
				walkRouteOverlay.zoomToSpan();
			} else {
				showToast("对不起，没有搜索到相关数据！");
			}
		} else if (rCode == 27) {
			showToast("搜索失败,请检查网络连接！");
		} else if (rCode == 32) {
			showToast("key验证无效！");
		} else {
			showToast("未知错误，请稍后重试!错误码为" + rCode);
		}
		
	}
	/**
	 * toast封装
	 *  @param str
	 */
	private void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
		
	}
	@SuppressLint("NewApi")
	public static void closeStrictMode() {
	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
	.detectAll().penaltyLog().build());
	}
	/**
	 * 路线规划
	 */
    public String routePanning(String s){
    	String Startp="";
		String Startpoint=s;
		try {  
            Startp = URLEncoder.encode( Startpoint, "UTF-8");  
        } catch (UnsupportedEncodingException e1) {  
  
            e1.printStackTrace();  
        }  
		String url = String .format("http://restapi.amap.com/v3/geocode/geo?key=%s&address=%s", KEY, Startp); 
		/**
		 * 起点转换经纬度
		 */	
		String Sp = HttpUtils.getJsonContent(url,"UTF-8");
		String data="";
		try {
			data=HttpUtils.jsonToObj(Sp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return data;
    }
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		 if (amapLocation != null) {
	            if (amapLocation.getErrorCode() == 0) {
	                //定位成功回调信息，设置相关消息
	                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
	                amapLocation.getLatitude();//获取纬度
	                amapLocation.getLongitude();//获取经度
	                amapLocation.getAccuracy();//获取精度信息
	                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                Date date = new Date(amapLocation.getTime());
	                df.format(date);//定位时间
	                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
	                amapLocation.getCountry();//国家信息
	                amapLocation.getProvince();//省信息
	                amapLocation.getCity();//城市信息
	                amapLocation.getDistrict();//城区信息
	                amapLocation.getStreet();//街道信息
	                amapLocation.getStreetNum();//街道门牌号信息
	                amapLocation.getCityCode();//城市编码
	                amapLocation.getAdCode();//地区编码

	                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
	                if (isFirstLoc) {
	                    //设置缩放级别
	                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
	                    //将地图移动到定位点
	                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
	                    //点击定位按钮 能够将地图的中心移动到定位点
	                    mListener.onLocationChanged(amapLocation);
	                    //添加图钉
	                    aMap.addMarker(getMarkerOptions(amapLocation));
	                    //获取定位信息
	                    buffer = new StringBuffer();
	                    buffer.append(amapLocation.getCity()+ "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
	                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
	                    editpoint_et.setText(buffer.toString());
	                    isFirstLoc = false;
	                }


	            } else {
	                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
	                Log.e("AmapError", "location Error, ErrCode:"
	                        + amapLocation.getErrorCode() + ", errInfo:"
	                        + amapLocation.getErrorInfo());

	               // Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
					Utils.showDialogTip(amapLocation.getErrorCode(),PlaningActivity.this);
	            }
	        }
		
	}
	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		 mListener = listener;
		
	}
	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener=null;
		
	}
	//自定义一个图钉，并且设置图标，当我们点击图钉时，显示设置的信息
    private MarkerOptions getMarkerOptions(AMapLocation amapLocation) {
         //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        
        //位置
        options.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
        StringBuffer buffer = new StringBuffer();
        buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() +  "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
        //标题
        options.title(buffer.toString());     
        //设置多少帧刷新一次图片资源
        options.period(60);
        return options;

    }
	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void onRoutePoiSearched(RoutePOISearchResult result, int errorCode) {
		if (errorCode == 1000) {
			if(result != null){
				List<RoutePOIItem> items = result.getRoutePois();
				if (items != null && items.size() > 0) {
					if (overlay != null) {
						overlay.removeFromMap();
					}
					overlay = new myRoutePoiOverlay(aMap, items);
					overlay.addToMap();
				} else {
					Toast.makeText(PlaningActivity.this,"对不起没有搜索相关数据",Toast.LENGTH_SHORT).show();
				}
			}
		}else{
			//Toast.makeText(PlaningActivity.this, "错误码："+errorCode,Toast.LENGTH_LONG).show();
			Toast.makeText(PlaningActivity.this, "请先规划路线后再搜索",Toast.LENGTH_LONG).show();
		}
		
	}
	/**
	 * 自定义PoiOverlay
	 *
	 */
	
	private class myRoutePoiOverlay {
		private AMap mamap;
		private List<RoutePOIItem> mPois;
	    private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();
		public myRoutePoiOverlay(AMap amap ,List<RoutePOIItem> pois) {
			mamap = amap;
	        mPois = pois;
		}

	    /**
	     * 添加Marker到地图中。
	     * @since V2.1.0
	     */
	    public void addToMap() {
	        for (int i = 0; i < mPois.size(); i++) {
	            Marker marker = mamap.addMarker(getMarkerOptions(i));
	            RoutePOIItem item = mPois.get(i);
				marker.setObject(item);
	            mPoiMarks.add(marker);
	        }
	    }

	    /**
	     * 去掉PoiOverlay上所有的Marker。
	     *
	     * @since V2.1.0
	     */
	    public void removeFromMap() {
	        for (Marker mark : mPoiMarks) {
	            mark.remove();
	        }
	    }

	    /**
	     * 移动镜头到当前的视角。
	     * @since V2.1.0
	     */
	    public void zoomToSpan() {
	        if (mPois != null && mPois.size() > 0) {
	            if (mamap == null)
	                return;
	            LatLngBounds bounds = getLatLngBounds();
	            mamap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
	        }
	    }

	    private LatLngBounds getLatLngBounds() {
	        LatLngBounds.Builder b = LatLngBounds.builder();
	        for (int i = 0; i < mPois.size(); i++) {
	            b.include(new LatLng(mPois.get(i).getPoint().getLatitude(),
	                    mPois.get(i).getPoint().getLongitude()));
	        }
	        return b.build();
	    }

	    private MarkerOptions getMarkerOptions(int index) {
	        return new MarkerOptions()
	                .position(
	                        new LatLng(mPois.get(index).getPoint()
	                                .getLatitude(), mPois.get(index)
	                                .getPoint().getLongitude()))
	                .title(getTitle(index)).snippet(getSnippet(index));
	    }

	    protected String getTitle(int index) {
	        return mPois.get(index).getTitle();
	    }

	    protected String getSnippet(int index) {
	        return mPois.get(index).getDistance() + "米  " + mPois.get(index).getDuration() + "秒";
	    }

	    /**
	     * 从marker中得到poi在list的位置。
	     *
	     * @param marker 一个标记的对象。
	     * @return 返回该marker对应的poi在list的位置。
	     * @since V2.1.0
	     */
	    public int getPoiIndex(Marker marker) {
	        for (int i = 0; i < mPoiMarks.size(); i++) {
	            if (mPoiMarks.get(i).equals(marker)) {
	                return i;
	            }
	        }
	        return -1;
	    }

	    /**
	     * 返回第index的poi的信息。
	     * @param index 第几个poi。
	     * @return poi的信息。poi对象详见搜索服务模块的基础核心包（com.amap.api.services.core）中的类 <strong><a href="../../../../../../Search/com/amap/api/services/core/PoiItem.html" title="com.amap.api.services.core中的类">PoiItem</a></strong>。
	     * @since V2.1.0
	     */
	    public RoutePOIItem getPoiItem(int index) {
	        if (index < 0 || index >= mPois.size()) {
	            return null;
	        }
	        return mPois.get(index);
	    }
	}
	@Override
	public void onRideRouteSearched(RideRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


}
