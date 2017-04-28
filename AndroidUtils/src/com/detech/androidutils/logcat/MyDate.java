package com.detech.androidutils.logcat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class MyDate {  
	
	private static String fileNameParrten = "yyyy-MM-dd-HH";
	private static String contentParrten = "yyyy-MM-dd HH:mm:ss";
	
	public static void setFileNameParrten(String formatParrten){
		fileNameParrten = formatParrten;
	}
	
	public static void setContentParrten(String contentParrten){
		MyDate.contentParrten = contentParrten;
	}
	
    public static String getFileName() {  
        SimpleDateFormat format = new SimpleDateFormat(fileNameParrten);  
        format.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String date = format.format(new Date(System.currentTimeMillis()));  
        return date;//  
    }  
  
    public static String getDateEN() {  
        SimpleDateFormat format1 = new SimpleDateFormat(contentParrten);  
        String date1 = format1.format(new Date(System.currentTimeMillis()));  
        return date1;//  
    }   
} 