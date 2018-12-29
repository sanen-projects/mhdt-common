package com.mhdt.log;

import java.io.File;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mhdt.io.FileIO;

/**
 * 
 * <pre>
 * The trigger time is automatically attached when the system throws an exception.
 * @author LazyToShow <br>
 * Date: 2017/10/15 <br>
 * Time: pm 4:35:28
 * </pre>
 */
public class ExceptionPrintStream extends PrintStream {

	private ExceptionPrintStream() {
		super(System.err);
	}

	static List<LogOut> listLogOut = new CopyOnWriteArrayList<>();

	private static class LazyLogPrintStream {
		static ExceptionPrintStream logPrintStream = new ExceptionPrintStream();
	}

	static ExceptionPrintStream getInstance() {
		return LazyLogPrintStream.logPrintStream;
	}

	public static void addLogOut(LogOut logOut) {
		if (!listLogOut.contains(logOut))
			listLogOut.add(logOut);
	}

	@Override
	public void print(String s) {
		Set<String> files = new HashSet<>();
		
		for (LogOut item : listLogOut) {

			File file = item.getErrorFile();
			
			if(file == null || files.contains(file.getAbsolutePath()))
				continue;
			else
				files.add(file.getAbsolutePath());

			long now = System.currentTimeMillis();
			
			if (now - item.getLastTime() > 1100) {
				
				if (file.length() != 0)
					FileIO.write(file,"\r\n-----------------------------------------------------------------------------------\r\n\r\n",true);

				String content =   s+"\r\n";
				FileIO.write(file, content, true);
				item.setLastTime(now);

			} else {
				FileIO.write(file, s + "\r\n", true);
			}

		}

		super.print(s);
	}

}
