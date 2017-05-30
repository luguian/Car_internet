package com.lu.car_internet.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lu.car_internet.config.QuestionConfig;


public class DBHelperForError extends SQLiteOpenHelper {

	public DBHelperForError(Context context) {
		super(context, "exam.db", null, QuestionConfig.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE exam (_id INTEGER PRIMARY KEY AUTOINCREMENT,indexQuestion INTEGER,q_type INTEGER,title VARCHAR(100),optionA VARCHAR(100),optionB VARCHAR(100),optionC VARCHAR(100),optionD VARCHAR(100),rightAnswer INTEGER,wrongAnswer INTEGER,picture VARCHAR(10000))";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
