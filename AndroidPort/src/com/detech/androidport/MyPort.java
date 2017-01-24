package com.detech.androidport;

import java.util.LinkedList;
import java.util.List;

import com.detect.androidutils.custom.MyFunc;

import android.content.Context;

public abstract class MyPort {

	public static final int SUCCESS 				=  100;
	public static final int PERMISSION_REQUESTING 	=  101;
	public static final int FAIL_PERMISSION_DENIED 	=  102;
	public static final int FAIL_UNKOWN 			=  103;
	public static final int FAIL_PARAMETER_ERROR 	=  104;
	public static final int FAIL_NULL_OBJECT 		=  105;

	public static final String ACTION_USB_ATTACHED 					= "android.hardware.usb.action.USB_DEVICE_ATTACHED";
	public static final String ACTION_USB_DETACHED 					= "android.hardware.usb.action.USB_DEVICE_DETACHED";
	public static final String ACTION_USB_READY 					= "com.detech.connectivityservices.USB_READY";
	public static final String ACTION_USB_NOT_SUPPORTED 			= "com.detech.usbservice.USB_NOT_SUPPORTED";
	public static final String ACTION_NO_USB 						= "com.detech.usbservice.NO_USB";
	public static final String ACTION_USB_PERMISSION_GRANTED 		= "com.detech.usbservice.USB_PERMISSION_GRANTED";
	public static final String ACTION_USB_PERMISSION_NOT_GRANTED 	= "com.detech.usbservice.USB_PERMISSION_NOT_GRANTED";
	public static final String ACTION_USB_DISCONNECTED 				= "com.detech.usbservice.USB_DISCONNECTED";
	public static final String ACTION_CDC_DRIVER_NOT_WORKING		= "com.detech.connectivityservices.ACTION_CDC_DRIVER_NOT_WORKING";
	public static final String ACTION_USB_DEVICE_NOT_WORKING 		= "com.detech.connectivityservices.ACTION_USB_DEVICE_NOT_WORKING";
	public static final String ACTION_USB_PERMISSION 				= "com.android.detech.USB_PERMISSION";
	
	public static final int CTS_CHANGE = 1;
	public static final int DSR_CHANGE = 2;
	
	public static final int MAX_QUEUE_NUM = 12;//最大缓存的数据
	
//	protected Queue<byte[]> bufferQueue = new LinkedList<byte[]>();
	protected List<byte[]> bufferList = java.util.Collections.synchronizedList(new LinkedList<byte[]>());
	protected int readDelayTime = 15; 
	
	public abstract void setContext(Context context);
	
	public abstract int open(String com, int baudrate);
	
	public abstract void sendBytes(byte[] cmdBytes);

	public abstract void sendHex(String hex);

	public abstract void close();

	public abstract boolean isOpen();
	
	public void setReadDelay(int delayTime){
		readDelayTime = delayTime;
	}
 
	public byte[] receive() {
		byte[] buffer =  new byte[] {};
//		if (bufferQueue.size() <= 0)
//			return buffer;
//		while (bufferQueue.size() > 0) {
//			byte[] b = bufferQueue.poll();
//			LogUtil.w("", MyFunc.ByteArrToHex(b));
//			buffer = MyFunc.byteMerger(buffer, b);
//		}
		if(bufferList.size() <= 0){
			return buffer;
		}
		while(bufferList.size() > 0){
			try{
				byte[] b = bufferList.remove(0);
//				LogUtil.w("", MyFunc.ByteArrToHex(b));
				buffer = MyFunc.byteMerger(buffer, b);
			}catch(IndexOutOfBoundsException ie){
				ie.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return buffer;
	}

	public interface ICallback {
		void receive(byte[] receiveData);
	}
}
