package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.lu.car_internet.database.DBHelperForCtjlb;

import java.util.ArrayList;
import java.util.List;


public class CtjlbActivity extends Activity implements OnClickListener,
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
	private int currentQuestionIndex = 0;
	private int sizeOfList;
	private RadioGroup rg_base;
	private int option = 0;
	private QuestionBean question;
	private boolean hasCollected = false;
	private CollectionDao collectionDao;
	private CtjlbDao ctjlbDao;
	private List<QuestionBean> questionList;
	private static SQLiteDatabase dbCtjlb;
	private ImageView ivFavor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ctjlb);

		// 初始化控件
		initView();
		// 创建用于收藏的数据库操作类的对象
		collectionDao = new CollectionDao(this);
		// 创建用于将错题收藏进错题记录本的数据库操作类的对象
		ctjlbDao = new CtjlbDao(this);

		questionList = new ArrayList<QuestionBean>();
		DBHelperForCtjlb dbHelperForCtjlb = new DBHelperForCtjlb(this);
		dbCtjlb = dbHelperForCtjlb.getWritableDatabase();
		// 将错题记录本数据库中的考题封装到集合中
		Cursor cursor = dbCtjlb.query("ctjlb", new String[] { "_id", "title",
				"optionA", "optionB", "optionC", "optionD", "rightAnswer",
				"q_type", "picture" }, null, null, null, null, null);
		if (cursor.getCount()==0) {
			Toast.makeText(CtjlbActivity.this,"您还没有任何错题记录", Toast.LENGTH_SHORT)
					.show();
		}else{
			while (cursor.moveToNext()) {
				QuestionBean question = new QuestionBean(cursor.getInt(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4),
						cursor.getString(5), cursor.getInt(6), cursor.getInt(7),
						cursor.getBlob(8));
				questionList.add(question);
			}
			sizeOfList = questionList.size();
			// 绑定数据
			setData();

		}


	}

	// 绑定数据
	private void setData() {

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
		btn_previous.setOnClickListener(this);
//		btn_collect.setOnClickListener(this);
		btn_check.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		ivFavor.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_previous:
			if (currentQuestionIndex > 0) {
				currentQuestionIndex--;
				// 将图片设置为不可见
				iv_picture.setVisibility(ImageView.INVISIBLE);
				// 将选项被选中的状态清空
				rb_option_a.setChecked(false);
				rb_option_b.setChecked(false);
				rb_option_c.setChecked(false);
				rb_option_d.setChecked(false);
				setData();
			} else {
				Toast.makeText(CtjlbActivity.this, "已经到头啦!", Toast.LENGTH_SHORT)
						.show();
			}
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
				Toast.makeText(CtjlbActivity.this, "收藏成功", Toast.LENGTH_SHORT)
						.show();

			} else {
				hasCollected = false;
				ivFavor.setImageResource(R.drawable.ic_favou);
				collectionDao.delete("collection", "title=?",
						new String[] { question.getTitle() });
				Toast.makeText(CtjlbActivity.this, "已取消收藏", Toast.LENGTH_SHORT)
						.show();

			}
			break;
		case R.id.btn_check:

			if (option == 0) {
				Toast.makeText(CtjlbActivity.this, "您还没有做出选择",
						Toast.LENGTH_LONG).show();
			} else if (option == question.getAnswer()) {
				Toast.makeText(CtjlbActivity.this, "好厉害！回答正确！",
						Toast.LENGTH_LONG).show();
				ctjlbDao.delete("ctjlb", "title=?",
						new String[] { question.getTitle() });
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
				Toast.makeText(CtjlbActivity.this, "回答错误，正确答案为" + showAnswer,
						Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.btn_next:
			if (currentQuestionIndex < sizeOfList - 1) {
				currentQuestionIndex++;
				// 将图片设置为不可见
				iv_picture.setVisibility(ImageView.INVISIBLE);
				// 将选项被选中的状态清空
				rb_option_a.setChecked(false);
				rb_option_b.setChecked(false);
				rb_option_c.setChecked(false);
				rb_option_d.setChecked(false);
				setData();
			} else {
				Toast.makeText(CtjlbActivity.this, "这已经是最后一题啦！",
						Toast.LENGTH_SHORT).show();
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

}
