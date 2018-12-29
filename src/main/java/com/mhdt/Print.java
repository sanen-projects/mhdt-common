package com.mhdt;

import com.mhdt.log.Log;
import com.mhdt.log.Log.Level;
import com.mhdt.toolkit.DateUtility;
import static org.fusesource.jansi.Ansi.*;

/**
 * Out test sentense.
 * 
 * @author LazyToShow <br>
 *         Date: 2016/06/08 <br> 
 *         Time: 15:55 <br>
 *         Email: 282854237@qq.com
 */
public class Print {
	
	/**
	 * 
	 * @param obj  - {@code Object.toString()}
	 * @param stackIndex - {@code new Throwable().getStackTrace()[stackIndex]}
	 */
	public static void info(Object obj, int stackIndex) {
		System.out.println(getCodeInfo(stackIndex) + (obj==null?"null":obj.toString()));
	}
	
	
	public static String info(Object obj) {
		String[] identifier = Log.identifier.get(Level.INFO);
		String message = toString(identifier[0]+" INFO "+identifier[1], "CYAN", (obj == null ? "null" : obj.toString()));
		System.out.println(message);
		return message;
	}
	
	public static String warn(Object obj) {
		String[] identifier = Log.identifier.get(Level.WARN);
		String message = toString(identifier[0]+" WARN "+identifier[1], "WHITE", (obj == null ? "null" : obj.toString()));
		System.out.println(message);
		return message;
	}

	public static String debug(Object obj) {
		String[] identifier = Log.identifier.get(Level.DEBUG);
		String message = toString(identifier[0]+" DEBUG "+identifier[1], "BLUE", (obj == null ? "null" : obj.toString()));
		System.out.println(message);
		return message;
	}

	public static String error(Object obj) {
		String[] identifier = Log.identifier.get(Level.ERROR);
		String message = toString(identifier[0]+" ERROR "+identifier[1], "RED", (obj == null ? "null" : obj.toString()));
		System.out.println(message);
		return message;
	}

	public static String out(Object obj, String color) {
		String message = translate(color, (obj == null ? "null" : obj.toString()));
		System.out.println(message);
		return message;
	}

	static int lastTime;

	@SuppressWarnings("unused")
	private static String getTime() {
		if (DateUtility.getNowForInt() - lastTime > 5) {
			lastTime = DateUtility.getNowForInt();
			return DateUtility.getNow();
		} else
			return DateUtility.getNow("HH:mm:ss");
	}

	public static String getCodeInfo(Integer stackIndex) {
		if (stackIndex == null || stackIndex < 0)
			stackIndex = 3;
		StackTraceElement ste = new Throwable().getStackTrace()[stackIndex];
		/**
		 * IDEA return
		 * ste.getClassName()+"."+ste.getMethodName()+"("+ste.getFileName()+":"+ste.getLineNumber()+")";
		 */
		return "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
	}

	public static void end() {
		warn("Application end .");
	}

	public static void start() {
		warn("Application start .");
	}

	private static String toString(Object sign, String color, Object obj) {
		
		return translate(color, sign + " at ") 
				+ getCodeInfo(3) 
				+ translate("MAGENTA", " - ")
				+ translate(color, obj.toString());
	}
	
	/**
	 * Converts a string to an <strong>ANSI</strong> encoding a new string of the
	 * specified color. <br>
	 * If you are using eclipse, download <strong>ANSI Escape in Console </strong>
	 * at the store.
	 * 
	 * @param color - {@link Color} 	BLACK/BLUE/CYAN/GREEN/MAGENTA/RED/WHITE/YELLOW
	 * @param obj   - By {@link Object#toString()} value.
	 * @return
	 */
	public static String translate(String color, String obj) {
		try {
			Class.forName("org.fusesource.jansi.Ansi");
			return ansi().eraseScreen().render("@|" + color + " " + obj.toString() + "|@ ").toString();
		} catch (ClassNotFoundException e) {
			return obj.toString();
		}
	}

}
