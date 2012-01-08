package org.xbmc.android.jsonrpc.io;

public class ApiException extends Exception {

	public static int MALFORMED_URL = 1000;
	public static int IO_EXCEPTION = 1001;
	public static int UNSUPPORTED_ENCODING = 1002;
	public static int JSON_EXCEPTION = 1003;
	public static int RESPONSE_ERROR = 1004;
	public static int API_ERROR = 1005;

	private int code;

	public ApiException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
