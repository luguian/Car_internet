package com.lu.car_internet.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lu.car_internet.config.QuestionConfig;


public class DBHelperForCollection extends SQLiteOpenHelper {
	public DBHelperForCollection(Context context) {
		super(context, "collection.db", null, QuestionConfig.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE collection (_id INTEGER PRIMARY KEY AUTOINCREMENT,q_type INTEGER,title VARCHAR(100),optionA VARCHAR(100),optionB VARCHAR(100),optionC VARCHAR(100),optionD VARCHAR(100),rightAnswer INTEGER,picture VARCHAR(10000))";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}
}
