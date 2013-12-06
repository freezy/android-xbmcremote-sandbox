package org.xbmc.android.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteStatement;

public class DBUtils {

	public static void bind(SQLiteStatement insert, ContentValues value, int index, String field) {
		final String v = value.getAsString(field);
		if (v != null) {
			insert.bindString(index, v);
		}
	}
}
