package com.mhdt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Table alias，Additional optional functionality for this annotation is added later in DDD mode.
 *
 * @author LazyToShow	<br>
 * Date:	Nov 27, 2018	<br>
 * Time:	5:25:49 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
	
	public String value();

}
