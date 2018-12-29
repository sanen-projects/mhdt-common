package com.mhdt.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies a Bootstrap instance with scoped classes and methods
 *
 * @author LazyToShow <br>
 *         Date: Nov 27, 2018 <br>
 *         Time: 5:28:14 PM
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BootStrapID {

	String value() default "bootstrap";

}
