package com.mhdt.toolkit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mhdt.analyse.Validate;
import com.mhdt.annotation.NoDB;

/**
 * Some about bean operate . eg:map to bean / bean to map;
 * 
 * @author 懒得出风头
 *         <p>
 *         Date: 2016-4-24<br>
 *         Time: 12:02<br>
 *         Email: 282854237@qq.com<br>
 *         lastUpdated: 2016-4-27 丶
 */
public class Bean {

	/**
	 * 
	 * @param obj
	 * @param map
	 */
	public static void populate(Object obj, Map<String, Object> map) {

		for (Map.Entry<String, Object> entry : map.entrySet())
			Reflect.setInject(obj, entry.getKey(), entry.getValue());
	}

	public static class InstantException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public InstantException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}
	}

	/**
	 * 
	 * @param beanClass bean.class
	 * @param map       key-value
	 * @return bean
	 */
	public static <T> T parse(Class<T> beanClass, Map<String, Object> map) {

		T t = null;
		try {
			t = (T) beanClass.newInstance();
		} catch (Exception e) {
			throw new InstantException("Entity classes have no no-argument constructors : " + beanClass, e);
		}

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Reflect.setInject(t, entry.getKey(), entry.getValue());
		}
		return t;

	}

	public static <T> void populate(Class<T> beanClass, Collection<Map<String, Object>> collection,
			Collection<T> receiveCollection) {

		for (Map<String, Object> map : collection) {
			T t = parse(beanClass, map);
			if (t != null)
				receiveCollection.add(t);
		}

	}

	public static <T> List<T> parse(Class<T> beanClass, Collection<Map<String, Object>> collection) {
		List<T> receiveCollection = new ArrayList<T>();
		for (Map<String, Object> map : collection) {
			T t = parse(beanClass, map);
			if (t != null)
				receiveCollection.add(t);
		}
		return receiveCollection;

	}

	/**
	 * Object to Bean.Object to Bean.Object to Bean.
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> toMap(Object bean) {
		Map<String, Object> map = new HashMap<String, Object>();

		Field[] fileds = Reflect.getFields(bean);
		for (Field field : fileds) {
			try {
				field.setAccessible(true);
				map.put(field.getName(), field.get(bean));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return map;
	}

	public static Map<String, Class<?>> structured(Object obj) {

		Map<String, Class<?>> map = new HashMap<>();

		Field[] fileds = Reflect.getFields(obj);

		for (Field field : fileds) {

			if (!Validate.isStatic(field) && !field.isAnnotationPresent(NoDB.class)) {
				field.setAccessible(true);
				map.put(field.getName(), field.getType().isEnum() ? String.class : field.getType());
			}
		}
		
		
		

		return map;
	}

}
