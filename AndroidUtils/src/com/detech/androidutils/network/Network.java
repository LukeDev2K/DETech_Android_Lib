package com.detech.androidutils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {

	/**
	 * 网络是否可用
	 * @return
	 */
	public static boolean isAvailable(Context context){
		if(context == null) return false;
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);    
        NetworkInfo info = manager.getActiveNetworkInfo();  
        return (info != null && info.isAvailable());  
	}
}
