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
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;


import com.amap.api.maps.UiSettings;


import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
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
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.autonavi.tbt.NaviStaticInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.lu.car_internet.R;
import com.lu.car_internet.utils.HttpUtils;
import com.lu.car_internet.utils.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity implements OnClickListener,LocationSource,AMapLocationListener,
OnMarkerClickListener, OnInfoWindowClickListener,InfoWindowAdapter,OnMapClickListener,OnPoiSearchListener,AMapNaviListener {
	 private static String KEY = "6965eced61a69e02464c5e31ccf0063a"; 
	//定位发起端
	  public  AMapLocationClient mLocationClient=null;  
	  private MapView mMapView = null;
	  //在主线程获得地图对象AMap，b并设置定位监听且实现LocationSource接口
	  private AMap aMap;
	  //定位监听器
	  private OnLocationChangedListener mListener;
	  private Marker mStartMarker;
	  private Marker mEndMarker;
	  //定位参数
	  private AMapLocationClientOption mLocationOption=null;
	  //标识
	  private boolean isFirstLoc=true;
//	  private int i=1;
//	  private int j=0;
//	  private int s1=0;

//新增	
	  private double Latitude;
	  private double Longitude;
	  private PoiResult poiResult;//poi返回的结果
	  private int currentPage = 0;//当前页面,从0开始
	  private PoiSearch.Query query;//Poi查询条件类
	  private LatLonPoint lp=new LatLonPoint(39.993743, 116.472995);//当前点
	  private Marker locationMarker;//选择的点
	  private Marker detailMarker;
	  private Marker mlastMarker;
	  private PoiSearch poiSearch;
	  private myPoiOverlay poiOverlay;//图层
	  private List<PoiItem> poiItems;//poi数据
	  private RelativeLayout mPoiDetail;
	  private TextView mPoiName,mPoiAddress;
	  private String keyWord = "";
	  private TextView mSearchText;
	  private Button go_here;
//新增
	  private AMapNavi mAMapNavi;
	  private boolean mapClickStartReady;
	  private boolean mapClickEndReady;
	  private NaviLatLng endLatlng = new NaviLatLng(39.955846,116.352765);
	  private NaviLatLng startLatlng = new NaviLatLng(39.925041,116.437901);
	  private List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
	  private List<NaviLatLng> wayList = new ArrayList<NaviLatLng>();
	  private List<NaviLatLng> endList = new ArrayList<NaviLatLng>();
	  /**
	   * 保存当前算好的路线
	   */
	  private SparseArray<RouteOverLay> routeOverlays = new SparseArray<RouteOverLay>();
	  /**
	   * 当前用户选中的路线，在下个页面进行导航
	   */
	  private int routeIndex;
	  /**路线的权值，重合路线情况下，权值高的路线会覆盖权值低的路线**/
	  private int zindex = 1;
	  /**
	   * 路线计算成功标志位
	   */
	  private boolean calculateSuccess = false;
	  private boolean chooseRouteSuccess = false;
	  private String city = "";
	//  private ListView minputlist;
	  private String destlocation="";
	  private boolean congestion,cost,hightspeed,avoidhightspeed;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
		super.onCreate(savedInstanceState);
		ActivityCompat.requestPermissions(SearchActivity.this, new String[]{
				Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    	setContentView(R.layout.activity_search);
		mMapView =(MapView) findViewById(R.id.map_search);
		//在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
		mMapView.onCreate(savedInstanceState);

	      //  mAMapNavi.addAMapNaviListener(this);
    }
    private void initialise(){
		closeStrictMode();
		//获取地图位置
		aMap=mMapView.getMap();
		//设置显示定位按钮 并且可以点击
		go_here = (Button) findViewById(R.id.go_here);
		go_here.setOnClickListener(this);
		UiSettings settings=aMap.getUiSettings();
		aMap.setLocationSource(this);
		settings.setMyLocationButtonEnabled(true);
		aMap.setMyLocationEnabled(true);
		initLoc();
		aMap.setOnMapClickListener(this);
		aMap.setOnMarkerClickListener(this);
		aMap.setOnInfoWindowClickListener(this);
		aMap.setInfoWindowAdapter(this);
		TextView searchButton = (TextView) findViewById(R.id.btn_search);
		searchButton.setOnClickListener(this);
		locationMarker = aMap.addMarker(new MarkerOptions()
				.anchor(0.5f, 0.5f)
				.icon(BitmapDescriptorFactory
						.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.point4)))
				.position(new LatLng(lp.getLatitude(), lp.getLongitude())));
		locationMarker.showInfoWindow();
		setup();
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lp.getLatitude(), lp.getLongitude()), 14));
		//minputlist.setOnItemClickListener(new MyOnItemClickListener());
		mStartMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.start))));
		mEndMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.end))));
		mAMapNavi = AMapNavi.getInstance(getApplicationContext());
	}
//	  private class MyOnItemClickListener implements OnItemClickListener{
//
//	        @Override
//	        public void onItemClick(AdapterView<?> parent, View view, int position,
//	                long id) {
//	        	 HashMap<String,String> map=(HashMap<String,String>)minputlist.getItemAtPosition(position);
//	             String title=map.get("name");
//	             String content=map.get("address");
//				 i=0;
////	             Toast.makeText(getApplicationContext(),
////	                     "你选择了第"+position+"个Item，itemTitle的值是："+title+"itemContent的值是:"+content,
////	                     Toast.LENGTH_SHORT).show();
//	        //	List<String> t = new ArrayList<String>();
//			//	t.get(position);
//			//	mKeywordText.setText( t.get(position));
//
//
//	             mSearchText.setText(title);
//				 s1 =mSearchText.getText().toString().length();
//	             minputlist.setVisibility(View.INVISIBLE);
//
//	        }
//
//	    }
	private void initLoc(){  
    	//mContext = this.getApplicationContext();
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
	private void setup() {
		mPoiDetail = (RelativeLayout) findViewById(R.id.poi_detail);
		mPoiDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(PoiSearchActivity.this,
//						SearchDetailActivity.class);
//				intent.putExtra("poiitem", mPoi);
//				startActivity(intent);
				
			}
		});
		mPoiName = (TextView) findViewById(R.id.poi_name);
		mPoiAddress = (TextView) findViewById(R.id.poi_address);
		mSearchText = (TextView)findViewById(R.id.input_edittext);
		//minputlist = (ListView)findViewById(R.id.inputlist);
		mSearchText.setOnClickListener(this);
		SharedPreferences sPreferences=this.getSharedPreferences("config", MODE_PRIVATE);
		String pointvalue=sPreferences.getString("point", "");
		if(!pointvalue.equals("")){
			mSearchText.setText(pointvalue);
		}
//		mSearchText.addTextChangedListener(this);
		
	}
	/**
	 * 开始进行poi搜索
	 */
	protected void doSearchQuery(){
		keyWord = mSearchText.getText().toString().trim();
		currentPage = 0;
		query = new PoiSearch.Query(keyWord, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		query.setPageSize(20);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页
		if (lp != null) {
			poiSearch = new PoiSearch(this, query);
			poiSearch.setOnPoiSearchListener(this);
			poiSearch.setBound(new SearchBound(lp, 20000, true));//
			// 设置搜索区域为以lp点为圆心，其周围5000米范围
			poiSearch.searchPOIAsyn();// 异步搜索
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_search:
			//minputlist.setVisibility(View.INVISIBLE);
			doSearchQuery();
			break;
		case R.id.go_here:
			Intent gpsintent = new Intent(getApplicationContext(), RouteNaviActivity.class);
			gpsintent.putExtra("gps", false);
			startActivity(gpsintent);
			this.finish();
			break;
			case R.id.input_edittext:
			Intent intentpoint = new Intent(getApplicationContext(), SearchTipActivity.class);
			intentpoint.putExtra("pointordestion","point");
			startActivity(intentpoint);
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
		// TODO Auto-generated method stub
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
                    buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
                    Latitude = amapLocation.getLatitude();
                    Longitude = amapLocation.getLongitude();
                    city = amapLocation.getCity();
                    lp = new LatLonPoint(Latitude,Longitude);
                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
               // Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
			//	Utils.showDialogTip(amapLocation.getErrorCode(),SearchActivity.this);
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
        //子标题
        options.snippet("这里好火");
        //设置多少帧刷新一次图片资源
        options.period(60);
        return options;

    }
	  @Override
	  protected void onDestroy() {
	    super.onDestroy();
	    //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
	    mMapView.onDestroy();
	//    mAMapNavi.removeAMapNaviListener(this);
	  }
	 @Override
	 protected void onResume() {
	    super.onResume();
	    //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
	    mMapView.onResume();
		 if(Utils.isNetworkAvailable(this)){
			 initialise();
			 whetherToShowDetailInfo(false);
		 }else{
			 Utils.showDialogTip(4,SearchActivity.this);
		 }

	    }
	 @Override
	 protected void onPause() {
	    super.onPause();
	    //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
	    mMapView.onPause();
	    startList.clear();
        wayList.clear();
        endList.clear();
        routeOverlays.clear();
	    }
	 @Override
	 protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
	    mMapView.onSaveInstanceState(outState);
	  }

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		whetherToShowDetailInfo(false);
		if (mlastMarker != null) {
			resetlastmarker();
		}
		
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		if (marker.getObject() != null) {
			whetherToShowDetailInfo(true);
			try {
				PoiItem mCurrentPoi = (PoiItem) marker.getObject();
				if (mlastMarker == null) {
					mlastMarker = marker;
				} else {
					// 将之前被点击的marker置为原来的状态
					resetlastmarker();
					mlastMarker = marker;
				}
				detailMarker = marker;
				detailMarker.setIcon(BitmapDescriptorFactory
									.fromBitmap(BitmapFactory.decodeResource(
											getResources(),
											R.drawable.poi_marker_pressed)));

				setPoiItemDisplayContent(mCurrentPoi);
				clearRoute();
				int strategyFlag = 0;
				try {
					strategyFlag = mAMapNavi.strategyConvert(congestion,
							avoidhightspeed, cost, hightspeed, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String number = routePanning(destlocation);
				String r[] = number.split(",");
				endLatlng = new NaviLatLng(Double.parseDouble(r[2]),
						Double.parseDouble(r[1]));
				startLatlng = new NaviLatLng(Latitude,
						Longitude);
				startList.clear();
				startList.add(startLatlng);
				endList.clear();
				endList.add(endLatlng);
				mAMapNavi.calculateDriveRoute(startList, endList,
						wayList, strategyFlag);
				Toast.makeText(getApplicationContext(),
						"策略:" + strategyFlag, Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else {
			whetherToShowDetailInfo(false);
			resetlastmarker();
		}


		return true;
	
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
	public void onPoiItemSearched(PoiItem arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPoiSearched(PoiResult result, int rcode) {
		// TODO Auto-generated method stub
		if (rcode == AMapException.CODE_AMAP_SUCCESS) {
			if (result != null && result.getQuery() != null) {// 搜索poi的结果
				if (result.getQuery().equals(query)) {// 是否是同一条
					poiResult = result;
					poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
					List<SuggestionCity> suggestionCities = poiResult
							.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
					if (poiItems != null && poiItems.size() > 0) {
						//清除POI信息显示
						whetherToShowDetailInfo(false);
						//并还原点击marker样式
						if (mlastMarker != null) {
							resetlastmarker();
						}				
						//清理之前搜索结果的marker
						if (poiOverlay !=null) {
							poiOverlay.removeFromMap();
						}
						aMap.clear();
						poiOverlay = new myPoiOverlay(aMap, poiItems);
						poiOverlay.addToMap();
						poiOverlay.zoomToSpan();
						
						aMap.addMarker(new MarkerOptions()
						.anchor(0.5f, 0.5f)
						.icon(BitmapDescriptorFactory
								.fromBitmap(BitmapFactory.decodeResource(
										getResources(), R.drawable.point4)))
						.position(new LatLng(lp.getLatitude(), lp.getLongitude())));
						
						 aMap.addCircle(new CircleOptions()
						.center(new LatLng(lp.getLatitude(),
								lp.getLongitude())).radius(5000)
						.strokeColor(Color.BLUE)
						.fillColor(Color.argb(50, 1, 1, 1))
						.strokeWidth(2));

					} else if (suggestionCities != null
							&& suggestionCities.size() > 0) {
						showSuggestCity(suggestionCities);
					} else {
						Toast.makeText( SearchActivity.this,R.string.no_result ,Toast.LENGTH_LONG).show();
					//	ToastUtil.show(PoiAroundSearchActivity.this,
					//			R.string.no_result);
					}
				}
			} else {
				Toast.makeText( SearchActivity.this,R.string.no_result ,Toast.LENGTH_LONG).show();
			//	ToastUtil
			//			.show(PoiAroundSearchActivity.this, R.string.no_result);
			}
		}
	}
	// 将之前被点击的marker置为原来的状态
		private void resetlastmarker() {
			int index = poiOverlay.getPoiIndex(mlastMarker);
			if (index < 20) {
				mlastMarker.setIcon(BitmapDescriptorFactory
						.fromBitmap(BitmapFactory.decodeResource(
								getResources(),
								markers[index])));
			}else {
				mlastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.marker_other_highlight)));
			}
			mlastMarker = null;
			
		}


		private void setPoiItemDisplayContent(final PoiItem mCurrentPoi) {
			mPoiName.setText(mCurrentPoi.getTitle());
			mPoiAddress.setText(mCurrentPoi.getSnippet());
			destlocation = city+mCurrentPoi.getSnippet();
		}
		private int[] markers = {R.drawable.poi_marker_1,
				R.drawable.poi_marker_2,
				R.drawable.poi_marker_3,
				R.drawable.poi_marker_4,
				R.drawable.poi_marker_5,
				R.drawable.poi_marker_6,
				R.drawable.poi_marker_7,
				R.drawable.poi_marker_8,
				R.drawable.poi_marker_9,
				R.drawable.poi_marker_10,
				R.drawable.poi_marker_11,
				R.drawable.poi_marker_12,
				R.drawable.poi_marker_13,
				R.drawable.poi_marker_14,
				R.drawable.poi_marker_15,
				R.drawable.poi_marker_16,
				R.drawable.poi_marker_17,
				R.drawable.poi_marker_18,
				R.drawable.poi_marker_19,
				R.drawable.poi_marker_20
				};
		
		private void whetherToShowDetailInfo(boolean isToShow) {
			if (isToShow) {
				mPoiDetail.setVisibility(View.VISIBLE);

			} else {
				mPoiDetail.setVisibility(View.GONE);

			}
		}
		/**
		 * poi没有搜索到数据，返回一些推荐城市的信息
		 */
		private void showSuggestCity(List<SuggestionCity> cities) {
			String infomation = "推荐城市\n";
			for (int i = 0; i < cities.size(); i++) {
				infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
						+ cities.get(i).getCityCode() + "城市编码:"
						+ cities.get(i).getAdCode() + "\n";
			}
			//ToastUtil.show(this, infomation);
			Toast.makeText( this,infomation ,Toast.LENGTH_LONG).show();
		}
		/**
		 * 自定义PoiOverlay
		 *
		 */
		
		private class myPoiOverlay {
			private AMap mamap;
			private List<PoiItem> mPois;
		    private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();
			public myPoiOverlay(AMap amap ,List<PoiItem> pois) {
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
		            PoiItem item = mPois.get(i);
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
		            b.include(new LatLng(mPois.get(i).getLatLonPoint().getLatitude(),
		                    mPois.get(i).getLatLonPoint().getLongitude()));
		        }
		        return b.build();
		    }

		    private MarkerOptions getMarkerOptions(int index) {
		        return new MarkerOptions()
		                .position(
		                        new LatLng(mPois.get(index).getLatLonPoint()
		                                .getLatitude(), mPois.get(index)
		                                .getLatLonPoint().getLongitude()))
		                .title(getTitle(index)).snippet(getSnippet(index))
		                .icon(getBitmapDescriptor(index));
		    }

		    protected String getTitle(int index) {
		        return mPois.get(index).getTitle();
		    }

		    protected String getSnippet(int index) {
		        return mPois.get(index).getSnippet();
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
		    public PoiItem getPoiItem(int index) {
		        if (index < 0 || index >= mPois.size()) {
		            return null;
		        }
		        return mPois.get(index);
		    }

			protected BitmapDescriptor getBitmapDescriptor(int arg0) {		
				if (arg0 < 20) {
					BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(
							BitmapFactory.decodeResource(getResources(), markers[arg0]));
					return icon;
				}else {
					BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(
							BitmapFactory.decodeResource(getResources(), R.drawable.marker_other_highlight));
					return icon;
				}	
			}
		}
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count,
//				int after) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before,
//				int count) {
//
//			// TODO Auto-generated method stub
//			if(s1!=s.length()){
//				String newText = s.toString().trim();
//				InputtipsQuery inputquery = new InputtipsQuery(newText, city);
//				inputquery.setCityLimit(true);
//				Inputtips inputTips = new Inputtips(SearchActivity.this, inputquery);
//				inputTips.setInputtipsListener(this);
//				inputTips.requestInputtipsAsyn();
//				j=1;
//			}
//			if(i==0){
//
//			}else{
//
//				String newText = s.toString().trim();
//				InputtipsQuery inputquery = new InputtipsQuery(newText, city);
//				inputquery.setCityLimit(true);
//				Inputtips inputTips = new Inputtips(SearchActivity.this, inputquery);
//				inputTips.setInputtipsListener(this);
//				inputTips.requestInputtipsAsyn();
//			}
//		}
//
//		@Override
//		public void afterTextChanged(Editable s) {
//			// TODO Auto-generated method stub
//
//		}

//		@Override
//		public void onGetInputtips(List<Tip> tipList, int rCode) {
//			// TODO Auto-generated method stub
//			if(i==1||j==1){
//				minputlist.setVisibility(View.VISIBLE);
//
//				if (rCode == AMapException.CODE_AMAP_SUCCESS) {
//					List<HashMap<String, String>> listString = new ArrayList<HashMap<String, String>>();
//					for (int i = 0; i < tipList.size(); i++) {
//						HashMap<String, String> map = new HashMap<String, String>();
//						map.put("name", tipList.get(i).getName());
//						map.put("address", tipList.get(i).getDistrict());
//						listString.add(map);
//					}
//					SimpleAdapter aAdapter = new SimpleAdapter(getApplicationContext(), listString, R.layout.item_layout,
//							new String[] {"name","address"}, new int[] {R.id.poi_field_id, R.id.poi_value_id});
//
//					minputlist.setAdapter(aAdapter);
//					aAdapter.notifyDataSetChanged();
//
//				}
//			}
//
//		}
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
			//清空上次计算的路径列表
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
			// TODO Auto-generated method stub
			calculateSuccess = false;
	        Toast.makeText(getApplicationContext(), "计算路线失败，errorcode＝" + arg0, Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onCalculateRouteSuccess() {
			// TODO Auto-generated method stub
			 /**
	         * 清空上次计算的路径列表。
	         */
	        routeOverlays.clear();
	        AMapNaviPath path = mAMapNavi.getNaviPath();
	        /**
	         * 单路径不需要进行路径选择，直接传入－1即可
	         */
	        drawRoutes(-1, path);

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
		public void updateAimlessModeCongestionInfo(
				AimLessModeCongestionInfo arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void updateAimlessModeStatistics(AimLessModeStat arg0) {
			// TODO Auto-generated method stub
			
		}
		 private void drawRoutes(int routeId, AMapNaviPath path) {
			    Toast.makeText(this, "算路", Toast.LENGTH_SHORT).show();
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
				String Sp =HttpUtils.getJsonContent(url,"UTF-8");
				String data="";
				try {
					data= HttpUtils.jsonToObj(Sp);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				}

				
				return data;
		    }
	
}

