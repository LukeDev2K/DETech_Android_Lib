package android_serialport_api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import com.detect.androidutils.custom.LogUtil;
import com.detect.androidutils.custom.MyFunc;


public abstract class SerialHelper{
	
	private static final String TAG = "SerialHelper";
	
	private SerialPort mSerialPort;
	private OutputStream mOutputStream;
	private InputStream mInputStream;
	private ReadThread mReadThread;
	private SendThread mSendThread;
	private String sPort="/dev/ttyS0";
	private int iBaudRate=115200;
	private boolean _isOpen=false;
	private byte[] _bLoopData=new byte[]{0x30};
	private int iDelay=500;
	private int rDelay = 15;//读取延时
	 
	//----------------------------------------------------
	public void open(String port, int baudRate, int readDelay) throws SecurityException, IOException,InvalidParameterException{
		sPort = port;
		iBaudRate = baudRate;
		rDelay = readDelay;
		LogUtil.i(TAG, "readDelayTime: " + rDelay);
		mSerialPort =  new SerialPort(new File(sPort), iBaudRate, 0);
		mOutputStream = mSerialPort.getOutputStream();
		mInputStream = mSerialPort.getInputStream();
		mReadThread = new ReadThread();
		mReadThread.start();
//		mSendThread = new SendThread();
//		mSendThread.setSuspendFlag();
//		mSendThread.start();
		_isOpen=true;
	}
	//----------------------------------------------------
	public void close(){
		try {
			if (mReadThread != null)
				mReadThread.interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (mSerialPort != null) {
			mSerialPort.close();
			mSerialPort = null;
		}
		_isOpen=false;
	}
	//----------------------------------------------------
	public void send(byte[] bOutArray){
		try{
			if(mOutputStream == null) return;
			mOutputStream.write(bOutArray);
//			Log.i("SerialHelper",MyFunc.ByteArrToHex(bOutArray));
		} catch (IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//----------------------------------------------------
	public void sendHex(String sHex){
		byte[] bOutArray = MyFunc.HexToByteArr(sHex); 
		send(bOutArray);		
	}
	//----------------------------------------------------
	public void sendTxt(String sTxt){
		byte[] bOutArray =sTxt.getBytes();
		send(bOutArray);		
	} 
	
	public byte[] readByte(){
		if (mInputStream == null) return new byte[]{};
		int size;
		try {
			byte[] buffer=new byte[20];
			size = mInputStream.read(buffer);
			if (size > 0){
				return buffer;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new byte[]{};
	}
	
	//----------------------------------------------------
	private class ReadThread extends Thread {
		
		@Override
		public void run() {
			super.run();
			while(!isInterrupted()) {
				try
				{
					if (mInputStream == null) return;
					byte[] buffer=new byte[20];
					int size = mInputStream.read(buffer);
					
					onDataReceived(buffer, size);
					try
					{
						Thread.sleep(rDelay);//  
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					} 
				} catch (Throwable e)
				{
					e.printStackTrace();
					return;
				}
			}
		}
	}
	//----------------------------------------------------
	private class SendThread extends Thread{
		public boolean suspendFlag = true; 
		@Override
		public void run() {
			super.run();
			while(!isInterrupted()) {
				synchronized (this)
				{
					while (suspendFlag)
					{
						try
						{
							wait();
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}
				send(getbLoopData());
				try
				{
					Thread.sleep(iDelay);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}

		public void setSuspendFlag() {
		this.suspendFlag = true;
		}
		
		public synchronized void setResume() {
		this.suspendFlag = false;
		notify();
		}
	}
	//----------------------------------------------------
	public int getBaudRate()
	{
		return iBaudRate;
	}
	public boolean setBaudRate(int iBaud)
	{
		if (_isOpen)
		{
			return false;
		} else
		{
			iBaudRate = iBaud;
			return true;
		}
	}
	public boolean setBaudRate(String sBaud)
	{
		int iBaud = Integer.parseInt(sBaud);
		return setBaudRate(iBaud);
	}
	//----------------------------------------------------
	public String getPort()
	{
		return sPort;
	}
	public boolean setPort(String sPort)
	{
		if (_isOpen)
		{
			return false;
		} else
		{
			this.sPort = sPort;
			return true;
		}
	}
	//----------------------------------------------------
	public boolean isOpen()
	{
		return _isOpen;
	}
	//----------------------------------------------------
	public byte[] getbLoopData()
	{
		return _bLoopData;
	}
	//----------------------------------------------------
	public void setbLoopData(byte[] bLoopData)
	{
		this._bLoopData = bLoopData;
	}
	//----------------------------------------------------
	public void setTxtLoopData(String sTxt){
		this._bLoopData = sTxt.getBytes();
	}
	//----------------------------------------------------
	public void setHexLoopData(String sHex){
		this._bLoopData = MyFunc.HexToByteArr(sHex);
	}
	//----------------------------------------------------
	public int getiDelay()
	{
		return iDelay;
	}
	//----------------------------------------------------
	public void setiDelay(int iDelay)
	{
		this.iDelay = iDelay;
	}
	//----------------------------------------------------
	public void startSend()
	{
		if (mSendThread != null)
		{
			mSendThread.setResume();
		}
	}
	//----------------------------------------------------
	public void stopSend()
	{
		if (mSendThread != null)
		{
			mSendThread.setSuspendFlag();
		}
	}
	//----------------------------------------------------
	protected abstract void onDataReceived(byte[] buffer,int size);
}