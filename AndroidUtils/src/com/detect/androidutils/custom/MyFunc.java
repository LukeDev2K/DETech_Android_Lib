package com.detect.androidutils.custom;

public class MyFunc {
	//------------------------------------------------------- // �ж�������ż����λ���㣬���һλ��?1��Ϊ������Ϊ0��ż��
    static public int isOdd(int num)
	{
		return num & 0x1;
	}
    //-------------------------------------------------------
    static public int HexToInt(String inHex) 
    {
    	return Integer.parseInt(inHex, 16);
    }
    //-------------------------------------------------------
    static public byte HexToByte(String inHex) 
    {
    	return (byte)Integer.parseInt(inHex,16);
    }
    //-------------------------------------------------------
    static public String Byte2Hex(Byte inByte) 
    {
    	return String.format("%02x", inByte).toUpperCase();
    }
    //-------------------------------------------------------
	static public String ByteArrToHex(byte[] inBytArr) 
	{
		StringBuilder strBuilder=new StringBuilder();
		int j=inBytArr.length;
		for (int i = 0; i < j; i++)
		{
			strBuilder.append(Byte2Hex(inBytArr[i]));
			strBuilder.append(" ");
		}
		return strBuilder.toString(); 
	}
  //-------------------------------------------------------
    static public String ByteArrToHex(byte[] inBytArr,int offset,int byteCount) 
	{
    	StringBuilder strBuilder=new StringBuilder();
		int j=byteCount;
		for (int i = offset; i < j; i++)
		{
			strBuilder.append(Byte2Hex(inBytArr[i]));
			strBuilder.append(" ");
		}
		return strBuilder.toString();
	}
	//------------------------------------------------------- 
    static public byte[] HexToByteArr(String inHex)// 
	{
		int hexlen = inHex.length();
		byte[] result;
		if (isOdd(hexlen)==1)
		{ 
			hexlen++;
			result = new byte[(hexlen/2)];
			inHex="0"+inHex;
		}else { 
			result = new byte[(hexlen/2)];
		}
	    int j=0;
		for (int i = 0; i < hexlen; i+=2)
		{
			result[j]=HexToByte(inHex.substring(i,i+2));
			j++;
		}
	    return result; 
	}
    
    public static byte[] byteAdjust(byte[] byteArr, int tag){
        int length = 0;
        for (int i = 1; i < byteArr.length; i++) {
			if(byteArr[i] == 0){
				length = i;
				break;
			}
		}
        byte[] bytes = new byte[length];
        System.arraycopy(byteArr, 0, bytes, 0, length); 
        return bytes;
	}
    
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){  
		byte[] b = byteAdjust(byte_1, 0);
        byte[] byte_3 = new byte[40];  
        System.arraycopy(b, 0, byte_3, 0, b.length);  
        System.arraycopy(byte_2, 0, byte_3, b.length, byte_2.length);  
        return byte_3;  
    }
    
    public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
			sb.append(" ");
		}
		return sb.toString();
	}
    
    public static float getMills(String timeFormat){
    	String[] arr = timeFormat.split("-");
    	float timer = Float.parseFloat(arr[arr.length-1]);
    	return timer;
    }
}