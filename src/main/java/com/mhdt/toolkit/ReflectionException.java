package com.mhdt.toolkit;

/**
 * 
 *
 * @author LazyToShow	<br>
 * Date:	Nov 23, 2018	<br>
 * Time:	4:20:35 PM
 */
public  class ReflectionException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ReflectionException(String arg0,Throwable arg1) {
		super(arg0, arg1);
	}

	public ReflectionException(String arg0) {
		super(arg0);
	}

	public ReflectionException(Throwable arg0) {
		super(arg0);
	}
	
	
}
