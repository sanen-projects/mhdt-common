package com.mhdt.log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.mhdt.toolkit.PathUtil;

/**
 * 
 * @author LazyToShow <br>
 *         Date： 2018年8月28日 <br>
 *         Time: 下午6:25:11
 */
public interface Log {

	/**
	 * Log level identifier
	 */
	static Map<Level, String[]> identifier = new HashMap<Level, String[]>() {

		private static final long serialVersionUID = 1L;

		{
			put(Level.INFO, new String[] { "[", "]" });
			put(Level.WARN, new String[] { "☆", "☆" });
			put(Level.DEBUG, new String[] { "[", "]" });
			put(Level.ERROR, new String[] { "「", "」" });
		}
	};

	/**
	 * Default log output folder path
	 */
	static final String DEFAULTOUTPATH = PathUtil.underCurrentProject() + File.separator + "log" + File.separator;


	/**
	 * Gets the current log level
	 * 
	 * @return
	 */
	Level getLevel();

	void info(Object obj);

	void error(Throwable e);

	void error(Object obj);

	void debug(Object obj);
	
	void debug(Object obj, Throwable ex);
	
	/**
	 * Is it currently at debug {@link Level}
	 * 
	 * @return
	 */
	boolean isDebugEnable();

	void warn(Object obj);

	public static Log getDefaultLog() {
		return DefaultLog.getInstance();
	}

	public enum Level {
		
		/**
		 * Close the log and all logs will no longer be output
		 */
		OFF("black","",0),

		/**
		 * Meaningful event information, such as program startup, closing event,
		 * receiving request event, etc.
		 */
		INFO("green","信息", 100),

		/**
		 * Warning information, such as a program called an invalid interface, the
		 * improper use of the interface, the running state is not expected but still
		 * can continue processing;
		 */
		WARN("yellow","警告", 200),

		/** Other error run-time errors; */
		ERROR("red","错误", 300),

		/**
		 * Debugging information that records the details of the business process and
		 * the current state of the variable.
		 */
		DEBUG("pink","DEBUG", 400);

		String color;
		Integer intValue;
		String sign;

		private Level(String color, String sign,Integer intValue) {
			this.color = color;
			this.intValue = intValue;
			this.sign = sign;
		}

		public String getColor() {
			return color;
		}

		public Integer intValue() {
			return intValue;
		}

		public void setNum(Integer num) {
			this.intValue = num;
		}
	}
	
}
