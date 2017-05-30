package com.lu.car_internet.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheshouye.api.client.WeizhangClient;
import com.cheshouye.api.client.json.ProvinceInfoJson;
import com.lu.car_internet.R;
import com.lu.car_internet.adapters.ListAdapter;
import com.lu.car_internet.model.ListModel;


public class ProvinceList extends Activity {
	private ListView lv_list;
	private ListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.csy_activity_citys);
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.csy_titlebar);
		
		//设置
		TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTitle.setText("选择查询地-省份");
		
		//返回按钮
		Button btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setVisibility(View.VISIBLE);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		lv_list = (ListView) findViewById(R.id.lv_1ist);

			mAdapter = new ListAdapter(this, getData2());
			lv_list.setAdapter(mAdapter);

		

		lv_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				TextView txt_name = (TextView) view.findViewById(R.id.txt_name);

				Intent intent = new Intent();
				intent.putExtra("province_name", txt_name.getText());
				intent.putExtra("province_id", txt_name.getTag().toString());

				intent.setClass(ProvinceList.this, CityList.class);
				startActivityForResult(intent, 20);
			}
		});

	}

	/**
	 * title:获取省份信息
	 * 
	 * @return
	 */
	private List<ListModel> getData2() {

		List<ListModel> list = new ArrayList<ListModel>();
		List<ProvinceInfoJson> provinceList = WeizhangClient.getAllProvince();

			//Toast.makeText(ProvinceList.this, "由于本地没有缓存省份，正在从网上获取省份，请稍等", 0).show();
		
				//开通数量提示
		TextView txtListTip = (TextView) findViewById(R.id.list_tip);
		txtListTip.setText("全国已开通"+provinceList.size()+"个省份, 其他省份将陆续开放");
		
		for (ProvinceInfoJson provinceInfoJson : provinceList) {
			String provinceName = provinceInfoJson.getProvinceName();
			int provinceId = provinceInfoJson.getProvinceId();
			
			ListModel model = new ListModel();
			model.setTextName(provinceName);
			model.setNameId(provinceId);
			list.add(model);
		}
	
		return list;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null)
			return;
		Bundle bundle = data.getExtras();
		// 获取城市name
		String cityName = bundle.getString("city_name");
		String cityId = bundle.getString("city_id");

		Intent intent = new Intent();
		intent.putExtra("city_name", cityName);
		intent.putExtra("city_id", cityId);
		setResult(1, intent);
		finish();
	}
	
}
