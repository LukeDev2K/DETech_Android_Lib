package com.detech.androidport;

import java.util.HashMap;
import java.util.Map;

import com.detech.usb_api.CDCSerialDevice;
import com.detech.usb_api.UsbSerialDevice;
import com.detech.usb_api.UsbSerialInterface;
import com.detect.androidutils.custom.CommonFunc;
import com.detect.androidutils.custom.LogUtil;
import com.detect.androidutils.custom.MyFunc;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

/**
 * usb 串口管理器
 * @author Luke O
 *
 */
public class MyUsbPort extends MyPort {
	
	private static final String TAG = "UsbPortManager";
	private static int BAUD_RATE = 115200;
	
//	private UsbEndpoint epBulkOut;
//	private UsbEndpoint epBulkIn;
	private UsbManager myUsbManager;
	private UsbDevice usbDevice;
	private UsbDeviceConnection usbConnect;
	private UsbSerialDevice usbPort;
	private Context context;
	private ConnectionThread connThread;
	private boolean serialPortConnected;
	private boolean granted = false;
	
	public MyUsbPort(){ 
	}
	
	@Override
	public void setContext(Context context){
		this.context = context;
		setFilter(context);
		myUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
		LogUtil.i(TAG, "SETCONTEXT SUCCESS: " + context);
	}   
	
	@Override
	public int open(String com, int baudrate){
		LogUtil.i(TAG, "baudrate: " + baudrate + " name: " + com);
		BAUD_RATE = baudrate;
		return findSerialPortDevice();
	} 
	
	@Override
	public void close() {
		if(connThread != null){
			connThread.interrupt();
			connThread = null;
		}
		if(usbReadCallback!=null){
			usbReadCallback = null;
		}
		if(usbPort != null){
			usbPort.close();
		}
		if(usbConnect != null){
			usbConnect.close();
		}
		if(context != null){
			context.unregisterReceiver(usbReceiver);
			context = null;
		}
		serialPortConnected = false;
		LogUtil.d(TAG, "PORT CLOSE");
	}
	
//	@Override
//	public byte[] receive() {
//		if (usbConnect == null) {
//			LogUtil.e(TAG, "DEVICECONNECTION == NULL");
//			return new byte[]{};
//		}
//		if(!isOpen()){
//			LogUtil.e(TAG, "DEVICE NOT OPEN");
//			return new byte[]{};
//		}
//		byte[] buffer = new byte[40];
//		int count = usbConnect.bulkTransfer(epBulkIn, buffer, buffer.length, 20);
//		if (count > 0) {
//			String a = MyFunc.bytesToHexString(buffer);
//			LogUtil.i(TAG, a);
//			return buffer;
//		} 
//		return new byte[]{};
//	}

	@Override
	public void sendBytes(byte[] cmdBytes) {
//		if (usbConnect == null) {
//			return;
//		}
//		if(!isOpen()){
//			LogUtil.e(TAG, "DEIVE NOT OPEN");
//			return;
//		}
//		int count = usbConnect.bulkTransfer(epBulkOut, cmdBytes, cmdBytes.length, 0);
//		if (count > 0) {
////			String a = MyFunc.bytesToHexString(cmdBytes);
//		}
		if(usbPort == null){
			LogUtil.e(TAG, "NULL USBPORT");
			return;
		}
		usbPort.write(cmdBytes);
	}

	@Override
	public void sendHex(String hex) {
		if(hex == null || hex == ""){
			LogUtil.e(TAG, "PARAMETER_ERROR!");
			return;
		}
		byte[] b = MyFunc.HexToByteArr(hex);
		sendBytes(b);
	}
	
	private int findSerialPortDevice() {
        // This snippet will try to open the first encountered usb device connected, excluding usb root hubs
		if(myUsbManager==null){
			LogUtil.e(TAG, "USB MANAGER IS NULL");
			return FAIL_NULL_OBJECT;
		}
		LogUtil.i(TAG, "USB MANAGER NOT NULL");
        HashMap<String, UsbDevice> usbDevices = myUsbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                usbDevice = entry.getValue();
                int deviceVID = usbDevice.getVendorId();
                int devicePID = usbDevice.getProductId();
                Log.i(TAG, "vid: "+deviceVID + "pid: "+devicePID);
                if (deviceVID != 0x1d6b && (devicePID != 0x0001 && devicePID != 0x0002 && devicePID != 0x0003)) {
                    // There is a device connected to our Android device. Try to open it as a Serial Port.
                    requestUserPermission();
                    keep = false;
                    if(!granted){
                    	return FAIL_PERMISSION_ASK;
                    }
                    return SUCCESS;
                } else {
                    usbConnect = null;
                    usbDevice = null;
                }

                if (!keep)
                    break;
            }
            if (!keep) {
                // There is no USB devices connected (but usb host were listed). Send an intent to MainActivity.
                Intent intent = new Intent(ACTION_NO_USB);
                context.sendBroadcast(intent);
            }
        } else {
            // There is no USB devices connected. Send an intent to MainActivity
            Intent intent = new Intent(ACTION_NO_USB);
            context.sendBroadcast(intent);
        }
        return FAIL_UNKOWN;
    }
	
	private void requestUserPermission() {
		PendingIntent mPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
		myUsbManager.requestPermission(usbDevice, mPendingIntent);
	}
	
	@Override
	public boolean isOpen() {
		return serialPortConnected;
	}

	private void setFilter(Context context) {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_USB_PERMISSION);
		filter.addAction(ACTION_USB_DETACHED);
		filter.addAction(ACTION_USB_ATTACHED);
		context.registerReceiver(usbReceiver, filter);
	} 

	private UsbSerialInterface.UsbReadCallback usbReadCallback = new UsbSerialInterface.UsbReadCallback() {
		@Override
		public void onReceivedData(byte[] buffer) {
//			String data = MyFunc.bytesToHexString(receiveData);
//			LogUtil.i(TAG, data);
			bufferQueue.add(buffer);
		}
	};
	
	 /*
     * State changes in the CTS line will be received here
     */
    private UsbSerialInterface.UsbCTSCallback ctsCallback = new UsbSerialInterface.UsbCTSCallback() {
        @Override
        public void onCTSChanged(boolean state) {
        	LogUtil.i(TAG, "onCTSChanged: " + state);
        }
    };

    /*
     * State changes in the DSR line will be received here
     */
    private UsbSerialInterface.UsbDSRCallback dsrCallback = new UsbSerialInterface.UsbDSRCallback() {
        @Override
        public void onDSRChanged(boolean state) {
        	LogUtil.i(TAG, "onDSRChanged: " + state);
        }
    };

	private class ConnectionThread extends Thread {
	        @Override
	        public void run() {
	            usbPort = UsbSerialDevice.createUsbSerialDevice(usbDevice, usbConnect);
	            if (usbPort != null) {
	                if (usbPort.open()) {
	                    serialPortConnected = true;
	                    usbPort.setBaudRate(BAUD_RATE);
	                    usbPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
	                    usbPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
	                    usbPort.setParity(UsbSerialInterface.PARITY_NONE);
	                    /**
	                     * Current flow control Options:
	                     * UsbSerialInterface.FLOW_CONTROL_OFF
	                     * UsbSerialInterface.FLOW_CONTROL_RTS_CTS only for CP2102 and FT232
	                     * UsbSerialInterface.FLOW_CONTROL_DSR_DTR only for CP2102 and FT232
	                     */
	                    usbPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
	                    usbPort.read(usbReadCallback);
	                    usbPort.getCTS(ctsCallback);
	                    usbPort.getDSR(dsrCallback);
	                    
	                    //
	                    // Some Arduinos would need some sleep because firmware wait some time to know whether a new sketch is going 
	                    // to be uploaded or not
	                    //Thread.sleep(2000); // sleep some. YMMV with different chips.
	                    
	                    Intent intent = new Intent(ACTION_USB_READY);
	                    context.sendBroadcast(intent);
	                } else {
	                    // Serial port could not be opened, maybe an I/O error or if CDC driver was chosen, it does not really fit
	                    // Send an Intent to Main Activity
	                    if (usbPort instanceof CDCSerialDevice) {
	                        Intent intent = new Intent(ACTION_CDC_DRIVER_NOT_WORKING);
	                        context.sendBroadcast(intent);
	                    } else {
	                        Intent intent = new Intent(ACTION_USB_DEVICE_NOT_WORKING);
	                        context.sendBroadcast(intent);
	                    }
	                }
	            } else {
	                // No driver for given device, even generic CDC driver could not be loaded
	                Intent intent = new Intent(ACTION_USB_NOT_SUPPORTED);
	                context.sendBroadcast(intent);
	            }
	        }
	    }
	
	private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent i) {
            if (i.getAction().equals(ACTION_USB_PERMISSION)) {
                granted = i.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                if (granted) // User accepted our USB connection. Try to open the device as a serial port
                {
                	CommonFunc.showMessage(c,"granted",Toast.LENGTH_SHORT);
                	LogUtil.i(TAG, "PERMISSION ACCEPT!");
                    Intent intent = new Intent(ACTION_USB_PERMISSION_GRANTED);
                    c.sendBroadcast(intent);
                    usbConnect = myUsbManager.openDevice(usbDevice);
                   	connThread = new ConnectionThread();
                   	connThread.start();
                } else // User not accepted our USB connection. Send an Intent to the Main Activity
                {
                    Intent intent = new Intent(ACTION_USB_PERMISSION_NOT_GRANTED);
                    c.sendBroadcast(intent);
                }
            } else if (i.getAction().equals(ACTION_USB_ATTACHED)) {
                if (!serialPortConnected)
                    findSerialPortDevice(); // A USB device has been attached. Try to open it as a Serial port
            } else if (i.getAction().equals(ACTION_USB_DETACHED)) {
                // Usb device was disconnected. send an intent to the Main Activity
                Intent intent = new Intent(ACTION_USB_DISCONNECTED);
                c.sendBroadcast(intent);
                if (serialPortConnected) {
                    usbPort.close();
                }
                serialPortConnected = false;
            }
        }
    }; 
}
