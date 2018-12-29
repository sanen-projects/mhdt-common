package com.mhdt.log;

import java.io.File;

/**
 * 
 * @author LazyToShow
 * Date：	2018年8月29日
 * Time:	上午11:38:25
 */
public interface LogOut{

	File getLogFile();

	default File getErrorFile() {
		return null;
	}
	
	long getLastTime();
	
	void setLastTime(long timelong);

}
