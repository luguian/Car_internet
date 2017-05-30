package com.lu.car_internet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.beans.QuestionBean;
import com.lu.car_internet.database.DBHelperForExercises;
import com.lu.car_internet.database.ErrorDao;

import java.util.ArrayList;
import java.util.List;


public class MnksActivity extends Activity implements OnCheckedChangeListener,
		OnChronometerTickListener {

	private TextView tv_title;
	private RadioButton rb_option_a;
	private RadioButton rb_option_b;
	private RadioButton rb_option_c;
	private RadioButton rb_option_d;
	private ImageView iv_picture;
	private Button btn_previous;
	private Button btn_submit;
	private Button btn_next;
	private int currentQuestionIndex = 0;// 0-724题
	private ArrayList<QuestionBean> questionList;
	private RadioGroup rg_base;
	private int option = 0;
	private QuestionBean question;
	private Chronometer chronometer;
	private int minute;
	private int second;
	private List<QuestionBean> randomList;
	private int score = 0;// 总分
	private ErrorDao dao;
	private AlertDialog.Builder builder;
	private AlertDialog.Builder selectDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mnks);
		dao = new ErrorDao(this);
		// 初始化控件
		initView();
		// 利用数据库将随机的100道考题封装到集合中
		getRandomList();
		// 绑定数据
		setData();
		setChronometer();

	}

	private void getRandomList() {

		DBHelperForExercises dbHelperForExercises = new DBHelperForExercises(
				this);
		questionList = (ArrayList<QuestionBean>) dbHelperForExercises
				.orderedExercise();
		Intent intent = getIntent();
		int[] ids = intent.getIntArrayExtra("ids");
		randomList = new ArrayList<QuestionBean>();
		for (int i = 0; i < ids.length; i++) {
			randomList.add(questionList.get(ids[i]));
		}

	}

	private void setChronometer() {
		minute = 40;
		second = 0;
		chronometer = (Chronometer) findViewById(R.id.chro_exam);
		chronometer.setText(nowtime());
		chronometer.start();
		chronometer.setOnChronometerTickListener(this);
		chronometer.setOnClickListener(new MyOnClickListener());
	}

	@Override
	public void onChronometerTick(Chronometer chronometer) {

		second--;
		if (second == -1) {
			minute--;
			second = 59;
		}
		if (minute == -1) {
			chronometer.stop();
		}
		if (minute < 5) {
			chronometer.setTextColor(Color.RED);
			chronometer.setText(nowtime());
		} else {
			chronometer.setTextColor(Color.GREEN);
			chronometer.setText(nowtime());
		}
	}

	private String nowtime() {
		if (second < 10) {
			return (minute + ":0" + second);
		} else {
			return (minute + ":" + second);
		}
	}

	// 绑定数据
	private void setData() {

		question = randomList.get(currentQuestionIndex);
		tv_title.setText((currentQuestionIndex + 1) + "." + question.getTitle());
		int q_type = question.getQ_type();
		// 有的考题有四个选项，而有些考题只有两个选项
		if (q_type == 1) {
			rb_option_a.setText(question.getOptionA());
			rb_option_b.setText(question.getOptionB());
			rb_option_c.setVisibility(RadioButton.VISIBLE);
			rb_option_d.setVisibility(RadioButton.VISIBLE);
			rb_option_c.setText(question.getOptionC());
			rb_option_d.setText(question.getOptionD());
		} else if (q_type == 0) {
			rb_option_a.setText(question.getOptionA());
			rb_option_b.setText(question.getOptionB());
			rb_option_c.setVisibility(RadioButton.INVISIBLE);
			rb_option_d.setVisibility(RadioButton.INVISIBLE);
		}
		if (question.getPicture() != null) {
			byte[] data = question.getPicture();
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			// 设置图片为可见
			iv_picture.setVisibility(ImageView.VISIBLE);
			iv_picture.setImageBitmap(bitmap);
		}
		// 给RadioButton设置监听
		rg_base.setOnCheckedChangeListener(this);
	}

	// 初始化控件
	private void initView() {

		tv_title = (TextView) findViewById(R.id.tv_title);
		rg_base = (RadioGroup) findViewById(R.id.rg_base);
		rb_option_a = (RadioButton) findViewById(R.id.rb_option_a);
		rb_option_b = (RadioButton) findViewById(R.id.rb_option_b);
		rb_option_c = (RadioButton) findViewById(R.id.rb_option_c);
		rb_option_d = (RadioButton) findViewById(R.id.rb_option_d);
		iv_picture = (ImageView) findViewById(R.id.iv_picture);
		btn_previous = (Button) findViewById(R.id.btn_previous);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_previous.setOnClickListener(new MyOnClickListener());
		btn_submit.setOnClickListener(new MyOnClickListener());
		btn_next.setOnClickListener(new MyOnClickListener());

	}

	// 弹出是否确认交卷的对话框
	private void initAlertDialog() {
		selectDialog = new AlertDialog.Builder(MnksActivity.this);
		selectDialog.setTitle("提示");
		selectDialog.setMessage("请选择您想得到的分数类型?");
		selectDialog.setPositiveButton("等级制", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(MnksActivity.this,
						ScoreActivity.class);
				String grade = "";
				if (score <= 100 && score >= 85) {
					grade = "优秀";
				} else if (score < 85 && score >= 75) {
					grade = "良好";
				} else if (score < 75 && score >= 60) {
					grade = "及格";
				} else if (score < 60 && score >= 0) {
					grade = "不及格";
				}
				intent.putExtra("type", 1);
				intent.putExtra("grade", grade);
				startActivity(intent);
			}

		});
		selectDialog.setNegativeButton("百分制", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(MnksActivity.this,
						ScoreActivity.class);
				intent.putExtra("type", 2);
				intent.putExtra("score", score);
				startActivity(intent);
			}
		});
		builder = new AlertDialog.Builder(MnksActivity.this);
		builder.setTitle("提示");
		builder.setMessage("是否确定交卷?");
		builder.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				selectDialog.show();
			}
		});
		builder.setNegativeButton("取消", null);
	}

	// RadioGroup的监听事件
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		if (checkedId == rb_option_a.getId()) {
			option = 1;
		} else if (checkedId == rb_option_b.getId()) {
			option = 2;
		} else if (checkedId == rb_option_c.getId()) {
			option = 3;
		} else if (checkedId == rb_option_d.getId()) {
			option = 4;
		}
	}

	class MyOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.btn_previous:
//				if (currentQuestionIndex > 0) {
//					currentQuestionIndex--;
//					// 将图片设置为不可见
//					iv_picture.setVisibility(ImageView.INVISIBLE);
//					// 将选项被选中的状态清空
//					rb_option_a.setChecked(false);
//					rb_option_b.setChecked(false);
//					rb_option_c.setChecked(false);
//					rb_option_d.setChecked(false);
//					setData();
//				} else {
//					Toast.makeText(MnksActivity.this, "已经到头啦!",
//							Toast.LENGTH_SHORT).show();
//				}
//				dao.deleteAfterPressPrevious(currentQuestionIndex + 1);
				Toast.makeText(MnksActivity.this, "不能返回",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.btn_submit:
				initAlertDialog();
				builder.show();
				if (option == question.getAnswer()) {
					score++;
				} else {
					ContentValues values = new ContentValues();
					values.put("indexQuestion", currentQuestionIndex);
					values.put("q_type", question.getQ_type());
					values.put("title", question.getTitle());
					values.put("optionA", question.getOptionA());
					values.put("optionB", question.getOptionB());
					values.put("optionC", question.getOptionC());
					values.put("optionD", question.getOptionD());
					values.put("rightAnswer", question.getAnswer());
					values.put("wrongAnswer", option);
					values.put("picture", question.getPicture());
					dao.insert("exam", values);
				}
				break;
			case R.id.btn_next:
				if (option == question.getAnswer()) {
					score++;
				} else {
					ContentValues values = new ContentValues();
					values.put("indexQuestion", currentQuestionIndex);
					values.put("q_type", question.getQ_type());
					values.put("title", question.getTitle());
					values.put("optionA", question.getOptionA());
					values.put("optionB", question.getOptionB());
					values.put("optionC", question.getOptionC());
					values.put("optionD", question.getOptionD());
					values.put("rightAnswer", question.getAnswer());
					values.put("wrongAnswer", option);
					values.put("picture", question.getPicture());
					dao.insert("exam", values);
				}
				if (currentQuestionIndex < 99) {
					currentQuestionIndex++;
					// 将图片设置为不可见
					iv_picture.setVisibility(ImageView.INVISIBLE);
					// 将选项被选中的状态清空


//					rb_option_a.setChecked(false);
//					rb_option_b.setChecked(false);
//					rb_option_c.setChecked(false);
//					rb_option_d.setChecked(false);
					setData();
				} else {
					Toast.makeText(MnksActivity.this, "这已经是最后一题啦！",
							Toast.LENGTH_SHORT).show();
				}
				break;

			default:
				break;
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Intent intentback = new Intent(MnksActivity.this,
					LicenseActivity.class);
			startActivity(intentback);
			return false;
		}else {
			return super.onKeyDown(keyCode, event);
		}

	}

}
