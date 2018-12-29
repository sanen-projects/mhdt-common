package com.mhdt.test;

import com.mhdt.log.Log;
import com.mhdt.log.LogFactory;

public class LogTest {
	
	public static void main(String[] args) {
		
			Log log = LogFactory.getLog("test");
			log.info("log-123");
			log.warn("log-123");
			log.debug("log-123");
			log.error("log-123");
		
			Log log2 = LogFactory.getLog("test2");
			log2.info("log2-123");
			log2.warn("log2-123");
			log2.debug("log2-123");
			log2.error("log2-123");
			
			Log log3 = Log.getDefaultLog();
			log3.info("log3-123");
			log3.warn("log3-123");
			log3.debug("log3-123");
			log3.error("log3-123");
	}

}
