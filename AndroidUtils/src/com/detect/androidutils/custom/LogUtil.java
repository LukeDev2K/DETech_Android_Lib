package com.detect.androidutils.custom;

import android.util.Log;

/**
 * 自定义log工具
 *
 * @author Luke O
 *
 */
public class LogUtil {

	private static final String TAG = "-----LogUtil->>>>";

	private static final int VERBOSE 	= 1;
	private static final int DEBUG 		= 2;
	private static final int INFO 		= 3;
	private static final int WARN 		= 4;
	private static final int ERROR 		= 5;
	@SuppressWarnings("unused")
	private static final int NOTHING 	= 6;
	private static final int LEVEL 		= VERBOSE;

	public static void v(String tag, String msg) {
		if (LEVEL <= VERBOSE) {
			Log.v(TAG+tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (LEVEL <= DEBUG) {
			Log.d(TAG+tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (LEVEL <= INFO) {
			Log.i(TAG+tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (LEVEL <= WARN) {
			Log.w(TAG+tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (LEVEL <= ERROR) {
			Log.e(TAG+tag, msg);
		}
	}
}
