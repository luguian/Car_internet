package com.lu.car_internet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CtjlbDao {
	private SQLiteDatabase dbCtjlb;

	public CtjlbDao(Context context) {
		DBHelperForCtjlb helper = new DBHelperForCtjlb(context);
		dbCtjlb = helper.getWritableDatabase();
	}

	public long insert(String table, ContentValues values) {
		return dbCtjlb.insert(table, null, values);
	}

	public int delete(String table, String whereClause, String[] whereArgs) {
		return dbCtjlb.delete(table, whereClause, whereArgs);
	}

	public Cursor select(String table, String[] columns) {
		return dbCtjlb
				.query(table, columns, null, null, null, null, null, null);
	}

}
