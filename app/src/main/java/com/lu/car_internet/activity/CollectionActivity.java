package com.lu.car_internet.activity;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lu.car_internet.R;
import com.lu.car_internet.beans.CollectionBean;
import com.lu.car_internet.database.CollectionDao;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends Activity {

	private CollectionDao collectionDao;
	private ListView lvCollection;
	private List<CollectionBean> mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		collectionDao = new CollectionDao(this);

		initData();
		initView();
		MyAdapter adapter = new MyAdapter();
		lvCollection.setAdapter(adapter);
	}

	private void initView() {

		lvCollection = (ListView) findViewById(R.id.lv_activity_collection_detail);
	}

	private void initData() {

		mList = new ArrayList<CollectionBean>();
		if (mList.size() != 0) {
			mList.clear();
		}
		Cursor cursor = collectionDao.select("collection", null);
		if (cursor != null) {
			while (cursor.moveToNext()) {

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
				byte[] picture = cursor.getBlob(cursor
						.getColumnIndex("picture"));
				CollectionBean bean = new CollectionBean(q_type, title,
						optionA, optionB, optionC, optionD, rightAnswer,
						picture);
				mList.add(bean);
			}
		}

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
				convertView = LayoutInflater.from(CollectionActivity.this)
						.inflate(R.layout.list_item_activity_collection, null);
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_item_activty_collection_title);
				holder.tvOptionA = (TextView) convertView
						.findViewById(R.id.tv_item_activty_collection_optionA);
				holder.tvOptionB = (TextView) convertView
						.findViewById(R.id.tv_item_activty_collection_optionB);
				holder.tvOptionC = (TextView) convertView
						.findViewById(R.id.tv_item_activty_collection_optionC);
				holder.tvOptionD = (TextView) convertView
						.findViewById(R.id.tv_item_activty_collection_optionD);
				holder.tvRightAnswer = (TextView) convertView
						.findViewById(R.id.tv_item_activty_collection_right);
				holder.ivPicture = (ImageView) convertView
						.findViewById(R.id.iv_item_activity_collection_picture);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 绑定数据
			CollectionBean collectionBean = mList.get(position);
			String title = collectionBean.getTitle();
			holder.tvTitle.setText(position + 1 + "." + title);
			String optionA = collectionBean.getOptionA();
			holder.tvOptionA.setText(optionA);
			String optionB = collectionBean.getOptionB();
			holder.tvOptionB.setText(optionB);
			int q_type = collectionBean.getQ_type();
			if (q_type == 1) {
				String optionC = collectionBean.getOptionC();
				holder.tvOptionC.setText(optionC);
				String optionD = collectionBean.getOptionD();
				holder.tvOptionD.setText(optionD);
			}
			byte[] picture = collectionBean.getPicture();
			if (picture != null) {
				holder.ivPicture.setVisibility(ImageView.VISIBLE);
				Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0,
						picture.length);
				holder.ivPicture.setImageBitmap(bitmap);
			}
			// 显示正确答案
			int rightAnswer = collectionBean.getRightAnswer();
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
			return convertView;
		}

		class ViewHolder {
			TextView tvTitle, tvRightAnswer, tvOptionA, tvOptionB, tvOptionC,
					tvOptionD;
			ImageView ivPicture;
		}

	}

}
