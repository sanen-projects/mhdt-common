package com.mhdt.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	
	Socket socket;
	
	public void start(String ip, int port,byte[] data,SocketProcess process) throws UnknownHostException, IOException {
		this.process= process;
		socket = new Socket(ip, port);
		
		System.out.println("Connected to server "+ip+"："+port);

		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();
		
		out.write(data);


		ByteArrayOutputStream recieve= new ByteArrayOutputStream();
		byte[] temp = new byte[1024];
		int len = 0;
		
		while ((len = in.read(temp))!=-1){
			recieve.write(temp, 0, len);
			if(len<1024) {
				handel(recieve.toByteArray());
			}
		}
		
		
		socket.close();
	}
	
	SocketProcess process;
	
	private void handel(byte[] byteArray) {
		process.process(byteArray);
	}


}
