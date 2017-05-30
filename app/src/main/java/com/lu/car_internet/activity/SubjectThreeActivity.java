package com.lu.car_internet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lu.car_internet.R;
import com.lu.car_internet.database.UsernameDao;

public class SubjectThreeActivity extends Activity implements
		OnItemClickListener {
	private AlertDialog.Builder exitDialog;
	private RelativeLayout ll_subjectOne;
	private RelativeLayout  ll_subjectTwo;
	private RelativeLayout  ll_subjectThree;
	private RelativeLayout  ll_subjectFour;
	private RelativeLayout  ll_exit;
	private ListView lv_subjectThree;
	private String titles[];
	private String subtitles[];
	//private TextView tvUsername;
	//private LinearLayout llCircleOfFriends;
	private UsernameDao dao;
	//private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject_three);

		dao = new UsernameDao(this);
		initMenu();
		initView();

		lv_subjectThree = (ListView) findViewById(R.id.lv_subjectThree);
		MyAdapter adapter = new MyAdapter();
		initData();
		lv_subjectThree.setAdapter(adapter);
		lv_subjectThree.setOnItemClickListener(this);
	}

	// 初始化侧滑菜单
	private void initMenu() {
        SlidingMenu menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setMode(SlidingMenu.LEFT);
		menu.setMenu(R.layout.slidingmenu);
		menu.setBehindWidth(600);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
	}

	// 初始化控件
	private void initView() {
		ll_subjectOne = (RelativeLayout) findViewById(R.id.ll_subjectOne);
		ll_subjectTwo = (RelativeLayout) findViewById(R.id.ll_subjectTwo);
		ll_subjectThree = (RelativeLayout) findViewById(R.id.ll_subjectThree);
		ll_subjectFour = (RelativeLayout) findViewById(R.id.ll_subjectFour);
		ll_exit = (RelativeLayout) findViewById(R.id.ll_exit);
		//tvUsername = (TextView) findViewById(R.id.tv_sliding_menu_username);
//		Cursor cursor = dao.select("username", null);
//		while (cursor.moveToNext()) {
//
//			username = cursor.getString(cursor.getColumnIndex("username"));
//		}
//		tvUsername.setText(username);
//		llCircleOfFriends = (LinearLayout) findViewById(R.id.ll_circle_of_friends);
		ll_subjectOne.setOnClickListener(new MyOnClickListener());
		ll_subjectTwo.setOnClickListener(new MyOnClickListener());
		ll_subjectThree.setOnClickListener(new MyOnClickListener());
		ll_subjectFour.setOnClickListener(new MyOnClickListener());
		ll_exit.setOnClickListener(new MyOnClickListener());
//		llCircleOfFriends.setOnClickListener(new MyOnClickListener());

	}

	// 初始化对话框
	private void initAlertDialog() {
		// 退出对话框
		exitDialog = new AlertDialog.Builder(SubjectThreeActivity.this);
		exitDialog.setTitle("提示");
		exitDialog.setMessage("是否确定退出?");
		exitDialog.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		exitDialog.setNegativeButton("取消", null);

	}

	private void initData() {

		titles = new String[] { "上车准备", "起步", "直线行驶", "加减挡位", "变更车道", "靠边停车",
				"通过路口", "通过各区域", "会车", "超车", "掉头", "夜间行驶", "路考口诀", "做好七步，路考必过",
				"五招远离路考恐惧" };
		subtitles = new String[] { "上车前观察车辆外观和周围环境，确认安全",
				"调整和检查车内设施，观察后方、侧方交通...", "合理控制车速，使用档位，保持直线行驶。",
				"合理加减档，换挡及时，平顺。", "由一个车道进入另一个车道的驾驶操作。", "驾驶车辆使之靠边停下。",
				"减速或停车瞭望，直行安全通过路口。", "通过人行横道，学校区域，公交车站。",
				"正确判断会车地点，与对方车辆保持安全间距...", "经过另一车侧面，从后面超过同方向行驶...",
				"正确选择掉头地点和时机，发出掉头信号后...", "根据各种照明情况和道路情况正确使用灯光。",
				"方便记忆的路考顺口溜。", "路考七步小秘籍", "克服路考恐惧小技巧" };
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
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(SubjectThreeActivity.this)
						.inflate(R.layout.list_item_subject_three, null);
				holder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder.tv_subtitle = (TextView) convertView
						.findViewById(R.id.tv_subtitle);
				holder.iv_order = (ImageView) convertView
						.findViewById(R.id.iv_order);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 绑定数据
			holder.tv_title.setText(titles[position]);
			holder.tv_subtitle.setText(subtitles[position]);

			return convertView;
		}

	}

	class ViewHolder {
		TextView tv_title, tv_subtitle;
		ImageView iv_order;
	}

	class MyOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.ll_subjectOne:
				Intent intent_subjectOne = new Intent(
						SubjectThreeActivity.this, LicenseActivity.class);
				startActivity(intent_subjectOne);
				break;
			case R.id.ll_subjectTwo:
				Intent intent_subjectTwo = new Intent(
						SubjectThreeActivity.this, SubjectTwoActivity.class);
				startActivity(intent_subjectTwo);
				break;
			case R.id.ll_subjectThree:
				Intent intent_subjectThree = new Intent(
						SubjectThreeActivity.this, SubjectThreeActivity.class);
				startActivity(intent_subjectThree);
				break;

			case R.id.ll_subjectFour:
				Intent intent_subjectFour = new Intent(
						SubjectThreeActivity.this, LicenseActivity.class);
				startActivity(intent_subjectFour);
				break;
//			case R.id.ll_circle_of_friends:
//				Intent intent_circle_of_friends = new Intent(
//						SubjectThreeActivity.this,
//						CircleOfFriendsActivity.class);
//				startActivity(intent_circle_of_friends);
//				break;
			case R.id.ll_exit:
				exitDialog.show();
				break;
			default:
				break;
			}
		}
	}

	// lv_subjectThree的监听
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(SubjectThreeActivity.this,
				DetailActivity.class);
		intent.putExtra("item", 301 + position);
		startActivity(intent);
	}

}
