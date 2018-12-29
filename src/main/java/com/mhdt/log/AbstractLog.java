package com.mhdt.log;

import com.mhdt.Print;
import com.mhdt.io.FileIO;
import com.mhdt.toolkit.DateUtility;
import com.mhdt.toolkit.StringUtility;

/**
 * 
 * @author LazyToShow <br>
 *         Date： 2018年8月29日 <br>
 *         Time: 上午10:51:28
 */
abstract class AbstractLog implements Log, LogOut {

	static {
		System.setErr(ExceptionPrintStream.getInstance());
	}

	public AbstractLog() {
		ExceptionPrintStream.addLogOut(this);
	}


	@Override
	public boolean isDebugEnable() {
		return getLevel().intValue >= Level.DEBUG.intValue() && LogManager.isDebugEnable();
	}


	@Override
	public Level getLevel() {
		return LogManager.level;
	}

	/**
	 * 
	 * @param level
	 * @param message
	 * @return
	 */
	protected String generatingOutput(Level level, String methodName, Object message, Throwable throwable) {

		String[] identifier = Log.identifier.get(level);

		return identifier[0] + level.sign + identifier[1] + "	" + DateUtility.getNow("HH:mm:ss") + " "
				+ Print.getCodeInfo(4) + "	- " + (message == null ? "\tnull" : message.toString()) + " "
				+ StringUtility.getStackMessage(throwable);
	}

	/**
	 * Output content at log level
	 * 
	 * @param level
	 * @param outMessage
	 */
	protected void outputLog(Level level, String outMessage) {
		FileIO.write(getLogFile(), "\r\n" + outMessage, true);
	}

	@Override
	public void info(Object obj) {

		if (getLevel().intValue >= Level.INFO.intValue) {

			String outMessage = generatingOutput(Level.INFO, getMethod(), String.valueOf(obj), null);
			System.err.println(outMessage);
		}

	}

	protected String getMethod() {
		Throwable dummyException = new Throwable();
		StackTraceElement locations[] = dummyException.getStackTrace();
		
		String method = "unknown";
		if (locations != null && locations.length > 3) {
			StackTraceElement caller = locations[3];
			method = caller.getMethodName();
		}

		return method;
	}

	@Override
	public void warn(Object obj) {
		if (getLevel().intValue >= Level.WARN.intValue) {
			String outMessage = generatingOutput(Level.WARN,getMethod(), String.valueOf(obj), null);
			System.err.println(outMessage);
		}
	}

	@Override
	public void debug(Object obj) {

		if (getLevel().intValue >= Level.DEBUG.intValue) {
			String outMessage = generatingOutput(Level.DEBUG,getMethod(), String.valueOf(obj), null);
			System.err.println(outMessage);
		}
	}

	@Override
	public void debug(Object obj, Throwable ex) {

		if (getLevel().intValue >= Level.DEBUG.intValue) {
			String outMessage = generatingOutput(Level.DEBUG,getMethod(), String.valueOf(obj), ex);
			System.err.println(outMessage);
		}

	}

	@Override
	public void error(Throwable e) {

		if (getLevel().intValue >= Level.OFF.intValue) {
			String outMessage = generatingOutput(Level.DEBUG,getMethod(), String.valueOf(""), e);
			System.err.println(outMessage);
		}

	}

	@Override
	public void error(Object obj) {

		if (getLevel().intValue >= Level.OFF.intValue) {
			String outMessage = generatingOutput(Level.DEBUG, getMethod(),String.valueOf(obj), null);
			System.err.println(outMessage);
		}

	}

	long lastTime;

	@Override
	public long getLastTime() {
		return lastTime;
	}

	@Override
	public void setLastTime(long timelong) {
		this.lastTime = timelong;
	}

}
