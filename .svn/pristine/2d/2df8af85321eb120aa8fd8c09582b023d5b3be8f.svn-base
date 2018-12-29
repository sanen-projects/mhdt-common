package com.mhdt.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.mhdt.toolkit.ByteUtilly;

/**
 * bytes out put stream
 * @author 懒得出风头 <br>
 * Date: 2016/07/28<br>
 * Time: 15:26
 */
public class RandomAcessOutputStream extends ByteArrayOutputStream{
	
	public synchronized void write(short number){
		try {
			write(ByteUtilly.shortToByte(number));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void write(int number){
		try {
			write(ByteUtilly.intToByte(number));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void writeString(String sentence){
		try {
			write(sentence.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
