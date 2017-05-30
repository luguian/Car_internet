package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lu.car_internet.R;
import com.lu.car_internet.beans.ScoreBean;
import com.lu.car_internet.database.ErrorDao;

import java.util.ArrayList;
import java.util.List;


public class ScoreActivity extends Activity {

	private ListView lvScore;
	private List<ScoreBean> mList;
	private ErrorDao dao;
	private TextView tvScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);

		dao = new ErrorDao(ScoreActivity.this);
		initView();

		initData();
		MyAdapter adapter = new MyAdapter();
		lvScore.setAdapter(adapter);
	}

	private void initView() {

		lvScore = (ListView) findViewById(R.id.lv_activity_score_detail);
		tvScore = (TextView) findViewById(R.id.tv_activity_score_score);
		Intent intent = getIntent();
		int type = intent.getIntExtra("type", 1);
		if (type == 1) {
			String grade = intent.getStringExtra("grade");
			tvScore.setText("您的成绩是： " + grade);
		} else if (type == 2) {
			int score = intent.getIntExtra("score", 0);
			tvScore.setText("您的本次得分是：" + score);
		}

	}

	private void initData() {

		mList = new ArrayList<ScoreBean>();
		if (mList.size() != 0) {
			mList.clear();
		}
		Cursor cursor = dao.select("exam", null);
		if (cursor != null) {
			while (cursor.moveToNext()) {

				int indexQuestion = cursor.getInt(cursor
						.getColumnIndex("indexQuestion"));
				int q_type = cursor.getInt(cursor.getColumnIndex("q_type"));
				String title = cursor.getString(cursor.getColumnIndex("title"));
				String optionA = cursor.getString(cursor
						.getColumnIndex("optionA"));
				String optionB = cursor.getString(cursor
						.getColumnIndex("optionB"));
				String optionC = cursor.getString(cursor
						.getColumnIndex("optionC"));
				String optionD = cursor.getString(cursor
						.getColumnIndex("optionD"));
				int rightAnswer = cursor.getInt(cursor
						.getColumnIndex("rightAnswer"));
				int wrongAnswer = cursor.getInt(cursor
						.getColumnIndex("wrongAnswer"));
				byte[] picture = cursor.getBlob(cursor
						.getColumnIndex("picture"));
				ScoreBean bean = new ScoreBean(indexQuestion, q_type, title,
						optionA, optionB, optionC, optionD, rightAnswer,
						wrongAnswer, picture);
				mList.add(bean);
			}
		}
		dao.clear();

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList != null ? mList.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
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
				convertView = LayoutInflater.from(ScoreActivity.this).inflate(
						R.layout.list_item_activity_score, null);
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_item_activty_score_title);
				holder.tvOptionA = (TextView) convertView
						.findViewById(R.id.tv_item_activty_score_optionA);
				holder.tvOptionB = (TextView) convertView
						.findViewById(R.id.tv_item_activty_score_optionB);
				holder.tvOptionC = (TextView) convertView
						.findViewById(R.id.tv_item_activty_score_optionC);
				holder.tvOptionD = (TextView) convertView
						.findViewById(R.id.tv_item_activty_score_optionD);
				holder.tvRightAnswer = (TextView) convertView
						.findViewById(R.id.tv_item_activty_score_right);
				holder.tvWrongAnswer = (TextView) convertView
						.findViewById(R.id.tv_item_activty_score_wrong);
				holder.ivPicture = (ImageView) convertView
						.findViewById(R.id.iv_item_activity_score_picture);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 绑定数据
			ScoreBean scoreBean = mList.get(position);
			int indexQuestion = scoreBean.getIndexQuestion();
			String title = scoreBean.getTitle();
			holder.tvTitle.setText(indexQuestion + 1 + "." + title);
			String optionA = scoreBean.getOptionA();
			holder.tvOptionA.setText(optionA);
			String optionB = scoreBean.getOptionB();
			holder.tvOptionB.setText(optionB);
			int q_type = scoreBean.getQ_type();
			if (q_type == 1) {
				String optionC = scoreBean.getOptionC();
				holder.tvOptionC.setText(optionC);
				String optionD = scoreBean.getOptionD();
				holder.tvOptionD.setText(optionD);
			}else{
				holder.tvOptionC.setVisibility(View.GONE);
				holder.tvOptionD.setVisibility(View.GONE);
			}
			byte[] picture = scoreBean.getPicture();
			if (picture != null) {
				holder.ivPicture.setVisibility(ImageView.VISIBLE);
				Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0,
						picture.length);
				holder.ivPicture.setImageBitmap(bitmap);
			}
			// 显示正确答案
			int rightAnswer = scoreBean.getRightAnswer();
			String right = "";
			switch (rightAnswer) {
			case 1:
				right = "A";
				break;
			case 2:
				right = "B";
				break;
			case 3:
				right = "C";
				break;
			case 4:
				right = "D";
				break;

			default:
				break;
			}
			holder.tvRightAnswer.setText("正确答案：" + right);
			// 显示用户选的错误答案
			int wrongAnswer = scoreBean.getWrongAnswer();
			String wrong = "";
			switch (wrongAnswer) {
			case 1:
				wrong = "A";
				break;
			case 2:
				wrong = "B";
				break;
			case 3:
				wrong = "C";
				break;
			case 4:
				wrong = "D";
				break;

			default:
				break;
			}
			holder.tvWrongAnswer.setText("错误答案：" + wrong);
			return convertView;
		}

		class ViewHolder {
			TextView tvTitle, tvRightAnswer, tvWrongAnswer, tvOptionA,
					tvOptionB, tvOptionC, tvOptionD;
			ImageView ivPicture;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Intent intentback = new Intent(ScoreActivity.this,
					LicenseActivity.class);
			startActivity(intentback);
			return false;
		}else {
			return super.onKeyDown(keyCode, event);
		}

	}

}
