package com.detech.androidutils.network;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.detech.androidutils.FileOperation;
import com.detect.androidutils.custom.LogUtil;

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

public class DownloadController {

	private static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
	private static final String TAG = "DownloadController";

	private static final int FAIL_NETWORK_INAVAILABLE		= 1001;
	private static final int FAIL_COMPONENT_INAVAILABLE 	= 1002;
	private static final int FAIL_WRONG_ADDRESS 			= 1003;
	private static final int FAIL_ALREADY_DOWNLOADED	 	= 1004;

	private static DownloadController _instance;

	private Context context;
	private String floder;
	private DownloadManager downloadManager;
	private List<DownloadInfo> downloadList = new ArrayList<DownloadInfo>();
	
	private boolean underAppFloder;//是否在app目录下
	private boolean allowedOverRoaming;//是否允许漫游

	public static DownloadController getInst() {
		if (_instance == null) {
			_instance = new DownloadController();
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
	public long download(String link, IDownloadStatusCallback callback) {
		if (!Network.isAvailable(context)) {
			LogUtil.e(TAG, "网络不可用");
			if (callback != null) {
				callback.onFail(FAIL_NETWORK_INAVAILABLE);
			}
			return FAIL_NETWORK_INAVAILABLE;
		}
		if (!isComponentAvailable(context)) {
			LogUtil.e(TAG, "下载组件不可用");
			if (callback != null) {
				callback.onFail(FAIL_COMPONENT_INAVAILABLE);
			}
			openDownloadComponent(context);
			return FAIL_COMPONENT_INAVAILABLE;
		}
		if (link == "" || link == null) {
			LogUtil.e(TAG, "地址出错");
			if (callback != null) {
				callback.onFail(FAIL_WRONG_ADDRESS);
			}
			return FAIL_WRONG_ADDRESS;
		}
		if (downloadManager == null) {
			downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		}
		for (DownloadInfo downloadInfo : downloadList) {
			if(downloadInfo.getLink().equals(link)){
				Log.w(TAG, "ALREADY DOWNLOADING: " + link);
				if(callback != null){
					callback.onFail(FAIL_ALREADY_DOWNLOADED);
				}
				return FAIL_ALREADY_DOWNLOADED;
			}
		}
		if (FileOperation.createFloder(floder))
			LogUtil.i(TAG, "成功创建文件夹");
		Request request = new DownloadManager.Request(Uri.parse(link));
		String fileName = getFileName(link);
		if(underAppFloder){
			request.setDestinationInExternalFilesDir(context, floder, fileName);
		}else{
			request.setDestinationInExternalPublicDir(floder, fileName);
		}
		request.setAllowedOverRoaming(allowedOverRoaming);
		long downloadId = downloadManager.enqueue(request);
		DownloadInfo info = new DownloadInfo();
		info.setId(downloadId);
		info.setLink(link);
		DownloadChangeObserver downloadChangeObserver = new DownloadChangeObserver(null, info, callback);
		context.getContentResolver().registerContentObserver(CONTENT_URI, true, downloadChangeObserver);
		
		return downloadId;
	}

	private String getFileName(String link) {
		String fName = link.trim();
		String fileName = fName.substring(fName.lastIndexOf("/") + 1);
		return fileName;
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
				String filepath = context.getExternalFilesDir(floder) + "/" + title;
				if(!underAppFloder){
					filepath = Environment.getExternalStoragePublicDirectory(floder) + "/" + title;
				}
				int fileSize = cursor.getInt(fileSizeIdx);
				int bytesDL = cursor.getInt(bytesDLIdx);
				int reason = cursor.getInt(reasonIdx);// 这个值分析下载和暂停的原因，

				StringBuilder builder = new StringBuilder();
				builder.append(title).append("\n");
				builder.append("Downloaded ").append(bytesDL).append("/").append(fileSize);
				if (reason == 0) {
					if (callback != null) {
						callback.onStatus(title, bytesDL, fileSize);
						if (bytesDL == fileSize) {
							context.getContentResolver().unregisterContentObserver(info.getObserver());
							if(downloadList.contains(info)){
								LogUtil.i(TAG, "下载完成移除Info");
								downloadList.remove(info);
							}
							callback.onSuccess(info.getId(), filepath);
						}
					}
				} else {
					if (callback != null) {
						callback.onFail(reason);
					}
					removeDownloadInfo(info);
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
		String filepath = context.getExternalFilesDir(floder) + "/" + getFileName(info.getLink());
		if(!underAppFloder){
			filepath = Environment.getExternalStoragePublicDirectory(floder) + "/" + getFileName(info.getLink());
		}
		if (FileOperation.deleteFile(new File(filepath))) {
			LogUtil.i(TAG, "成功删除: " + filepath);
		} else {
			LogUtil.e(TAG, "不成功删除: " + filepath);
		}
	}
	
	private void removeAllDownloadInfo(){
		for (DownloadInfo downloadInfo : downloadList) {
			removeDownloadInfo(downloadInfo);
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
		 * @param title
		 *            下载的标题
		 * @param currentBytes
		 *            当前字节
		 * @param totalBytes
		 *            总字节
		 */
		void onStatus(String title, int currentBytes, int totalBytes);

		/**
		 * 下载完成
		 * 
		 * @param downloadId
		 *            下载的id
		 * @param filepath
		 *            文件路径
		 */
		void onSuccess(long downloadId, String filepath);

		/**
		 * 下载失败
		 * 
		 * @param reason
		 *            这个值分析失败的原因
		 */
		void onFail(int reason);
	}
}
