package com.detect.androidutils.custom;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {
	
	private Timer timer;
	private int t;
	private boolean start;
	
	public MyTimer(final int delayTime, final ScheduleCallback callback){
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(start){
					t++;
					if(callback != null){
						callback.onTimeChange(t);
					}
				}
			}
		},0, delayTime);
	}
	
	public void stop(){
		start = false;
		if(timer!=null){
			timer.cancel();
		}
	}
	
	public void start(){
		start = true;
	}
	
	public interface ScheduleCallback{
		void onTimeChange(int currentTime);
	}
}
