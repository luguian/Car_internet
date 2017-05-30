package com.lu.car_internet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ErrorDao {
	private SQLiteDatabase dbExam;

	public ErrorDao(Context context) {
		DBHelperForError helper = new DBHelperForError(context);
		dbExam = helper.getWritableDatabase();
	}

	public long insert(String table, ContentValues values) {
		return dbExam.insert(table, null, values);
	}

	public int delete(String table, String whereClause, String[] whereArgs) {
		return dbExam.delete(table, whereClause, whereArgs);
	}

	public Cursor select(String table, String[] columns) {
		return dbExam.query(table, columns, null, null, null, null, null, null);
	}

	public void clear() {
		String sql = "DELETE FROM exam ;";
		dbExam.execSQL(sql);
	}

	public void deleteAfterPressPrevious(int nextQuestionIndex) {
		String sql = "DELETE FROM exam WHERE indexQuestion='"
				+ nextQuestionIndex + "'";
		dbExam.execSQL(sql);
	}
}
