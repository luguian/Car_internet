package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lu.car_internet.R;


public class ZjlxActivity extends Activity implements OnItemClickListener {

	private ListView lv_zjlx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zjlx);
		ActivityCompat.requestPermissions(ZjlxActivity.this, new String[]{android
				.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(ZjlxActivity.this, ZjlxTypeActivity.class);
		intent.putExtra("index", position);
		startActivity(intent);

	}

	class MyAdapter extends BaseAdapter {

		String titles[];
		Context context;

		public void setData(Context context, String titles[]) {
			this.titles = titles;
			this.context = context;
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			return titles[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_item_zjlx, null);
			TextView tv_zjlx_item_type = (TextView) convertView
					.findViewById(R.id.tv_zjlx_item_type);
			tv_zjlx_item_type.setText(titles[position]);

			return convertView;
		}

	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
										   @NonNull int[] grantResults) {
		//super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case 1:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// 初始化控件
					lv_zjlx = (ListView) findViewById(R.id.lv_zjlx);
					MyAdapter adapter = new MyAdapter();
					String titles[] = { "恶劣天气安全行驶试题", "车辆安全行驶试题", "夜间行车注意事项",
							"在特殊道路上安全行驶试题", "自动挡汽车安全行驶相关知识", "机动车安全行驶相关试题" };
					adapter.setData(this, titles);
					lv_zjlx.setAdapter(adapter);
					lv_zjlx.setOnItemClickListener(this);
				}
		}
	}

}
