package com.mhdt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Field alias
 * 
 * @author LazyTosHow <br>
 * Date： 2017/01/06 <br>
 * Time: 13:47
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Alias {
	
	public String value();

}
