package com.detech.androidutils.network;

import android.database.ContentObserver;

public class DownloadInfo {
	
	private ContentObserver observer;
	private long id;
	private String link;
	
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
}
