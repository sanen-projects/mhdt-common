package com.mhdt.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.mhdt.analyse.Validate;
import com.mhdt.exception.illegalLinkException;

/**
 * Access to the network time and the default timeZone is GMT+8 from <br>
 * Default linkUrl is http://www.baidu.com you can also change this use changeURL
 * @author 懒得出风头<br>
 *         Date: 2016-5-19<br>
 *         Time： 16:41<br>
 *         Email: 282854237@qq.com
 */
public class TimeZone {
	private static URL url;
	private static URLConnection uc;

	static {
		try {
			java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("GMT+8"));
			url = new URL("http://www.baidu.com");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private TimeZone() {
		
	}

	/**
	 * If the user is not connected to the Internet Retutn -1 else return
	 * currentTime.
	 * 
	 * @return timelong By int.
	 */
	public final static int getNow() {
		try {
			uc = url.openConnection();
			uc.connect();
			int result = (int) (uc.getDate() / 1000l);
			return result > 0 ? result : -1;
		} catch (Exception e) {
			System.out.println("[ERROR] 获取时间异常: 返回 -1");
			return -1;
		}
	}
	
	/**
	 * change url to get time online.
	 * @param link
	 * @throws illegalLinkException
	 */
	public final static void changeURL(String link) throws illegalLinkException{
		link=link.startsWith("http://")?link:"http://"+link;
		if(!Validate.isUrl(link))throw new illegalLinkException("链接不合法:"+link);
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	

}
