package com.detech.androidport;

import com.detech.androidutils.logcat.LogcatUtil;
import com.detect.androidutils.custom.LogUtil;
import com.detect.androidutils.custom.MyFunc;
import com.detect.androidutils.custom.MyTimer;
import com.detect.androidutils.custom.MyTimer.ScheduleCallback;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class TestActivity extends Activity implements OnClickListener{

	private static final String TAG = "TestActivity";
	private MyPort myPort;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		SerialPortManager.getInst().open("ttyS0", 115200);
		LogcatUtil.getInst().setContext(this);
		LogcatUtil.getInst().setFloder("LogcatDebugTest");
		LogcatUtil.getInst().start();
		
//		UsbPortManager.getInst().setContext(this);
//		UsbPortManager.getInst().open(115200);
		myPort = PortManager.getInst().createPort(PortManager.SERIAL_PORT);
		if(myPort != null){
			myPort.setContext(this);
			myPort.open("ttyS0", 115200);
		}
		
		findViewById(R.id.open_btn).setOnClickListener(this);
		findViewById(R.id.send_btn).setOnClickListener(this);
		findViewById(R.id.receive_btn).setOnClickListener(this);
		
		MyTimer t= new MyTimer(20, new ScheduleCallback() {
			
			@Override
			public void onTimeChange(int currentTime) {
				byte[] receiveData = myPort.receive();
				if(receiveData.length>0){
					String data = MyFunc.bytesToHexString(receiveData);
					LogUtil.i(TAG, data);
				}
			}
		});
		t.start();
	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
//		SerialPortManager.getInst().close();
//		UsbPortManager.getInst().close();
		if(myPort != null){
			myPort.close();
		}
		LogcatUtil.getInst().stop();
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.open_btn:
			myPort.open("ttyMT2", 115200);
			break;
		case R.id.receive_btn:
			byte[] receiveData = myPort.receive();
			if(receiveData.length>0){
				String data = MyFunc.bytesToHexString(receiveData);
				LogUtil.i(TAG, data);
			}
			break;
		case R.id.send_btn:
			String tString= "88 0b 01 FF 02 55 01 02 01 65 81 ";
			myPort.sendHex(tString);
			break;
		default:
			break;
		}
	}
}
