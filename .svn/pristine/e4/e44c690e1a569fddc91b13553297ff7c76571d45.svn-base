package com.mhdt.log;

import java.io.File;

import com.mhdt.toolkit.DateUtility;
import com.mhdt.toolkit.FileUtility;

/**
 * <pre>
 * 
 * Simple implementation of log.
 * 
 * &#64;author lAZY_TO_SHOW
 * Date: 2018/01/15
 * Time: 11:03
 * </pre>
 */
public class DefaultLog extends AbstractLog {

	static DefaultLog getInstance() {
		return LazySimpleLog.log;
	}

	static class LazySimpleLog {
		static DefaultLog log = new DefaultLog();
	}


	@Override
	public File getLogFile() {

		File file = new File(Log.DEFAULTOUTPATH + DateUtility.today()  + "-root-.txt");
		if (!file.exists())
			FileUtility.createFile(file);
		return file;

	}

	@Override
	public File getErrorFile() {

		File file = new File(Log.DEFAULTOUTPATH + DateUtility.today()  + ".txt");
		if (!file.exists())
			FileUtility.createFile(file);
		return file;

	}

}
