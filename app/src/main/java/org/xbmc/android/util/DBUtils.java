package org.xbmc.android.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DBUtils {

	private final static String TAG = DBUtils.class.getSimpleName();

	/// 2014-01-30 13:37:06
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void bind(SQLiteStatement insert, ContentValues value, int index, String field) {
		final String v = value.getAsString(field);
		if (v != null) {
			insert.bindString(index, v);
		}
	}

	public static void bindInt(SQLiteStatement insert, ContentValues value, int index, String field) {
		final Integer v = value.getAsInteger(field);
		if (v != null) {
			insert.bindLong(index, v);
		}
	}

	public static String getStringValue(JsonNode node, String attr) {
		if (!node.has(attr)) {
			return null;
		}
		return node.get(attr).getTextValue();
	}

	public static Double getDoubleValue(JsonNode node, String attr) {
		if (!node.has(attr)) {
			return null;
		}
		return node.get(attr).getDoubleValue();
	}

	public static int getIntValue(JsonNode node, String attr) {
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

	/**
	 * Returns integer value for data like "3,002,300" or null if attribute not found.
	 * @param node Node to retrieve value from
	 * @param attr Attribute name
	 * @return Parsed integer or null if not found or number parse error.
	 */
	public static Integer getMessedUpIntValue(ObjectNode node, String attr) {
		if (!node.has(attr)) {
			return null;
		}
		try {
			return Integer.parseInt(node.get(attr).getTextValue().replaceAll("[^\\d]", ""));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Long getDateValue(ObjectNode node, String attr) {
		if (!node.has(attr)) {
			return null;
		}
		final String d = node.get(attr).getTextValue();
		if (d.isEmpty()) {
			return null;
		}
		try {
			return DATE_FORMAT.parse(d).getTime();
		} catch (ParseException e) {
			Log.w(TAG, "Cannot parse date \"" + node.get(attr).getTextValue() + "\"");
		}
		return null;
	}

	public static String getArrayValue(ObjectNode node, String attr, String separator) {
		if (!node.has(attr)) {
			return null;
		}
		final ArrayNode arr = (ArrayNode)node.get(attr);
		if (arr.size() == 0) {
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		for (JsonNode item : arr) {
			sb.append(item.getTextValue());
			sb.append(separator);
		}
		return sb.toString().substring(0, sb.length() - separator.length());
	}

	public static String[] args(long... args) {
		final String[] ints = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			ints[i] = String.valueOf(args[i]);
		}
		return ints;
	}

	public static String[] args(String... args) {
		return args;
	}
}
