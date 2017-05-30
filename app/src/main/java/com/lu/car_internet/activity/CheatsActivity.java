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

public class CheatsActivity extends Activity implements OnItemClickListener {

	private ListView lvCheats;
	private String[] titles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheats);

		lvCheats = (ListView) findViewById(R.id.lv_cheats);
		initData();
		MyAdapter adapter = new MyAdapter();
		lvCheats.setAdapter(adapter);
		lvCheats.setOnItemClickListener(this);
	}

	private void initData() {

		titles = new String[] { "交规巧记忆", "八种交警手势信号口诀", "处罚相关题巧记", "罚款金额题巧记",
				"最低、最高时速题巧记", "安全距离题巧记" };

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

			convertView = getLayoutInflater().inflate(
					R.layout.list_item_cheats, null);
			TextView tv_law_item = (TextView) convertView
					.findViewById(R.id.tv_cheats_item);
			tv_law_item.setText(titles[position]);
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(CheatsActivity.this, DetailActivity.class);
		intent.putExtra("item", 701 + position);
		startActivity(intent);
	}
}
