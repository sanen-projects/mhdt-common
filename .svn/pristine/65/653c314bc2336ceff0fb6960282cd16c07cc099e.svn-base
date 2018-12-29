package com.mhdt.log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author LazyToShow
 * Date: 2018/06/12
 * Time: 09:17
 */
public class LogFactory {

	static Map<String, Log> cache = new HashMap<>();

	public static Log getLog(String namespace) {

		if (cache.containsKey(namespace))
			return cache.get(namespace);

		return new IndependentLog(namespace);
	}

	public static Log getLog(String namespace, File outputFolder) {
		if (cache.containsKey(namespace))
			return cache.get(namespace);

		return new IndependentLog(namespace, outputFolder);
	}

	public static Log getLog(Class<?> cls) {
		return getLog(cls.getName());
	}

	public static Log getLog(Class<?> cls, File outputFolder) {
		return getLog(cls.getName(), outputFolder);
	}

}
