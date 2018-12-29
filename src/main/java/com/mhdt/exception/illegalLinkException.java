package com.mhdt.exception;


/**
 * You can use validate register link/url is or not legal.
 * @author 懒得出风头<p>
 * Date: 2016-5-19<br>
 * Time: 22:04<br>
 * Email： 282854237@qq.com
 */
public class  illegalLinkException extends Exception{

	private static final long serialVersionUID = 1L;

	public illegalLinkException(String string) {
		super(string);
	}
	
}