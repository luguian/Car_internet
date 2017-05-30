package com.lu.car_internet.activity;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lu.car_internet.R;
import com.lu.car_internet.beans.QuestionBean;
import com.lu.car_internet.database.DBHelperForExercises;
import com.lu.car_internet.utils.Utils;

public class LicenseActivity extends Activity {

	private AlertDialog.Builder exitDialog;
	private AlertDialog.Builder mobileDialog;
	private Button btnSxlx;
	private Button btnSjlx;
	private Button btnZjlx;
	private Button btnLaw;
	private RelativeLayout llSubjectOne;
	private RelativeLayout llSubjectTwo;
	private RelativeLayout llSubjectThree;
	private RelativeLayout llSubjectFour;
	private RelativeLayout llExit;
	private RelativeLayout mnks_rl;
	private ProgressDialog pd;
	private int totalNum = 725;//数据库中考题的总数
	private int testNum = 100;// 需要抽取考题数目
	private int normalSpan = totalNum / testNum;// 每一道被抽取的考题所占的区间
	private int bigSpanNum = totalNum % testNum;// 由于考题总数并不总能被抽取考题数整除而导致有些区间会比其它正常区间大一，该变量即为这些大区间的数量
	private int smallSpanNum = testNum - bigSpanNum;// 正常区间的数目
	private int[] ids = new int[100];
	private Random random = new Random();
	private SharedPreferences sp;
	private Editor editor;
	private Button btnCollection;
	private Button btnCtjlb;
	private Button btnPoint;
	private Button btnCheats;
	private String flag="";
//	private TextView tvUsername;
//	private String usernameFromLogin;
//	private String usernameFromRegister;
//	private LinearLayout llCircleOfFriends;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 完成抽取随机题目并封装到随机集合的操作
			case 1:
			//	pd.dismiss();
				Intent intent_mnks = new Intent(LicenseActivity.this,
						MnksActivity.class);
				intent_mnks.putExtra("ids", ids);
				startActivity(intent_mnks);
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_license);
		ActivityCompat.requestPermissions(LicenseActivity.this, new String[]{android
				.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
		sp = getSharedPreferences("isFirstOrNot", MODE_PRIVATE);
		final boolean isFirst = sp.getBoolean("isFirst", true);
		// 初始化侧滑菜单
		initMenu();
		// 初始化对话框
		initAlertDialog();
		boolean mobileConnected = Utils
				.isNetworkAvailable(LicenseActivity.this);
		if (isFirst && mobileConnected) {
		//	mobileDialog.show();
			editor = sp.edit();
			editor.putBoolean("isFirst", false);
			editor.commit();
		}

		// 初始化控件
		initView();

		Intent intent = getIntent();
//		usernameFromLogin = intent.getStringExtra("usernameFromLogin");
//		usernameFromRegister = intent.getStringExtra("usernameFromRegister");
//		if (usernameFromLogin != null) {
//			tvUsername.setText(usernameFromLogin);
//		} else if (usernameFromRegister != null) {
//			tvUsername.setText(usernameFromRegister);
//		}

	}

	// 初始化控件
	private void initView() {
		btnSxlx = (Button) findViewById(R.id.btn_sxlx);
		btnSjlx = (Button) findViewById(R.id.btn_sjlx);
		btnZjlx = (Button) findViewById(R.id.btn_zjlx);
		mnks_rl = (RelativeLayout) findViewById(R.id.mnks_rl);
		btnLaw = (Button) findViewById(R.id.btn_law);
		btnCollection = (Button) findViewById(R.id.btn_collection);
		llSubjectOne = (RelativeLayout) findViewById(R.id.ll_subjectOne);
		llSubjectTwo = (RelativeLayout) findViewById(R.id.ll_subjectTwo);
		llSubjectThree = (RelativeLayout) findViewById(R.id.ll_subjectThree);
		llSubjectFour = (RelativeLayout) findViewById(R.id.ll_subjectFour);
		llExit = (RelativeLayout) findViewById(R.id.ll_exit);
		btnCtjlb = (Button) findViewById(R.id.btn_ctjlb);
		btnPoint = (Button) findViewById(R.id.btn_point);
		btnCheats = (Button) findViewById(R.id.btn_cheats);
//		tvUsername = (TextView) findViewById(R.id.tv_sliding_menu_username);
//		llCircleOfFriends = (LinearLayout) findViewById(R.id.ll_circle_of_friends);
		btnLaw.setOnClickListener(new MyOnClickListener());
		llSubjectOne.setOnClickListener(new MyOnClickListener());
		llSubjectTwo.setOnClickListener(new MyOnClickListener());
		llSubjectThree.setOnClickListener(new MyOnClickListener());
		llSubjectFour.setOnClickListener(new MyOnClickListener());
		llExit.setOnClickListener(new MyOnClickListener());
		btnSxlx.setOnClickListener(new MyOnClickListener());
		btnSjlx.setOnClickListener(new MyOnClickListener());
		btnZjlx.setOnClickListener(new MyOnClickListener());
		mnks_rl.setOnClickListener(new MyOnClickListener());
		btnCollection.setOnClickListener(new MyOnClickListener());
		btnCtjlb.setOnClickListener(new MyOnClickListener());
		btnPoint.setOnClickListener(new MyOnClickListener());
		btnCheats.setOnClickListener(new MyOnClickListener());
//		llCircleOfFriends.setOnClickListener(new MyOnClickListener());
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

	// 初始化对话框
	private void initAlertDialog() {
		// 退出对话框
		exitDialog = new AlertDialog.Builder(LicenseActivity.this);
		exitDialog.setTitle("提示");
		exitDialog.setMessage("是否确定退出?");
		exitDialog.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				editor = sp.edit();
				editor.putBoolean("isFirst", true);
				editor.commit();
				finish();
			}
		});
		exitDialog.setNegativeButton("取消", null);
		// 弹出框提示
//		mobileDialog = new AlertDialog.Builder(LicenseActivity.this);
//		mobileDialog.setTitle("温馨提示");
//		mobileDialog.setMessage("继续浏览会消耗流量，是否切换为WIFI模式");
//		mobileDialog.setPositiveButton("马上切换", new OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// 跳到WIFI设置界面
//				Intent intent = new Intent(
//						android.provider.Settings.ACTION_WIFI_SETTINGS);
//				startActivity(intent);
//			}
//		});
//		mobileDialog.setNegativeButton("不切换", null);

	}

	class MyOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.btn_sxlx://顺序练习
				if(flag.equals("authority")){
					Intent intent_sxlx = new Intent(LicenseActivity.this,
							SxlxActivity.class);
					startActivity(intent_sxlx);
				}else{
					toastdialog();
				}
				break;
			case R.id.btn_sjlx://随机练习
				if(flag.equals("authority")){
					Intent intent_sjlx = new Intent(LicenseActivity.this,
							SjlxActivity.class);
					startActivity(intent_sjlx);
				}else{
					toastdialog();
				}
				break;
			case R.id.btn_zjlx://章节练习
				if(flag.equals("authority")){
					Intent intent_zjlx = new Intent(LicenseActivity.this,
							ZjlxActivity.class);
					startActivity(intent_zjlx);
				}else{
					toastdialog();
				}
				break;
			case R.id.mnks_rl://模拟考试
//				pd = ProgressDialog.show(LicenseActivity.this, "温馨提示",
//						"正在拼命加载考题><");
				if(flag.equals("authority")){
					new Thread() {
						private ArrayList<QuestionBean> questionList;
						private int bigSpanNum;

						public void run() {
							DBHelperForExercises dbHelperForExercises = new DBHelperForExercises(
									LicenseActivity.this);
							questionList = (ArrayList<QuestionBean>) dbHelperForExercises.orderedExercise();
							if (bigSpanNum != 0) {
								for (int i = 0; i < testNum - bigSpanNum; i++) {
									ids[i] = random.nextInt(normalSpan) + i
											* normalSpan;
								}
								for (int i = testNum - bigSpanNum; i < testNum; i++) {
									ids[i] = random.nextInt(normalSpan + 1)
											+ smallSpanNum * normalSpan
											+ (i - smallSpanNum) * (normalSpan + 1);
								}
							} else {
								for (int i = 0; i < testNum; i++) {
									ids[i] = random.nextInt(normalSpan) + i
											* normalSpan;
								}

							}
							handler.sendEmptyMessage(1);
						}
					}.start();
				}else{
					toastdialog();
				}
				break;

			case R.id.btn_ctjlb://错题记录本
				if(flag.equals("authority")){
					Intent intent_ctjlb = new Intent(LicenseActivity.this, CtjlbActivity.class);
					startActivity(intent_ctjlb);
				}else{
					toastdialog();
				}
				break;

			case R.id.btn_point://考试要点
				if(flag.equals("authority")){
					Intent intent_point = new Intent(LicenseActivity.this,
							PointActivity.class);
					startActivity(intent_point);
				}else{
					toastdialog();
				}
				break;

			case R.id.btn_law://法律法规
				Intent intent_law = new Intent(LicenseActivity.this,
						LawActivity.class);
				startActivity(intent_law);
				break;

			case R.id.btn_cheats://必过
				Intent intent_cheats = new Intent(LicenseActivity.this,
						CheatsActivity.class);
				startActivity(intent_cheats);
				break;

			case R.id.btn_collection:
				if(flag.equals("authority")){
					Intent intent_collection = new Intent(LicenseActivity.this,
							CollectionActivity.class);
					startActivity(intent_collection);
				}else{
					toastdialog();
				}

				break;

			case R.id.ll_subjectOne:
				Intent intent_subjectOne = new Intent(LicenseActivity.this,
						LicenseActivity.class);
				startActivity(intent_subjectOne);
				break;
			case R.id.ll_subjectTwo:
				Intent intent_subjectTwo = new Intent(LicenseActivity.this,
						SubjectTwoActivity.class);
				startActivity(intent_subjectTwo);
				break;
			case R.id.ll_subjectThree:
				Intent intent_subjectThree = new Intent(LicenseActivity.this,
						SubjectThreeActivity.class);
				startActivity(intent_subjectThree);
				break;
			case R.id.ll_subjectFour:
				Intent intent_subjectFour = new Intent(LicenseActivity.this,
						LicenseActivity.class);
				startActivity(intent_subjectFour);
				break;
//			case R.id.ll_circle_of_friends:
//				if (usernameFromLogin != null || usernameFromRegister != null) {
//					Intent intent_circle_of_friends = new Intent(
//							LicenseActivity.this, CircleOfFriendsActivity.class);
//					startActivity(intent_circle_of_friends);
//				} else {
//					Toast.makeText(MainActivity.this, "����û�е�¼���ܲ鿴����Ȧ",
//							Toast.LENGTH_LONG).show();
//				}
//				break;
			case R.id.ll_exit:
				exitDialog.show();
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		editor = sp.edit();
		editor.putBoolean("isFirst", true);
		editor.commit();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
										   @NonNull int[] grantResults) {
		//super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case 1:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// 初始化控件
				   flag="authority";
				}else{
					flag="unauthority";
				}
		}
	}

	private void toastdialog(){
		exitDialog = new AlertDialog.Builder(LicenseActivity.this);
		exitDialog.setTitle("提示");
		exitDialog.setMessage("你没有开启读取文件权限");
		exitDialog.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		exitDialog.show();
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Intent intentback = new Intent(LicenseActivity.this,
					GradientTabStripActivity.class);
			startActivity(intentback);
			return false;
		}else {
			return super.onKeyDown(keyCode, event);
		}

	}
}
