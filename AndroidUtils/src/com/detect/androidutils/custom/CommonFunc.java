package com.detect.androidutils.custom;

import android.content.Context;
import android.widget.Toast;

public class CommonFunc {
	
	private static final String TAG = "CommonFunc";

	public static void showMessage(Context context, String msg, int duration){
		if(context==null){
			LogUtil.e(TAG, "null context");
			return;
		}
		Toast.makeText(context, msg, duration).show();
	}
}
