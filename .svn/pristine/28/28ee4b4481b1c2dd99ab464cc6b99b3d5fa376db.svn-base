package com.mhdt.test;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.TimeZone;

import com.mhdt.toolkit.DateUtility;

public class BeiJingTime {
	
	public static void main(String[] args) {
		try{
			TimeZone.setDefault(TimeZone.getTimeZone("GMT+8")); // 时区设置  
		       URL url=new URL("http://www.bjtime.cn");//取得资源对象  
		       URLConnection uc=url.openConnection();//生成连接对象  
		       uc.connect(); //发出连接  
		       long ld=uc.getDate(); //取得网站日期时间（时间戳）  
		       //转换为标准时间对象  
		       Date date=new Date(ld); 
		       System.out.println(DateUtility.intToString((int)(date.getTime()/1000l)));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
