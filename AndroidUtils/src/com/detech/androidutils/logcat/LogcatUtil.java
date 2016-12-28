package com.detech.androidutils.logcat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.detect.androidutils.custom.LogUtil;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class LogcatUtil {

	private static final String TAG = "LogcatUtil";
	
	private static LogcatUtil INSTANCE = null;
	private static String PATH_LOGCAT;
	private static File PATH_LOGCAT_FILE; // 文件地址

	private LogDumper mLogDumper = null;
	private int mPId;

	private Context context;

	private LogcatUtil() {
		checkFile();
		mPId = android.os.Process.myPid();
		Log.i(TAG, mPId + "");
	}

	public static LogcatUtil getInst() {
		if (INSTANCE == null) {
			INSTANCE = new LogcatUtil();
		}
		return INSTANCE;
	}

	public void setContext(Context context) {
		this.context = context;
		LogUtil.i(TAG, "SET CONTEXT SUCCESS: " + context);
	}

	public void setFloder(String floderName) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
			PATH_LOGCAT = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + floderName;
		} else {// 如果SD卡不存在，就保存到本应用的目录下
			PATH_LOGCAT = context.getFilesDir().getAbsolutePath() + File.separator + floderName;
		}
		File file = new File(PATH_LOGCAT);
		if (!file.exists()) {
			LogUtil.i(TAG, "没有文件夹, 创建: " + PATH_LOGCAT);
			if(file.mkdir()){
				LogUtil.i(TAG, "文件创建成功");
			}else {
				LogUtil.e(TAG, "文件创建失败");
			}
		}else{
			LogUtil.i(TAG, "文件夹已存在: " + PATH_LOGCAT);
		}
	}
	
	/**
	 * 设置文件的命名格式
	 * @param timeParrten
	 */
	public void setFileNameParrten(String timeParrten){
		try {
			MyDate.setFileNameParrten(timeParrten);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置内容的写入格式
	 * 
	 * @param timeParrten
	 */
	public void setContentParrten(String timeParrten){
		try {
			MyDate.setContentParrten(timeParrten);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public void start() {
		if (mLogDumper == null)
			mLogDumper = new LogDumper(String.valueOf(mPId), PATH_LOGCAT);
		mLogDumper.start();
	}

	public void stop() {
		if (mLogDumper != null) {
			mLogDumper.stopLogs();
			mLogDumper = null;
		}
	}
	
	private void checkFile(){
		//TODO 检查文件夹文件, 如果大约多少容量就删除
	}

	private class LogDumper extends Thread {

		private Process logcatProc;
		private BufferedReader mReader = null;
		private boolean mRunning = true;
		String cmds = null;
		private String mPID;
		private FileOutputStream out = null;

		public LogDumper(String pid, String dir) {
			mPID = pid;
			PATH_LOGCAT_FILE = new File(dir, "Log-" + MyDate.getFileName() + ".log");
			LogUtil.i(TAG, "PATH_LOGCAT_FILE: " + PATH_LOGCAT_FILE);
			try {
				out = new FileOutputStream(PATH_LOGCAT_FILE);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/**
			 * 
			 * 日志等级�?*:v , *:d , *:w , *:e , *:f , *:s
			 * 
			 * 显示当前mPID程序�? E和W等级的日�?.
			 * 
			 */

			// cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
			// cmds = "logcat | grep \"(" + mPID + ")\"";//打印�?有日志信�?
			// cmds = "logcat -s way";//打印标签过滤信息
			cmds = "logcat *:e *:i | grep \"(" + mPID + ")\"";

		}

		public void stopLogs() {
			mRunning = false;
			interrupt();
		}

		@Override
		public void run() {
			try {
				logcatProc = Runtime.getRuntime().exec(cmds);
				mReader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()), 1024);
				String line = null;
				while (mRunning && (line = mReader.readLine()) != null) {
					if (!mRunning) {
						break;
					}
					if (line.length() == 0) {
						continue;
					}
					if (out != null && line.contains(mPID)) {
						out.write((MyDate.getDateEN() + "  " + line + "\n").getBytes());
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (logcatProc != null) {
					logcatProc.destroy();
					logcatProc = null;
				}
				if (mReader != null) {
					try {
						mReader.close();
						mReader = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					out = null;
				}
			}
		}
	}
}