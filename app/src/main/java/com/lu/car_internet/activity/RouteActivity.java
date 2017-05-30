package com.lu.car_internet.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.CompoundButton.OnCheckedChangeListener;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviStaticInfo;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.WalkRouteResult;
import com.autonavi.tbt.NaviStaticInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.lu.car_internet.R;
import com.lu.car_internet.utils.HttpUtils;
import com.lu.car_internet.utils.Utils;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class RouteActivity extends Activity implements OnClickListener, OnRouteSearchListener,AMapNaviListener, OnCheckedChangeListener, LocationSource, 
AMapLocationListener,TextWatcher,InputtipsListener {
	private static String KEY = "6965eced61a69e02464c5e31ccf0063a";
	private AMap aMap;
	private MapView mapView;
    private boolean congestion,cost,hightspeed,avoidhightspeed;
	private AMapNavi mAMapNavi;
	/**
	 * 地图对象
	 */
	  //定位发起端
	  public  AMapLocationClient mLocationClient=null;
	  //在主线程获得地图对象AMap，b并设置定位监听且实现LocationSource接口
	  //定位监听器
	  private OnLocationChangedListener mListener;
	  //定位参数
	  private AMapLocationClientOption mLocationOption=null;
	  //标识
	  private boolean isFirstLoc=true;
//--
	private Marker mStartMarker;
	private Marker mEndMarker;
	/**
	 * 选择起点Action标志位
	 */
	//private boolean mapClickStartReady;
    /**
     * 选择终点Action标志位
     */
	//private boolean mapClickEndReady;
	private NaviLatLng endLatlng = new NaviLatLng(39.955846, 116.352765);
	private NaviLatLng startLatlng = new NaviLatLng(39.925041, 116.437901);
	private List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
	/**
	 * 途径点坐标集合
	 */
	private List<NaviLatLng> wayList = new ArrayList<NaviLatLng>();
	/**
	 * 终点坐标集合
	 */
	private List<NaviLatLng> endList = new ArrayList<NaviLatLng>();
	/**
	 * 保存当前算好的路线
	 */
	private SparseArray<RouteOverLay> routeOverlays=new SparseArray<RouteOverLay>();
	/**
	 * 当前用户选中的路线,在下个页面进行导航
	 */
	private int routeIndex;
	/**
	 * 路线的权值,重合路线情况下,权值高的的路线会覆盖权值低的路线
	 */
	private int zindex=1;
	/**
	 * 路线计算成功的标志位
	 */
	private boolean calculateSuccess=false;
	private boolean chooseRouteSuccess=false;

	/**
	 * 输入起点，终点框
	 */

    /**
     * 搜索按钮
     */
    //private Button query;
	private RouteSearch routeSearch;
    private String city = "";
  //  private Button selectroute;
 //   private Button gpsnavi;
 //   private Button emulatornavi;
	private TextView point_et;
	private TextView destion_et;
	private ImageView exchange_position;
	private RelativeLayout search_rl;
	private RelativeLayout simulation_rl;
	private RelativeLayout actual_rl;
	private RelativeLayout navi_bottom;
	private ImageButton choose_route;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route);
		ActivityCompat.requestPermissions(RouteActivity.this, new String[]{
				Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
		mapView = (MapView) findViewById(R.id.route_map);
		mapView.onCreate(savedInstanceState);


	}
	private void setup(){
		closeStrictMode();
		CheckBox congestion = (CheckBox) findViewById(R.id.congestion);
		CheckBox cost = (CheckBox) findViewById(R.id.cost);
		CheckBox hightspeed = (CheckBox) findViewById(R.id.hightspeed);
		CheckBox avoidhightspeed = (CheckBox) findViewById(R.id.avoidhightspeed);
	//	selectroute = (Button) findViewById(R.id.selectroute);//选路径
		point_et = (TextView)findViewById(R.id.point_et);
		destion_et =(TextView)findViewById(R.id.destion_et);
		exchange_position = (ImageView)findViewById(R.id.exchange_position);
		search_rl = (RelativeLayout)findViewById(R.id.search_rl);
		simulation_rl = (RelativeLayout)findViewById(R.id.simulation_rl);
		actual_rl = (RelativeLayout)findViewById(R.id.actual_rl);
		navi_bottom = (RelativeLayout)findViewById(R.id.navi_bottom);
		choose_route = (ImageButton )findViewById(R.id.choose_route);
		choose_route.setOnClickListener(this);
		simulation_rl.setOnClickListener(this);
		actual_rl.setOnClickListener(this);
		search_rl.setOnClickListener(this);
		exchange_position.setOnClickListener(this);
		SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
		String pointvalue=sPreferences.getString("point", "");
		if(!pointvalue.equals("")){
			point_et.setText(pointvalue);
		}
		String destvalue  = sPreferences.getString("destion", "");
		if(!destvalue.equals("")){
			destion_et.setText(destvalue);
		}
        point_et.setOnClickListener(this);
		destion_et.setOnClickListener(this);
	//	selectroute.setOnClickListener(this);
		congestion.setOnCheckedChangeListener(this);
		cost.setOnCheckedChangeListener(this);
		hightspeed.setOnCheckedChangeListener( this);
		avoidhightspeed.setOnCheckedChangeListener(this);
		// query=(Button) findViewById(R.id.query_route);
		routeSearch = new RouteSearch(this);
		routeSearch.setRouteSearchListener(this);
		mAMapNavi = AMapNavi.getInstance(getApplicationContext());
		if(mAMapNavi==null){
			Toast.makeText(getApplicationContext(), "起点输入无效,请重新输入",
					Toast.LENGTH_LONG).show();
		}
		//mAMapNavi.setAMapNaviListener(this);
		mAMapNavi.addAMapNaviListener(this);
		aMap = mapView.getMap();
		mStartMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.start))));
		mEndMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.end))));
		UiSettings settings=aMap.getUiSettings();
		aMap.setLocationSource(this);
		settings.setMyLocationButtonEnabled(true);
		aMap.setMyLocationEnabled(true);

	}



	 private void initLoc(){
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
        mLocationOption.setOnceLocation(true);
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
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if(Utils.isNetworkAvailable(this)){
			setup();
		}else{

		}
		initLoc();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		startList.clear();
	    wayList.clear();
	    endList.clear();
	    routeOverlays.clear();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
		 /**
         * 当前页面只是展示地图，activity销毁后不需要再回调导航的状态
         */
		if(mAMapNavi != null) {
			mAMapNavi.removeAMapNaviListener(this);
		}
        //注意：不要调用这个destory方法，因为在当前页面进行算路，算路成功的数据全部存在此对象中。到另外一个activity中只需要开始导航即可。
        //如果用户是回退退出当前activity，可以调用下面的destory方法。
        //mAMapNavi.destroy();
	}


	@Override
	public void onClick(View v) {
		/**
		 * 根据用户输入地址转换经纬度
		 */

		switch(v.getId()){
			case R.id.choose_route:
			  changeRoute();
			break;
		    case R.id.simulation_rl:
			if (!calculateSuccess) {
				Toast.makeText(this, "请先算路", Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent(getApplicationContext(), RouteNaviActivity.class);
				intent.putExtra("gps", false);
				startActivity(intent);
				this.finish();
			}
			break;
		case R.id.actual_rl:
			if (!calculateSuccess) {
				Toast.makeText(this, "请先算路", Toast.LENGTH_SHORT).show();
			} else {
				Intent gpsintent = new Intent(getApplicationContext(), RouteNaviActivity.class);
				gpsintent.putExtra("gps", true);
				startActivity(gpsintent);
				this.finish();
			}
			break;
//        case R.id.gpsnavi:
//        	if (!calculateSuccess) {
// 	            Toast.makeText(this, "请先算路", Toast.LENGTH_SHORT).show();
// 	        }else{
// 	        	Intent gpsintent = new Intent(getApplicationContext(), RouteNaviActivity.class);
// 	            gpsintent.putExtra("gps", true);
// 	            startActivity(gpsintent);
// 	            this.finish();
// 	        }
//            break;
//        case R.id.emulatornavi:
//        	if (!calculateSuccess) {
// 	            Toast.makeText(this, "请先算路", Toast.LENGTH_SHORT).show();
// 	        }else{
// 	       	    Intent intent = new Intent(getApplicationContext(), RouteNaviActivity.class);
//                intent.putExtra("gps", false);
//                startActivity(intent);
//                this.finish();
// 	        }
//
//            break;
            case R.id.point_et:
                Intent intentpoint = new Intent(getApplicationContext(), SearchTipActivity.class);
				intentpoint.putExtra("pointordestion","point");
                startActivity(intentpoint);
                break;
			case R.id.destion_et:
				Intent intentdestion = new Intent(getApplicationContext(), SearchTipActivity.class);
				intentdestion.putExtra("pointordestion","destion");
				startActivity(intentdestion);
				break;
			case R.id.exchange_position:
				String p_value = point_et.getText().toString();
				String d_value = destion_et.getText().toString();
				point_et.setText(d_value);
				destion_et.setText(p_value);
				Animation animation = new RotateAnimation(0f,180f,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
				animation.setDuration(250);
				animation.setFillAfter(true);//设置为true，动画转化结束后被应用
				exchange_position.startAnimation(animation);//开始动画
                break;
			case R.id.search_rl:
				clearRoute();
				// mapClickStartReady = false;
				// mapClickEndReady = false;
				if (avoidhightspeed && hightspeed) {
					Toast.makeText(getApplicationContext(), "不走高速与高速优先不能同时为true.", Toast.LENGTH_LONG).show();
				}
				if (cost && hightspeed) {
					Toast.makeText(getApplicationContext(), "高速优先与避免收费不能同时为true.", Toast.LENGTH_LONG).show();
				}
        /*
		 * strategyFlag转换出来的值都对应PathPlanningStrategy常量，用户也可以直接传入PathPlanningStrategy常量进行算路。
		 * 如:mAMapNavi.calculateDriveRoute(mStartList, mEndList, mWayPointList,PathPlanningStrategy.DRIVING_DEFAULT);
		 */
				int strategyFlag = 0;
				try {
					strategyFlag = mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (strategyFlag >= 0) {
					//			String number = "";
					String Startpoint1 = point_et.getText().toString();
					String Endpoint1 = destion_et.getText().toString();
					String number = routePanning(Startpoint1);
//				Toast.makeText(getApplicationContext(), number,
//						Toast.LENGTH_LONG).show();
					if (number == null||Startpoint1.equals("")) {
						Toast.makeText(getApplicationContext(), "起点输入无效,请重新输入",
								Toast.LENGTH_LONG).show();
					} else if(number.length()>0){

//					Toast.makeText(getApplicationContext(), number,
//							Toast.LENGTH_LONG).show();
						String r[] = number.split(",");
						String number2 = routePanning(Endpoint1);
						if (number2 == null||Endpoint1.equals("")) {
							Toast.makeText(getApplicationContext(), "终点输入无效,请重新输入",
									Toast.LENGTH_LONG).show();

						} else if(number2.length()>0){
							String r1[] = number2.split(",");
							startLatlng = new NaviLatLng(Double.parseDouble(r[2]),
									Double.parseDouble(r[1]));
							endLatlng = new NaviLatLng(Double.parseDouble(r1[2]),
									Double.parseDouble(r1[1]));
							startList.clear();
							startList.add(startLatlng);
							endList.clear();
							endList.add(endLatlng);

							mAMapNavi.calculateDriveRoute(startList, endList,
									wayList, strategyFlag);
//				        mAMapNavi.calculateDriveRoute(startList, endList,
//											wayList, strategyFlag);
//							Toast.makeText(getApplicationContext(),
//									"策略:" + Double.parseDouble(r[2]), Toast.LENGTH_LONG).show();
//

						}

					}
				}
				break;
        default:
            break;
    }
}

	@Override
	public void onBusRouteSearched(BusRouteResult result, int rCode) {

	}

	@Override
	public void onDriveRouteSearched(DriveRouteResult result, int rCode) {

	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult result, int rCode) {

	}

	/**
	 * toast封装
	 *
	 * @param str
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
	public void onCalculateMultipleRoutesSuccess(int[] ints) {
		//清空上次计算的路径列表。
        routeOverlays.clear();
        HashMap<Integer, AMapNaviPath> paths = mAMapNavi.getNaviPaths();
        for (int i = 0; i < ints.length; i++) {
            AMapNaviPath path = paths.get(ints[i]);
            if (path != null) {
                drawRoutes(ints[i], path);
            }
        }

	}

	@Override
	public void onCalculateRouteFailure(int arg0) {
		 calculateSuccess = false;
	     Toast.makeText(getApplicationContext(), "计算路线失败，errorcode＝" + arg0, Toast.LENGTH_SHORT).show();

	}
	 private void drawRoutes(int routeId, AMapNaviPath path) {
	        calculateSuccess = true;
	        aMap.moveCamera(CameraUpdateFactory.changeTilt(0));
	        RouteOverLay routeOverLay = new RouteOverLay(aMap, path, this);
	        routeOverLay.setTrafficLine(true);
	        routeOverLay.addToMap();
	        routeOverlays.put(routeId, routeOverLay);
	    }
	 public void changeRoute() {
	        if (!calculateSuccess) {
	            Toast.makeText(this, "请先算路", Toast.LENGTH_SHORT).show();
	            return;
	        }
	        /**
	         * 计算出来的路径只有一条
	         */
	        if (routeOverlays.size() == 1) {
	            chooseRouteSuccess = true;
	            Toast.makeText(this, "导航距离:" + (mAMapNavi.getNaviPath()).getAllLength() + "m" + "\n" + "导航时间:" + (mAMapNavi.getNaviPath()).getAllTime() + "s", Toast.LENGTH_SHORT).show();
	            return;
	        }

	        if (routeIndex >= routeOverlays.size())
	            routeIndex = 0;
	        int routeID = routeOverlays.keyAt(routeIndex);
	        //突出选择的那条路
	        for (int i = 0; i < routeOverlays.size(); i++) {
	            int key = routeOverlays.keyAt(i);
	            routeOverlays.get(key).setTransparency(0.7f);
	        }
	        routeOverlays.get(routeID).setTransparency(0);
	        /**把用户选择的那条路的权值弄高，使路线高亮显示的同时，重合路段不会变的透明**/
	        routeOverlays.get(routeID).setZindex(zindex++);

	        //必须告诉AMapNavi 你最后选择的哪条路
	        mAMapNavi.selectRouteId(routeID);
	        Toast.makeText(this, "导航距离:" + (mAMapNavi.getNaviPaths()).get(routeID).getStrategy()+ "\n" + "导航时间:" + (mAMapNavi.getNaviPaths()).get(routeID).getAllTime() + "s", Toast.LENGTH_SHORT).show();
	        routeIndex++;

	        chooseRouteSuccess = true;
	    }
	   /**
	     * 清除当前地图上算好的路线
	     */
	    private void clearRoute() {
	        for (int i = 0; i < routeOverlays.size(); i++) {
	            RouteOverLay routeOverlay = routeOverlays.valueAt(i);
	            routeOverlay.removeFromMap();
	        }
	        routeOverlays.clear();
	    }

	@Override
	public void onCalculateRouteSuccess() {
		 /**
         * 清空上次计算的路径列表。
         */
        routeOverlays.clear();
        AMapNaviPath path = mAMapNavi.getNaviPath();
        /**
         * 单路径不需要进行路径选择，直接传入－1即可
         */
        drawRoutes(-1, path);
        Toast.makeText(this, "导航距离:", Toast.LENGTH_SHORT).show();
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

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		int id = buttonView.getId();
        switch (id){
            case R.id.congestion:
                congestion = isChecked;
                break;
            case R.id.avoidhightspeed:
                avoidhightspeed = isChecked;
                break;
            case R.id.cost:
                cost = isChecked;
                break;
            case R.id.hightspeed:
                hightspeed = isChecked;
                break;
            default:
                break;
        }

	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		  mListener = listener;

	}

	@Override
	public void deactivate() {
		mListener=null;

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
	                    StringBuffer buffer = new StringBuffer();
	                    buffer.append(amapLocation.getCountry()+ "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());


						point_et.setText(buffer.toString());

						isFirstLoc = false;
	                }
	            } else {
	                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//	                Log.e("AmapError", "location Error, ErrCode:"
//	                        + amapLocation.getErrorCode() + ", errInfo:"
//	                        + amapLocation.getErrorInfo());
//	                Toast.makeText(getApplicationContext(), "定位失败"+amapLocation.getErrorCode(), Toast.LENGTH_LONG).show();
//					SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
//					boolean isshowdialog = sPreferences.getBoolean("isshowdialog", true);
//					SharedPreferences.Editor editor = sPreferences.edit();
				//		Utils.showDialogTip(amapLocation.getErrorCode(),RouteActivity.this);
	            }
	        }
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
	public void onRideRouteSearched(RideRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onGetInputtips(List<Tip>tipList, int rCode) {

}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
	}



}
