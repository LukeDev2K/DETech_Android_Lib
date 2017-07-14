package com.detect.androidutils.custom;

import android.util.Log;

/**
 * 自定义log工具
 *
 * @author Luke O
 *
 */
public class LogUtil {

	private static final String TAG = "--LogUtil->>>>";

	public static final int VERBOSE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int NOTHING = 6;

	private static int LEVEL = VERBOSE;

	public static void setLevel(int level) {
		LEVEL = level;
	}

	public static void v(String tag, String msg) {
		if (LEVEL <= VERBOSE) {
			Log.v(Thread.currentThread().getId() + TAG + tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (LEVEL <= DEBUG) {
			Log.d(Thread.currentThread().getId() + TAG + tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (LEVEL <= INFO) {
			Log.i(Thread.currentThread().getId() + TAG + tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (LEVEL <= WARN) {
			Log.w(Thread.currentThread().getId() + TAG + tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (LEVEL <= ERROR) {
			Log.e(Thread.currentThread().getId() + TAG + tag, msg);
		}
	}
}
