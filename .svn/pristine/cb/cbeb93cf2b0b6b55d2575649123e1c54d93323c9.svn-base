package com.mhdt.toolkit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mhdt.analyse.Validate;
import com.mhdt.annotation.Alias;
import com.mhdt.annotation.BootStrapID;
import com.mhdt.annotation.NoDB;
import com.mhdt.annotation.Priority;
import com.mhdt.annotation.Table;

/**
 * Reflection object field method and validation
 * 
 * @author LazyToShow<br>
 *         Date: 2016/3/4<br>
 *         Time: 17:34 <br>
 *         Email: 282854237@qq.com<br>
 *         lastUpdate： 2016/06/13 15:36
 */
public class Reflect {

	private Reflect() {

	}

	public static Field getField(Object obj, String fieldName) {
		if (obj == null)
			return null;
		return getField(obj.getClass(), fieldName);
	}

	@SuppressWarnings("rawtypes")
	public static Field getField(Class<?> cls, String fieldName) {
		if (cls == null)
			return null;

		Field field = null;
		try {
			// Step 1
			field = cls.getDeclaredField(fieldName);

		} catch (NoSuchFieldException e) {

			// Step 2
			for (Field f : cls.getDeclaredFields()) {
				if (f.getName().toLowerCase().equals(fieldName.toLowerCase())) {
					field = f;
					break;
				}
			}

			// Step 3
			if (field == null) {
				Class c = cls.getSuperclass();

				if (c != null && c.getName().indexOf("java.lang.Object") != -1)
					return null;
				else
					field = getField(c, fieldName);
			}

		}

		if (field != null)
			field.setAccessible(true);
		return field;
	}

	/**
	 * Get field by {@link Alias}.value();
	 * 
	 * @return
	 */
	public static Field getFieldByAlias(Object obj, String column) {
		Field[] fields = getFields(obj);

		for (Field f : fields) {
			if (f.isAnnotationPresent(Alias.class) && f.getAnnotation(Alias.class).value().equals(column)) {
				f.setAccessible(true);
				return f;
			}
		}

		return null;
	}

	public static Field[] getFields(Object bean) {
		if (bean == null)
			throw new NullPointerException("Object is null");

		return getFields(bean.getClass());

	}

	public static Field[] getFields(Class<?> cls) {
		List<Field> result = new ArrayList<Field>();
		result.addAll(Arrays.asList(cls.getDeclaredFields()));

		while (!cls.getSuperclass().getSimpleName().equals("Object")) {
			cls = cls.getSuperclass();
			result.addAll(Arrays.asList(cls.getDeclaredFields()));
		}

		return result.toArray(new Field[] {});
	}

	/**
	 * Access to the object's field values.
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static Object getValue(Object obj, String fieldName)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return getField(obj, fieldName).get(obj);
	}

	/**
	 * 用方法名称查找
	 * 
	 * @param cls
	 * @param methodName
	 * @return 找不到返回null
	 */
	public static Method getMethodByName(String cls, String methodName) throws ClassNotFoundException {
		return getMethodByName(Class.forName(cls), methodName);
	}

	/**
	 * 用方法名称查找
	 * 
	 * @param cls
	 * @param methodName
	 * @return 找不到返回null
	 */
	public static Method getMethodByName(Class<?> cls, String methodName) {

		Method[] methods = cls.getDeclaredMethods();

		while (methods != null && methods.length > 0) {

			for (Method method : methods) {

				if (method.getName().equals(methodName)) {
					return method;
				}

			}

			cls = cls.getSuperclass();
			if (cls != null && cls.getName().indexOf("java.lang.Object") != -1)
				break;
			methods = cls.getDeclaredMethods();
		}

		return null;
	}

	/**
	 * 
	 * @param obj
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Method getMethod(Object obj, String methodName, Class<?>... parameterTypes)
			throws NoSuchMethodException, SecurityException {
		Class<?> c = obj.getClass();
		Method method = c.getMethod(methodName, parameterTypes);
		method.setAccessible(true);
		return method;
	}

	/**
	 * Call the object's method.
	 * 
	 * @param owner
	 * @param methodName
	 * @param args
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	public static Object invokeMethod(Object owner, String methodName, Object[] args) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class[] argClass = new Class[args.length];
		for (int i = 0; i < argClass.length; i++) {
			argClass[i] = args[i].getClass();
		}

		Method method = owner.getClass().getDeclaredMethod(methodName, argClass);
		method.setAccessible(true);
		return method.invoke(owner, args);
	}

	/**
	 * create Instance By className.
	 * 
	 * @param className
	 * @return Object
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static Object newInstance(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		return Reflect.class.getClassLoader().loadClass(className).newInstance();

	}

	/**
	 * create Instance By className.
	 * 
	 * @param className
	 * @return Object
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String className, Class<T> cls)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return (T) Reflect.class.getClassLoader().loadClass(className).newInstance();

	}

	/**
	 * create Instance of Object and has params.
	 * 
	 * @param className
	 * @param args
	 * @return Object
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object newInstance(String className, Object... args) throws Exception {

		Class newoneClass = Reflect.class.getClassLoader().loadClass(className);

		Class[] argsClass = new Class[args.length];

		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Constructor cons = newoneClass.getDeclaredConstructor(argsClass);
		cons.setAccessible(true);
		return cons.newInstance(args);
	}

	/**
	 * To obtain field generics, return null said there was no.
	 * 
	 * @param field
	 * @return Type[]
	 */
	public static Type[] getGeneric(Field field) {
		ParameterizedType type = (ParameterizedType) field.getGenericType();
		int size = type.getActualTypeArguments().length;
		if (size < 1)
			return null;

		return type.getActualTypeArguments();
	}

	/**
	 * According to the generic build array.
	 * 
	 * @param cls
	 * @param len
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newArray(Class<T> cls, int len) {
		return (T[]) Array.newInstance(cls, len);
	}

	/**
	 * 给字段设值
	 * 
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setField(Object obj, String fieldName, Object value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = getField(obj, fieldName);
		if (field == null) {
			throw new ReflectionException("Can't find field:" + fieldName + "  from " + obj.getClass());
		}
		field.setAccessible(true);

		value = typeConversion(value, field.getType());

		field.set(obj, value);
	}

	/**
	 * 类型转换,只能是基本类型
	 * 
	 * @param value
	 * @param type
	 * @return
	 */
	public static Object typeConversion(Object value, Class<?> type) {
		String name = type.getName();
		if (name.equals("byte") || name.equals("Byte")) {
			return Byte.valueOf(value.toString());
		}

		if (name.equals("short") || name.equals("Short")) {
			return Short.valueOf(value.toString());
		}

		if (name.equals("float") || name.equals("Float")) {
			return Float.valueOf(value.toString());
		}

		if (name.equals("double") || name.equals("Double")) {
			return Double.valueOf(value.toString());
		}

		if (name.equals("int") || name.equals("Integer")) {
			return Integer.valueOf(value.toString());
		}

		if (name.equals("long") || name.equals("Long")) {
			return Long.valueOf(value.toString());
		}

		if (name.equals("boolean") || name.equals("Boolean")) {
			return Boolean.valueOf(value.toString());
		}

		if (name.equals("char") || name.equals("Character")) {
			return (char) value;
		}

		return value;
	}

	/**
	 * Iterate the class and its parent classes to find annotations
	 * 
	 * @param cls
	 * @param annotation
	 * @return
	 */
	public static Class<?> getClassOfHasAnnotation(Class<?> cls, Class<? extends Annotation> annotation) {

		boolean b = cls.isAnnotationPresent(annotation);

		if (b) {
			return cls;
		} else if (!b && cls.getSuperclass() != null) {
			return getClassOfHasAnnotation(cls.getSuperclass(), annotation);
		}

		return null;

	}

	public static boolean hasAnnotation(Class<?> cls, Class<? extends Annotation> annotation) {

		boolean b = cls.isAnnotationPresent(annotation);

		if (b == false && cls.getSuperclass() != null) {
			return hasAnnotation(cls.getSuperclass(), annotation);
		}

		return b;
	}

	/**
	 * 获取column字段的value值
	 * 
	 * @param f
	 * @return
	 */
	public static String getColumnValue(Field f) {
		return f.getAnnotation(Alias.class).value();
	}

	static Map<Class<?>, String> cache = new HashMap<>();

	/**
	 * 获取BootStrapId值
	 * 
	 * @param cls
	 * @return
	 */
	public static String getBootStrapId(Class<?> cls) {

		if (cache.containsKey(cls))
			return cache.get(cls);

		cls = getClassOfHasAnnotation(cls, BootStrapID.class);
		if (cls == null) {
			return null;
		} else {
			String result = cls.getAnnotation(BootStrapID.class).value();
			if (!Validate.isNullOrEmpty(result))
				cache.put(cls, result);

			return result;
		}
	}

	/**
	 * 获取类注解值(tableName)
	 * 
	 * @param cls
	 * @return
	 */
	public static String getTableNameValue(Class<?> cls) {
		cls = getClassOfHasAnnotation(cls, Table.class);
		return cls == null ? null : cls.getAnnotation(Table.class).value();
	}

	/**
	 * 获取类的泛型
	 * 
	 * @param cls
	 * @return
	 */
	public static Type getGeneric(Class<?> cls) {
		return ((ParameterizedType) cls.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Use set injection first else use field injection than throw Exception
	 * 
	 * @param target
	 * @param columnName
	 * @param value
	 */
	public static void setInject(Object target, String columnName, Object value) {

		Field f = Reflect.getField(target, columnName);

		f = (f == null) ? Reflect.getFieldByAlias(target, columnName) : f;

		try {
			if (f.isAnnotationPresent(NoDB.class))
				return;
		} catch (NullPointerException e) {

			if (target.getClass().isAnnotationPresent(Priority.class))
				return;

			throw new ReflectionException("Cant match field for '" + columnName + "' from " + target.getClass(), e);
		}

		try {

			Method method = Reflect.getSetMethod(target.getClass(), columnName);
			method.setAccessible(true);

			Class<?> paramerClass = method.getParameters()[0].getType();

			if (paramerClass.isEnum())
				value = newEnum(paramerClass, String.valueOf(value));

			method.invoke(target, value);
		} catch (Exception e) {

			try {
				f.setAccessible(true);
				f.set(target, value);
			} catch (Exception e1) {
				throw new ReflectionException(e1);
			}
		}

	}

	private static Map<Class<?>, Map<String, Method>> map = new HashMap<>();

	public static Method getSetMethod(Class<? extends Object> cls, String field) {

		String methodName = ("set" + field).toUpperCase();

		if (map.containsKey(cls) && map.get(cls).containsKey(methodName)) {
			return map.get(cls).get(methodName);
		}

		for (Method m : cls.getDeclaredMethods()) {
			if (m.getName().toUpperCase().equals(methodName) && m.getParameterCount() == 1) {
				if (map.containsKey(cls)) {
					map.get(cls).put(methodName, m);
				} else {
					Map<String, Method> temp = new HashMap<>();
					temp.put(methodName, m);
					map.put(cls, temp);
				}

				return m;
			}
		}
		return null;
	}

	/**
	 * Use set injection first else use field injection than throw Exception
	 * 
	 * @param target
	 * @param field
	 * @return
	 */
	public static Object getInject(Object target, String field) {

		Field f = Reflect.getField(target, field);
		f = (f == null) ? Reflect.getFieldByAlias(target, field) : f;

		try {

			Method method = target.getClass().getDeclaredMethod("get" + StringUtility.firstToUpperCase(field));
			return method.invoke(target);

		} catch (Exception e) {

			try {

				f.setAccessible(true);
				return f.get(target);

			} catch (Exception e1) {
				throw new ReflectionException(e1);
			}
		}
	}

	public static <T> T newInstance(Class<T> type) throws InstantiationException, IllegalAccessException {
		return type.newInstance();
	}

	@SuppressWarnings("unchecked")
	public static <T> T newEnum(Class<T> cls, String value)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = Reflect.getMethodByName(cls, "valueOf");
		return (T) method.invoke(cls, value);
	}

}
