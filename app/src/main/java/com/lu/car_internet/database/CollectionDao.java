package com.lu.car_internet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CollectionDao {
	private SQLiteDatabase dbCollection;

	public CollectionDao(Context context) {
		DBHelperForCollection helper = new DBHelperForCollection(context);
		dbCollection = helper.getWritableDatabase();
	}

	public long insert(String table, ContentValues values) {
		return dbCollection.insert(table, null, values);
	}

	public int delete(String table, String whereClause, String[] whereArgs) {
		return dbCollection.delete(table, whereClause, whereArgs);
	}

	public Cursor select(String table, String[] columns) {
		return dbCollection.query(table, columns, null, null, null, null, null,
				null);
	}

}
