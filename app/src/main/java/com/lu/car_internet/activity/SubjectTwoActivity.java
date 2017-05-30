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


public class SubjectTwoActivity extends Activity implements OnItemClickListener {

	private AlertDialog.Builder exitDialog;
	private RelativeLayout ll_subjectOne;
	private RelativeLayout ll_subjectTwo;
	private RelativeLayout ll_subjectThree;
	private RelativeLayout ll_subjectFour;
	private RelativeLayout ll_exit;
	private ListView lv_subjectTwo;
	private String titles[];
	private String subtitles[];
//	private TextView tvUsername;
//	private LinearLayout llCircleOfFriends;
	private UsernameDao dao;
//	private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject_two);

		dao = new UsernameDao(this);
		initMenu();
		initView();

		lv_subjectTwo = (ListView) findViewById(R.id.lv_subjectTwo);
		MyAdapter adapter = new MyAdapter();
		initData();
		lv_subjectTwo.setAdapter(adapter);
		lv_subjectTwo.setOnItemClickListener(this);
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
		ll_subjectOne.setOnClickListener(new MyOnClickListener());
		ll_subjectTwo.setOnClickListener(new MyOnClickListener());
		ll_subjectThree.setOnClickListener(new MyOnClickListener());
		ll_subjectFour.setOnClickListener(new MyOnClickListener());
		ll_exit.setOnClickListener(new MyOnClickListener());

	}

	// 初始化对话框
	private void initAlertDialog() {
		// 退出对话框
		exitDialog = new AlertDialog.Builder(SubjectTwoActivity.this);
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
		titles = new String[] { "考前准备", "合格标准", "坡道定点停车和起步", "侧方停车", "曲线行驶",
				"直角转弯", "倒车入库" };
		subtitles = new String[] { "做好准备 淡定考试", "考试合格的评分标准", "坡道定点停车和起步要领",
				"考试要领，过关技巧", "考试技巧 评分标准", "评分标准 驾驶技巧图解", "操作方法 评分标准" };
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
				convertView = LayoutInflater.from(SubjectTwoActivity.this)
						.inflate(R.layout.list_item_subject_two, null);
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

	// lv_subjectTwo的监听
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(SubjectTwoActivity.this,
				DetailActivity.class);
		intent.putExtra("item", 201 + position);
		startActivity(intent);

	}

	class MyOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.ll_subjectOne:
				Intent intent_subjectOne = new Intent(SubjectTwoActivity.this,
						LicenseActivity.class);
				startActivity(intent_subjectOne);
				break;
			case R.id.ll_subjectTwo:
				Intent intent_subjectTwo = new Intent(SubjectTwoActivity.this,
						SubjectTwoActivity.class);
				startActivity(intent_subjectTwo);
				break;
			case R.id.ll_subjectThree:
				Intent intent_subjectThree = new Intent(
						SubjectTwoActivity.this, SubjectThreeActivity.class);
				startActivity(intent_subjectThree);
				break;

			case R.id.ll_subjectFour:
				Intent intent_subjectFour = new Intent(SubjectTwoActivity.this,
						LicenseActivity.class);
				startActivity(intent_subjectFour);
				break;
//			case R.id.ll_circle_of_friends:
//				Intent intent_circle_of_friends = new Intent(
//						SubjectTwoActivity.this, CircleOfFriendsActivity.class);
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

}
