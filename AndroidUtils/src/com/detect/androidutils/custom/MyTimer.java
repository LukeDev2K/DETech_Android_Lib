package com.detect.androidutils.custom;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyTimer {
	
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	private int t;
	private boolean start;
	
	public MyTimer(int delayTime, final ScheduleCallback callback) {
		try {
			executorService.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					if (start) {
						t++;
						if (callback != null) {
							callback.onTimeChange(t);
						}
					}
				}
			}, 0, delayTime, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		start = false; 
	}
	
	public void start(){
		start = true; 
	}
	
	public void cancel(){
		start = false; 
		if(executorService != null){
			executorService.shutdown();
			executorService = null;
		}
	}
	
	public interface ScheduleCallback{
		void onTimeChange(int currentTime);
	}
}
