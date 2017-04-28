package com.detech.androidport;


import com.detect.androidutils.custom.LogUtil;

import android.content.Context;

public class PortManager {
	
	private final static String TAG = "PortManager";
	
	public final static int USB_PORT = 1;
	public final static int SERIAL_PORT = 2;
	
	private static PortManager _instance;
	
	private MyPort targetPort;
	
	public static PortManager getInst(){
		if(_instance==null){
			_instance = new PortManager();
		}
		return _instance;
	}
	
	public MyPort createPort(int portParrten){
		if(portParrten == USB_PORT){
			targetPort = new MyUsbPort();
		}else if(portParrten== SERIAL_PORT){
			targetPort = new MySerialPort();
		}else {
			LogUtil.e(TAG, "parameter error");
		}
		return targetPort;
	}
	
	/************************************************For Unity**********************************************************/
	/************************************************For Unity**********************************************************/
	/************************************************For Unity**********************************************************/
	/************************************************For Unity**********************************************************/
	public void initPort(int portType){
		LogUtil.i(TAG, "type: " + portType);
		if(portType == USB_PORT){
			targetPort = new MyUsbPort();
		}else if(portType== SERIAL_PORT){
			targetPort = new MySerialPort();
		}else {
			LogUtil.e(TAG, "parameter error");
		}
	}
	
	public void setContext(Context context){
		if(targetPort!=null){
			targetPort.setContext(context);
		}
	}
	
	public void setReadDelay(int delay){
		if(targetPort != null){
			targetPort.setReadDelay(delay);
		}
	}
	
	public int open(String com, int baudrate){
		if(targetPort!=null){
			return targetPort.open(com, baudrate);
		}
		return MyPort.FAIL_UNKOWN;
	}
	
	public void sendBytes(byte[] cmdBytes){
		if(targetPort!=null){
			targetPort.sendBytes(cmdBytes);
		}
	}
	
	public void sendHex(String hex){
		if(targetPort!=null){
			targetPort.sendHex(hex);
		}
	}
	
	public byte[] receive(){
		if(targetPort != null){
			return targetPort.receive();
		}
		return new byte[]{};
	}
	
	public void close(){
		if(targetPort!=null){
			targetPort.close();
		}
	}
	
	public boolean isOpen(){
		if(targetPort != null){
			return targetPort.isOpen();
		}
		return false;
	}
	/************************************************For Unity**********************************************************/
	/************************************************For Unity**********************************************************/
	/************************************************For Unity**********************************************************/
	/************************************************For Unity**********************************************************/
}
