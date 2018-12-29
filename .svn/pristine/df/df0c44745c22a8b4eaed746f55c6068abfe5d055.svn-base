package com.mhdt.toolkit;

import java.util.Collection;
import java.util.Map;

import com.mhdt.analyse.Validate;

/**
 * 
 * Assertions can be used to create more stable, higher-quality code that is
 * less error-prone. You can use assertions when you want to interrupt the
 * current operation with a value of FALSE.
 * 
 * @author LazyToShow <br>
 *         Date: 2018年10月14日 <br>
 *         Time: 下午1:52:17
 */
public class Assert {

	/**
	 * Verify the Boolean expression, otherwise throw an IllegalStateException
	 * 
	 * @param expression
	 * @param message
	 */
	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(message);
		}
	}

	/**
	 * 
	 * @param object
	 * @param message
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 
	 * @param object
	 * @param message
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 
	 * @param object
	 * @param message
	 */
	public static <T> void notNullOrEmpty(Object object, String message) {
		if (Validate.isNullOrEmpty(object)) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 
	 * @param map
	 * @param message
	 */
	public static <k,V> void notNullOrEmpty(Map<k,V> map, String message) {
		if (Validate.isNullOrEmpty(map) || map.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 
	 * @param object
	 * @param message
	 */
	public static <T> void notNullOrEmpty(Collection<T> object, String message) {
		if (object == null || object.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 
	 * @param text
	 * @param message
	 */
	public static void hasLength(String text, String message) {
		if (!StringUtility.hasLength(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 
	 * @param text
	 * @param message
	 */
	public static void hasText(String text, String message) {
		if (!StringUtility.hasText(text)) {
			throw new IllegalArgumentException(message);
		}
	}

}
