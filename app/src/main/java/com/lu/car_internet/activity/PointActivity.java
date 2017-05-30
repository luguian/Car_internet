package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lu.car_internet.R;


public class PointActivity extends Activity implements OnItemClickListener {

	private ListView lvPoint;
	private String[] titles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_point);

		lvPoint = (ListView) findViewById(R.id.lv_point);
		initData();
		MyAdapter adapter = new MyAdapter();
		lvPoint.setAdapter(adapter);
		lvPoint.setOnItemClickListener(this);
	}

	private void initData() {

		titles = new String[] { "合格标准", "扣分细则", "手势口诀", "考试技巧", "酒驾要点", "交规技巧",
				"处罚相关题技巧" };

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return titles[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			convertView = getLayoutInflater().inflate(R.layout.list_item_point,
					null);
			TextView tv_law_item = (TextView) convertView
					.findViewById(R.id.tv_point_item);
			tv_law_item.setText(titles[position]);
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(PointActivity.this, DetailActivity.class);
		intent.putExtra("item", 501 + position);
		startActivity(intent);
	}

}
