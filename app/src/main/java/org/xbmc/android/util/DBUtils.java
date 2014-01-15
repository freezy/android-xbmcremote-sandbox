package org.xbmc.android.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteStatement;
import org.codehaus.jackson.node.ObjectNode;

public class DBUtils {

	public static void bind(SQLiteStatement insert, ContentValues value, int index, String field) {
		final String v = value.getAsString(field);
		if (v != null) {
			insert.bindString(index, v);
		}
	}


	public static int getIntValue(ObjectNode node, String attr) {
		if (!node.has(attr)) {
			return -1;
		}
		if (node.get(attr).isArray()) {
			if (node.get(attr).size() > 0) {
				return node.get(attr).get(0).getIntValue();
			} else {
				return -1;
			}
		}
		return node.get(attr).getIntValue();
	}

	public static String[] args(long... args) {
		final String[] ints = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			ints[i] = String.valueOf(args[i]);
		}
		return ints;
	}
}
