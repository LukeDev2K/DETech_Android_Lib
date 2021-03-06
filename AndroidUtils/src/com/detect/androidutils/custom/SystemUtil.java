package com.detect.androidutils.custom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.detech.androidutils.PackageUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class SystemUtil {
	
	private static final String TAG = "SystemUtil";
	
	private static SystemUtil INSTANCE = null;
	public static SystemUtil getInst() {
		if (INSTANCE == null) {
			INSTANCE = new SystemUtil();
		}
		return INSTANCE;
	}
	
	private Context context;
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	/**
	 * 获取IEMI码
	 * @return
	 */
	@SuppressLint("InlinedApi")
	public String getIEMI() {
		if(context == null) return "";
		TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELECOM_SERVICE);
		return tManager.getDeviceId();
	}
	
	public String getUuid(){
		String id = getSdcardId(0);
		if(MyFunc.isNullOrEmpty(id)){
			LogUtil.w(TAG, "获取EMMC id 出错， 尝试获取Android id");
			id = getAndroidID();
		}
		return id;
	}
	
	/**
	 * 如果Android Pad没有IMEI,用此方法获取设备ANDROID_ID
	 * @return
	 */
	public String getAndroidID() {
		if(context == null) return "";
		return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}
	
	public String getSdcardId(int index) {
		String path = "/sys/block/mmcblk" + index + "/device/cid";
		File file = new File(path);
		if (file.exists()) {
			try {
				BufferedReader CID = new BufferedReader(new FileReader(path));
				String sd_cid = CID.readLine();
				return sd_cid;
			} catch (Exception e1) {
				System.out.println("getSdcardId()-" + e1.getMessage());
			}
		}
		return null;
	}
	
	/**
	 * 获取sim卡码
	 * @return
	 */
	public String getSimSerialNumber() {
		if(context == null) return "";
		String num = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
		if(num !=null)
			return num;
		return "";
	}

	/**
	 * 获得序列号
	 * @return
	 */
	public String getSerialNumber() {
		String num =  android.os.Build.SERIAL;
		if(num !=null)
			return num;
		return "";
	}
	
	/**
	 * 设置震动
	 * @param time
	 */
	public void setVibrator(long time) {
		if(context == null) return;
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(time);
	}
	
	/**
	 * 关机
	 * @param needSysPermission 是否需要系统权限
	 */
	public void shutdown(boolean needSysPermission){
		if(!needSysPermission){
			try {
				Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", "reboot -p" });
				proc.waitFor();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			// Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
			// intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
			// //其中false换成true,会弹出是否关机的确认窗口
			// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// startActivity(intent);
			if(context == null){
				LogUtil.e(TAG, "NULL CONTEXT ON SHUTDOWN METHOD");
				return;
			}
			Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");

			// 源码中"android.intent.action.ACTION_REQUEST_SHUTDOWN“ 就是
			// Intent.ACTION_REQUEST_SHUTDOWN方法
			intent.putExtra("android.intent.extra.KEY_CONFIRM", false);

			// 源码中"android.intent.extra.KEY_CONFIRM"就是 Intent.EXTRA_KEY_CONFIRM方法
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}
	
	/**
	 * 重启
	 * @param needSysPermission 是否需要系统权限
	 */
	public void reboot(boolean needSysPermission){
		if(!needSysPermission){
			try {
				Process proc = Runtime.getRuntime().exec("su -c \"/system/bin/reboot\"");
				proc.waitFor();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			if(context == null){
				LogUtil.e(TAG, "NULL CONTEXT ON SHUTDOWN METHOD");
				return;
			}
			LogUtil.v(TAG, "broadcast->reboot");
			Intent intent2 = new Intent(Intent.ACTION_REBOOT);
			intent2.putExtra("nowait", 1);
			intent2.putExtra("interval", 1);
			intent2.putExtra("window", 0);
			context.sendBroadcast(intent2);
		}
	}
	
	/**
	 * 打开app
	 * @param packageName
	 */
	public void openApp(String packageName){
		if(haveApp(packageName)){
			if(context == null) {
				LogUtil.e(TAG, "NULL CONTEXT");
				return;
			}
			Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			context.startActivity(launchIntent);
		}else{
			LogUtil.e(TAG, "PLS INSTAL " + packageName);
		}
	}
	
	/**
	 * 是否安装app
	 * @param packageName
	 * @return
	 */
	public boolean haveApp(String packageName) {
        PackageInfo packageInfo = null;

        try {
            packageInfo = this.context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException var7) {
            packageInfo = null;
        } finally {
            if(packageInfo == null) {
                return false;
            }

        }

        return true;
    }
	
	/**
	 * 自动安装
	 * @param context
	 * @param filepath
	 * @return
	 * 		0：安装成功
	 * 		1：文件不存在
	 * 		2：发生其他错误
	 */
	public int autoInstall(Context context, String filepath) {
		int tag = PackageUtils.install(context, filepath);
		return tag;
	}
    
    /**
     * 获得当前日期
     * @param template 样式
     * @return
     */
    public String getCurrentDate(String template){
		SimpleDateFormat formatter = new SimpleDateFormat(template, Locale.getDefault());
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		return formatter.format(curDate);
    }
}
