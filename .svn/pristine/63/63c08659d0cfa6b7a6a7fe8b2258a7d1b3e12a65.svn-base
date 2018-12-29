package com.mhdt.toolkit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author 懒得出风头<p>
 * Date: 2016-5-18<br>
 * Time： 11:46
 */
public class ByteUtilly {

	public static byte[] intToByte(int integer) {
		int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer : integer)) / 8;
		byte[] byteArray = new byte[4];

		for (int n = 0; n < byteNum; n++)
			byteArray[3 - n] = (byte) (integer >>> (n * 8));

		return (byteArray);
	}
	
	public static int byteToInt(byte[] b, int offset) {
	       int value= 0;
	       for (int i = 0; i < 4; i++) {
	           int shift= (4 - 1 - i) * 8;
	           value +=(b[i + offset] & 0x000000FF) << shift;
	       }
	       return value;
	 }
	
	  public static byte[] shortToByte(short number) { 
	        int temp = number; 
	        byte[] result = new byte[2]; 
	        for (int i = 0; i < result.length; i++) { 
	            result[i] = new Integer(temp & 0xff).byteValue();
	            temp = temp >> 8; // 向右移8位 
	        } 
	        return result; 
	    } 
	  
	  public static short byteToShort(byte[] bytes) { 
	        short s = 0; 
	        short s0 = (short) (bytes[0] & 0xff);
	        short s1 = (short) (bytes[1] & 0xff); 
	        s1 <<= 8; 
	        s = (short) (s0 | s1); 
	        return s; 
	    }
	
	
	public static final InputStream byteToInputStream(byte[] bytes){
		return new ByteArrayInputStream(bytes); 
	}
	
	public static final byte[] inputStreamToByte(InputStream inStream)  throws IOException {  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        byte[] buff = new byte[1024];  
        int in = 0;  
        while ((in = inStream.read(buff, 0, buff.length)) > 0) {  
            bos.write(buff, 0, in);  
        }  
        byte[] result = bos.toByteArray();  
        return result;  
    }  
	

}
