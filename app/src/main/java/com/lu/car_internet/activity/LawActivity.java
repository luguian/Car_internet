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


public class LawActivity extends Activity implements OnItemClickListener {

	private ListView lv_law;
	private int[] law_titles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_law);

		lv_law = (ListView) findViewById(R.id.lv_law);
		initData();
		MyAdapter_law adapter = new MyAdapter_law();
		lv_law.setAdapter(adapter);
		lv_law.setOnItemClickListener(this);
	}

	private void initData() {

		law_titles = new int[] { R.string.law01_title, R.string.law02_title,
				R.string.law03_title, R.string.law04_title,
				R.string.law05_title };

	}

	class MyAdapter_law extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return law_titles.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return law_titles[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			convertView = getLayoutInflater().inflate(R.layout.list_item_law,
					null);
			TextView tv_law_item = (TextView) convertView
					.findViewById(R.id.tv_law_item);
			tv_law_item.setText(law_titles[position]);
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(LawActivity.this, DetailActivity.class);
		intent.putExtra("item", 601 + position);
		startActivity(intent);
	}

}
