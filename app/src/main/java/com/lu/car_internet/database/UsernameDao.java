package com.lu.car_internet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsernameDao {

	private SQLiteDatabase dbUsername;

	public UsernameDao(Context context) {
		DBHelperForUsername helper = new DBHelperForUsername(context);
		dbUsername = helper.getWritableDatabase();
	}

	public long insert(String table, ContentValues values) {
		return dbUsername.insert(table, null, values);
	}

	public int delete(String table, String whereClause, String[] whereArgs) {
		return dbUsername.delete(table, whereClause, whereArgs);
	}

	public Cursor select(String table, String[] columns) {
		return dbUsername.query(table, columns, null, null, null, null, null,
				null);
	}

	public void clear() {
		String sql = "DELETE FROM username ;";
		dbUsername.execSQL(sql);
	}
}
