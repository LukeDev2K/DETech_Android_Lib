package com.detech.androidutils.network;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.detech.androidutils.FileOperation;
import com.detect.androidutils.custom.LogUtil;
import com.detect.androidutils.custom.MyFunc;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

public class DownloadUtil {

	private static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
	private static final String TAG = "DownloadController";
	private static final String DOWNLOAD_SUFFIX = "_downloading";//下载文件的后缀

	public static final int FAIL_NETWORK_INAVAILABLE		= 1001;
	public static final int FAIL_COMPONENT_INAVAILABLE 	= 1002;
	public static final int FAIL_WRONG_ADDRESS 			= 1003;
	public static final int FAIL_ALREADY_DOWNLOADED	 	= 1004;

	private static DownloadUtil _instance;

	private Context context;
	private String floder;
	private DownloadManager downloadManager;
	private List<DownloadInfo> downloadList = new ArrayList<DownloadInfo>();
	
	private boolean underAppFloder;//是否在app目录下
	private boolean allowedOverRoaming;//是否允许漫游

	public static DownloadUtil getInst() {
		if (_instance == null) {
			_instance = new DownloadUtil();
		}
		return _instance;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setFloder(String floder, boolean underAppFloder) {
		this.floder = floder;
		this.underAppFloder = underAppFloder;
	}
	
	public void setAllowedOverRoaming(boolean allowedOverRoaming) {
		this.allowedOverRoaming = allowedOverRoaming;
	}

	/**
	 * 下载文件
	 * 
	 * @param link
	 * @param callback
	 * @return
	 */
	public void start(String link, IDownloadStatusCallback callback) {
		if(!checkDownloadAction(link, callback)) return;
		
		if (downloadManager == null) {
			downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		} 
		Request request = new DownloadManager.Request(Uri.parse(link));
		String originFileName = getFileName(link);
		String temporaryFileName = setFileName2Temporary(originFileName, DOWNLOAD_SUFFIX); //临时文件名
		if(MyFunc.isNullOrEmpty(temporaryFileName)) return;
		String temporaryFilePath = "";//临时下载文件路径
		String originFilePath = "";//原始文件路径
		if(underAppFloder){
			temporaryFilePath = context.getExternalFilesDir(floder) + "/" + temporaryFileName;
			originFilePath = context.getExternalFilesDir(floder) + "/" + originFileName;
			request.setDestinationInExternalFilesDir(context, floder, temporaryFileName);
		}else{
			temporaryFilePath = Environment.getExternalStoragePublicDirectory(floder) + "/" + temporaryFileName;
			originFilePath = Environment.getExternalStoragePublicDirectory(floder) + "/" + originFileName;
			request.setDestinationInExternalPublicDir(floder, temporaryFileName);
		}

		DownloadInfo info = new DownloadInfo();
		info.setLink(link);
		info.setTemporaryFilePath(temporaryFilePath);
		info.setOriginFilePath(originFilePath);
		//查找有没有临时文件， 有的话就删掉下载， 没有就直接下载
		File file = new File(temporaryFilePath);
		if(file.exists()){
			if (FileOperation.deleteFile(new File(temporaryFilePath))) {
				LogUtil.i(TAG, "成功删除: " + temporaryFilePath);
				doDownloadAction(request, info, link, callback);
				return;
			}
			LogUtil.e(TAG, "不成功删除: " + temporaryFilePath);
			removeDownloadInfo(info);
		}else{
			doDownloadAction(request, info, link, callback);
		}
	}
	
	/**
	 * 开始下载的动作
	 * @param request
	 * @param filepath
	 * @param link
	 * @param callback
	 */
	private void doDownloadAction(Request request,DownloadInfo info, String link, IDownloadStatusCallback callback){
		request.setAllowedOverRoaming(allowedOverRoaming);
		long downloadId = downloadManager.enqueue(request);
		info.setId(downloadId);
		DownloadChangeObserver downloadChangeObserver = new DownloadChangeObserver(null, info, callback);
		context.getContentResolver().registerContentObserver(CONTENT_URI, true, downloadChangeObserver);
	}
	
	private boolean checkDownloadAction(String link, IDownloadStatusCallback callback){
		if (!Network.isAvailable(context)) {
			LogUtil.e(TAG, "网络不可用");
			if (callback != null) {
				callback.onFail(FAIL_NETWORK_INAVAILABLE);
			}
			return false;
		}
		if (!isComponentAvailable(context)) {
			LogUtil.e(TAG, "下载组件不可用");
			if (callback != null) {
				callback.onFail(FAIL_COMPONENT_INAVAILABLE);
			}
			openDownloadComponent(context);
			return false;
		}
		if (link == "" || link == null) {
			LogUtil.e(TAG, "地址出错");
			if (callback != null) {
				callback.onFail(FAIL_WRONG_ADDRESS);
			}
			return false;
		}
		for (DownloadInfo downloadInfo : downloadList) {
			if(downloadInfo.getLink().equals(link)){
				Log.w(TAG, "ALREADY DOWNLOADING: " + link);
				if(callback != null){
					callback.onFail(FAIL_ALREADY_DOWNLOADED);
				}
				return false;
			}
		}
		return true;
	}

	private String getFileName(String link) {
		String fName = link.trim();
		String fileName = fName.substring(fName.lastIndexOf("/") + 1);
		return fileName;
	}
	
	/**
	 * 把名字设置成临时名字
	 * @param fileName
	 * @param suffix
	 * @return
	 */
	private String setFileName2Temporary(String fileName, String suffix){
		if(MyFunc.isNullOrEmpty(fileName)) return null;
		try{
			String fileFormat = fileName.substring(fileName.lastIndexOf("."));//文件格式
			String name = fileName.substring(0, fileName.lastIndexOf("."));//文件名
			return name + suffix + fileFormat;
		}catch(NullPointerException ne){
			ne.printStackTrace();
		}catch (IndexOutOfBoundsException ie) {
			ie.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检查下载状态
	 * @param info
	 * @param callback
	 */
	private void queryDownloadStatus(DownloadInfo info, IDownloadStatusCallback callback) {
		if(info == null) return;
		if(downloadManager == null) return;
		DownloadManager.Query query = new DownloadManager.Query();
		query.setFilterById(info.getId());
		Cursor cursor = null;
		try {
			cursor = downloadManager.query(query);
			if (cursor != null && cursor.moveToFirst()) {
				int titleIdx = cursor.getColumnIndex(DownloadManager.COLUMN_TITLE);
				int reasonIdx = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
				int fileSizeIdx = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);//
				int bytesDLIdx = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);// 目前的下载大小

				String title = cursor.getString(titleIdx); 
				int fileSize = cursor.getInt(fileSizeIdx);
				int bytesDL = cursor.getInt(bytesDLIdx);
				int reason = cursor.getInt(reasonIdx);// 这个值分析下载和暂停的原因，

				StringBuilder builder = new StringBuilder();
				builder.append(title).append("\n");
				builder.append("Downloaded ").append(bytesDL).append("/").append(fileSize);
				if (reason == 0) {
					if (callback != null) {
						callback.onStatus(info, title, bytesDL, fileSize);
						if (bytesDL == fileSize) {
							context.getContentResolver().unregisterContentObserver(info.getObserver());
							File file = new File(info.getTemporaryFilePath());
							if(file.renameTo(new File(info.getOriginFilePath()))){
								LogUtil.i(TAG, "重命名成功： " + info.getOriginFilePath());
							}
							if(downloadList.contains(info)){
								LogUtil.i(TAG, "下载完成移除Info");
								downloadList.remove(info);
							}
							callback.onSuccess(info);
						}
					}
				} else {
					if (callback != null) {
						callback.onFail(reason);
						removeDownloadInfo(info);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
	}
	
	/**
	 * 移除下载信息
	 * @param downloadId
	 */
	public void removeDownloadInfo(long downloadId){
		for (DownloadInfo downloadInfo : downloadList) {
			if(downloadId == downloadInfo.getId()){
				removeDownloadInfo(downloadInfo);
				break;
			}
		}
	}
	
	/**
	 * 移除下载信息
	 * @param info
	 */
	private void removeDownloadInfo(DownloadInfo info){
		if(info == null) return;
		if(context == null) return;
		context.getContentResolver().unregisterContentObserver(info.getObserver());
		if(downloadManager != null){
			downloadManager.remove(info.getId());
		}
		if(downloadList.contains(info)){
			if(downloadList.remove(info)){
				LogUtil.i(TAG, "移除id: " + info.getId());
			}else{
				LogUtil.w(TAG, "没有移除id: " + info.getId());
			}
		}
		String filepath = info.getTemporaryFilePath();
		if (FileOperation.deleteFile(new File(filepath))) {
			LogUtil.i(TAG, "成功删除: " + filepath);
		} else {
			LogUtil.e(TAG, "不成功删除: " + filepath);
		}
	}
	
	private void removeAllDownloadInfo(){
		try {
			for (DownloadInfo downloadInfo : downloadList) {
				removeDownloadInfo(downloadInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 观察下载信息
	 * @author Luke O
	 *
	 */
	private class DownloadChangeObserver extends ContentObserver {

		private DownloadInfo info;
		private IDownloadStatusCallback downloadCompletedCallback;

		public DownloadChangeObserver(Handler handler, DownloadInfo info, IDownloadStatusCallback callback) {
			super(handler);
			info.setObserver(this);
			LogUtil.i(TAG, "id: " + info.getId() + " link: " + info.getLink());
			downloadList.add(info);
			this.info = info;
			downloadCompletedCallback = callback;
		}

		@Override
		public void onChange(boolean selfChange) {
			queryDownloadStatus(info, downloadCompletedCallback);
		}
	}

	/**
	 * 下载组件是否可用
	 * 
	 * @param context
	 * @return
	 */
	private boolean isComponentAvailable(Context context) {
		try {
			int state = context.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

			if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
					|| state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
					|| state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 打开下载管理器
	 * 
	 * @param context
	 */
	private void openDownloadComponent(Context context) {
		String packageName = "com.android.providers.downloads";
		Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(Uri.parse("package:" + packageName));
		context.startActivity(intent);
	}

	public void dispose() {
		removeAllDownloadInfo();
		if (downloadList != null) {
			downloadList.clear();
			downloadList = null;
		}
		if (_instance != null) {
			_instance = null;
		}
		if (context != null) {
			context = null;
		}
		if (downloadManager != null) {
			downloadManager = null;
		}
	}

	/**
	 * 下载状态回调
	 * 
	 * @author Luke O
	 *
	 */
	public interface IDownloadStatusCallback {
		/**
		 * 正在下载信息
		 * 
		 * @param info
		 * 			     下载的info
		 * @param title
		 *            下载的标题
		 * @param currentBytes
		 *            当前字节
		 * @param totalBytes
		 *            总字节
		 */
		void onStatus(DownloadInfo info, String title, int currentBytes, int totalBytes);

		/**
		 * 下载完成
		 * @param info
		 *            下载信息
		 */
		void onSuccess(DownloadInfo info);

		/**
		 * 下载失败
		 * 
		 * @param reason
		 *            这个值分析失败的原因
		 */
		void onFail(int reason);
	}
}
