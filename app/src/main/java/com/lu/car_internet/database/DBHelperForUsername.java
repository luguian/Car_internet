package com.lu.car_internet.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lu.car_internet.config.QuestionConfig;


public class DBHelperForUsername extends SQLiteOpenHelper {

	public DBHelperForUsername(Context context) {
		super(context, "username.db", null, QuestionConfig.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE username (_id INTEGER PRIMARY KEY AUTOINCREMENT,username VARCHAR(30))";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}
}
