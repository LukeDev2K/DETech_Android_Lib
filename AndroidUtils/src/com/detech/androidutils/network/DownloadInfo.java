package com.detech.androidutils.network;

import android.database.ContentObserver;

public class DownloadInfo {
	
	private ContentObserver observer;
	private long id;
	private String link;
	private String temporaryFilePath;//临时的文件路径
	private String originFilePath;//真正的文件路径
	
	public ContentObserver getObserver() {
		return observer;
	}
	
	public void setObserver(ContentObserver observer) {
		this.observer = observer;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	} 

	public String getOriginFilePath() {
		return originFilePath;
	}

	public void setOriginFilePath(String originFilePath) {
		this.originFilePath = originFilePath;
	}

	public String getTemporaryFilePath() {
		return temporaryFilePath;
	}

	public void setTemporaryFilePath(String temporaryFilePath) {
		this.temporaryFilePath = temporaryFilePath;
	}
}
