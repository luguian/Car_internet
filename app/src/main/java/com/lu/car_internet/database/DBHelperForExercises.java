package com.lu.car_internet.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lu.car_internet.beans.QuestionBean;
import com.lu.car_internet.config.QuestionConfig;

import java.util.ArrayList;
import java.util.List;


public class DBHelperForExercises {

	private static SQLiteDatabase db;
	private Context mContext;

	public DBHelperForExercises(Context context) {
		this.mContext = context;
	}

	// 开启连接
	public void openConn() {

		if (db == null) {
			DataBaseToSD dbtsd = new DataBaseToSD(mContext);
			// 把raw下的数据库写进SD卡
			dbtsd.WriteToSD();
			// 打开SD卡中的数据库并赋给db
			db = SQLiteDatabase.openOrCreateDatabase(
					QuestionConfig.DB_FILE_PATH, null);
		}

	}

	// 关闭连接
	public void closeConn() {
		if (db != null) {
			db.close();
			db = null;
		}
	}

	// 获得各种题型的方法的公共部分
	public void baseCursor(List<QuestionBean> list, String where) {
		Cursor cursor = db.query(QuestionConfig.TABLE_NAME, new String[] {
				"_id", "mexam_type", "question", "optionA", "optionB",
				"optionC", "optionD", "answer", "q_type", "image" }, where,
				null, null, null, null);
		while (cursor.moveToNext()) {
			QuestionBean question = new QuestionBean(cursor.getInt(0),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5),
					cursor.getString(6), cursor.getInt(7), cursor.getInt(8),
					cursor.getBlob(9));
			list.add(question);
		}
		cursor.close();
		closeConn();
	}

	// 全部考题供顺序联系,随机联系使用
	public List<QuestionBean> orderedExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		// 开启连接
		openConn();

		String where = null;
		baseCursor(list, where);

		return list;
	}

	// 恶劣天气安全行驶试题
	public List<QuestionBean> badWeatherExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		// 开启连接
		openConn();
		String where = "question like " + "'%雨%'";
		baseCursor(list, where);
		return list;
	}

	// 车辆安全行驶试题
	public List<QuestionBean> safeDrivingExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		// 开启连接
		openConn();
		String where = "question like " + "'驾驶车辆%'";
		baseCursor(list, where);
		return list;
	}

	// 夜间行车注意事项
	public List<QuestionBean> eveningDrivingExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		//开启连接
		openConn();
		String where = "question like " + "'%夜间%'";
		baseCursor(list, where);
		return list;
	}

	// 在特殊道路上安全行驶试题
	public List<QuestionBean> whileDrivingExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		// 开启连接
		openConn();
		String where = "question like " + "'%行车中%'";
		baseCursor(list, where);
		return list;
	}

	//自动挡
	public List<QuestionBean> autoExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		// 开启连接
		openConn();
		String where = "question like " + "'%自动挡%'";
		baseCursor(list, where);
		return list;
	}

	// 机动车
	public List<QuestionBean> motorExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		// 开启连接
		openConn();
		String where = "question like " + "'%机动车%'";
		baseCursor(list, where);
		return list;
	}

}
