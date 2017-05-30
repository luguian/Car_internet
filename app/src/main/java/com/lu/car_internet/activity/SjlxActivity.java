package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.beans.QuestionBean;
import com.lu.car_internet.database.CollectionDao;
import com.lu.car_internet.database.CtjlbDao;
import com.lu.car_internet.database.DBHelperForExercises;

import java.util.ArrayList;
import java.util.Random;

public class SjlxActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	private TextView tv_title;
	private RadioButton rb_option_a;
	private RadioButton rb_option_b;
	private RadioButton rb_option_c;
	private RadioButton rb_option_d;
	private ImageView iv_picture;
	private Button btn_previous;
	private Button btn_collect;
	private Button btn_check;
	private Button btn_next;
	private int currentQuestionIndex = 0;// 0-724题
	private int preQuestionIndex = 0;// 上一题的题号
	private ArrayList<QuestionBean> questionList;
	private int sizeOfList;
	private RadioGroup rg_base;
	private int option = 0;
	private QuestionBean question;
	private Random random = new Random();
	private boolean hasCollected = false;
	private CollectionDao collectionDao;
	private CtjlbDao ctjlbDao;
	private ImageView ivFavor;
	private ImageView question_stateim;
	private TextView question_tip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCompat.requestPermissions(SjlxActivity.this, new String[]{android
				.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
	}

	// 绑定数据
	private void setData() {
		currentQuestionIndex = random.nextInt(725);
		question = questionList.get(currentQuestionIndex);
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
	//	btn_collect = (Button) findViewById(R.id.btn_collect);
		btn_check = (Button) findViewById(R.id.btn_check);
		btn_next = (Button) findViewById(R.id.btn_next);
		ivFavor = (ImageView) findViewById(R.id.iv_favor);
		question_stateim = (ImageView)findViewById(R.id.question_stateim);
		question_tip = (TextView)findViewById(R.id.question_tip);
		btn_previous.setOnClickListener(this);
	//	btn_collect.setOnClickListener(this);
		btn_check.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		ivFavor.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_previous:
			currentQuestionIndex = preQuestionIndex;
			// 将图片设置为不可见
			iv_picture.setVisibility(ImageView.INVISIBLE);
			question_stateim.setVisibility(View.INVISIBLE);
			question_tip.setVisibility(View.INVISIBLE);
			// 将选项被选中的状态清空
//			rb_option_a.setChecked(false);
//			rb_option_b.setChecked(false);
//			rb_option_c.setChecked(false);
//			rb_option_d.setChecked(false);
			setData();

			break;
		case R.id.iv_favor:
			if (!hasCollected) {
				hasCollected = true;
				ivFavor.setImageResource(R.drawable.icon_collect);
				ContentValues values = new ContentValues();
				values.put("q_type", question.getQ_type());
				values.put("title", question.getTitle());
				values.put("optionA", question.getOptionA());
				values.put("optionB", question.getOptionB());
				values.put("optionC", question.getOptionC());
				values.put("optionD", question.getOptionD());
				values.put("rightAnswer", question.getAnswer());
				values.put("picture", question.getPicture());
				collectionDao.insert("collection", values);
				Toast.makeText(SjlxActivity.this, "收藏成功", Toast.LENGTH_SHORT)
						.show();
			} else {
				hasCollected = false;
				ivFavor.setImageResource(R.drawable.ic_favou);
				collectionDao.delete("collection", "title=?",
						new String[] { question.getTitle() });
				Toast.makeText(SjlxActivity.this, "已取消收藏", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.btn_check:
			if (option == 0) {
				Toast.makeText(SjlxActivity.this, "还没选呐！", Toast.LENGTH_LONG)
						.show();
			} else if (option == question.getAnswer()) {
				question_stateim.setVisibility(View.VISIBLE);
				question_tip.setVisibility(View.VISIBLE);
				question_stateim.setImageResource(R.drawable.normal);
				question_tip.setText("回答正确");
			} else {
				String showAnswer = null;
				switch (question.getAnswer()) {
				case 1:
					showAnswer = "A";
					break;
				case 2:
					showAnswer = "B";
					break;
				case 3:
					showAnswer = "C";
					break;
				case 4:
					showAnswer = "D";
					break;
				default:
					break;
				}
				question_stateim.setVisibility(View.VISIBLE);
				question_tip.setVisibility(View.VISIBLE);
				question_stateim.setImageResource(R.drawable.error);
				question_tip.setText("正确答案:"+showAnswer);
			}
			break;
		case R.id.btn_next:
			preQuestionIndex = currentQuestionIndex;
			currentQuestionIndex = random.nextInt(725);
			// 将图片设置为不可见
			iv_picture.setVisibility(ImageView.INVISIBLE);
			question_stateim.setVisibility(View.INVISIBLE);
			question_tip.setVisibility(View.INVISIBLE);
			// 将选项被选中的状态清空
//			rb_option_a.setChecked(false);
//			rb_option_b.setChecked(false);
//			rb_option_c.setChecked(false);
//			rb_option_d.setChecked(false);
			setData();
			if (option != question.getAnswer()) {
				ContentValues values = new ContentValues();
				values.put("q_type", question.getQ_type());
				values.put("title", question.getTitle());
				values.put("optionA", question.getOptionA());
				values.put("optionB", question.getOptionB());
				values.put("optionC", question.getOptionC());
				values.put("optionD", question.getOptionD());
				values.put("rightAnswer", question.getAnswer());
				values.put("picture", question.getPicture());
				ctjlbDao.insert("ctjlb", values);
			}
			break;

		default:
			break;
		}
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
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
										   @NonNull int[] grantResults) {
		//super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case 1:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// 初始化控件
					setContentView(R.layout.activity_sxlx);
					initView();
					// 创建用于收藏的数据库操作类的对象
					collectionDao = new CollectionDao(this);
					// 创建用于将错题收藏进错题记录本的数据库操作类的对象
					ctjlbDao = new CtjlbDao(this);
					// 利用数据库将全部考题封装到集合中
					DBHelperForExercises dbHelperForExercises = new DBHelperForExercises(
							this);
					questionList = (ArrayList<QuestionBean>) dbHelperForExercises
							.orderedExercise();
					sizeOfList = questionList.size();
					// 绑定数据
					setData();
				}else{

				}
		}
	}

}
