package com.detech.androidport;

import java.io.IOException;
import java.security.InvalidParameterException;

import com.detect.androidutils.custom.LogUtil;
import com.detect.androidutils.custom.MyFunc;

import android.content.Context;
import android_serialport_api.SerialHelper;

public class MySerialPort extends MyPort implements MyPort.ICallback{

	private final static String TAG = "MySerialPort";
 
	private SerialControl myCom;//  
	
	@Override
	public void setContext(Context context) {
		LogUtil.i(TAG, "SETCONTEXT SUCCESS: " + context);
	}

	public int open(String portName, int baudrate){
		LogUtil.i(TAG, "baudrate: " + baudrate + " name: " + portName);
		String port = "/dev/" + portName;
		myCom = new SerialControl(port, baudrate);//  
		return openComPort(myCom);
	}
	
	@Override
	public void sendBytes(byte[] cmdBytes){
		if(myCom != null){
			myCom.send(cmdBytes);
		}
	}
	
	@Override
	public void sendHex(String hexStr){
		if(hexStr == null || hexStr == ""){
			LogUtil.e(TAG, "PARAMETER ERROR");
			return;
		}
		if(myCom != null){
			myCom.sendHex(hexStr);
		}
	}
	
	@Override
	public void receive(byte[] receiveData) {
//		Log.i(TAG, "RECEIVE BYTES: " + MyFunc.bytesToHexString(receiveData));
	}

	@Override
	public void close() {
		if(myCom != null){
			myCom.close();
			LogUtil.i(TAG, "CLOSE PORT");
		}
	}
 
	@Override
	public boolean isOpen() {
		if(myCom != null){
			return myCom.isOpen();
		}
		return false;
	}

	private int openComPort(SerialHelper comPort) {
		try {
			comPort.open(myCom.getPort(), myCom.getBaudRate());
			return SUCCESS;
		} catch (SecurityException e) {
			LogUtil.e(TAG, comPort.getPort() + "SecurityException!" + e.getMessage());
			return FAIL_PERMISSION_DENIED;
		} catch (IOException e) {
			LogUtil.e(TAG, comPort.getPort() + "IOException!" +e.getMessage());
			return FAIL_UNKOWN;
		} catch (InvalidParameterException e) {
			LogUtil.e(TAG, comPort.getPort() + "InvalidParameterException!" + e.getMessage());
			return FAIL_PARAMETER_ERROR;
		}
	}

	private class SerialControl extends SerialHelper { 
		
		private ICallback callback;

		public SerialControl(String port, int baudrate) {
			setPort(port);
			setBaudRate(baudrate);
			callback = new MySerialPort();
		}

		@Override
		protected void onDataReceived(byte[] buffer, int size) {
			buffer = MyFunc.subBytes(buffer, 0, size);
			bufferQueue.add(buffer);
//			Log.i(TAG, "RECEIVE BYTES: " + MyFunc.bytesToHexString(buffer));
			if(callback != null){
				callback.receive(buffer);
			}
		}
	}
}
