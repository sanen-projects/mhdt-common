package com.mhdt.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import com.mhdt.Print;
import com.mhdt.toolkit.DateUtility;

/**
 * 
 *
 * @author LazyToShow <br>
 * Date: Nov 12, 2018 <br>
 * Time: 5:50:13 PM
 */
public class SocketServer {  
	
	ServerSocket serverSocket;
	
    static int servPort= 6666;  
      
    static int recvMsgSize;  
    
    private static final int BUFSIZE=32;  
    
    static byte [] receiveBuf=new byte[BUFSIZE];
    
    SocketProcess process;
    
    public void start(Integer port,SocketProcess process) throws IOException {
    	
    	this.process = process;
    	
    	serverSocket  =new ServerSocket(port==null?servPort:port);  
        Print.info("\r\nStart Server At "+DateUtility.getNow("YYYY/MM/dd HH:mm:ss")+", Port Bind["+(port==null?servPort:port)+"]"); 
       
        while(true){
            final Socket socket=serverSocket.accept();  
            SocketAddress clientAddress=socket.getRemoteSocketAddress();  
            System.out.println("Handling client at "+clientAddress);  
           
            new Thread(new Runnable() {
				
				@Override
				public void run() {
					 InputStream in;
					try {
						in = socket.getInputStream();
						OutputStream out=socket.getOutputStream();  
						  
			            while((recvMsgSize=in.read(receiveBuf))!=-1){
			            	handel(receiveBuf, out);
			            }  
			          
			            socket.close();  
					} catch (IOException e) {
						e.printStackTrace();
					}  
			          
				}
			}).start();
           
        }  
    }
    
    private void handel(byte[] receive,OutputStream outputStream) throws IOException {
    	
    	byte[] bytes = process.process(receive);
    	if(bytes!=null) {
    		outputStream.write(bytes);
    	}
    }
    
}  

