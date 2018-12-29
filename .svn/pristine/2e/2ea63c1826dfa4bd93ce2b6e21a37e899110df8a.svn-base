package com.mhdt.parse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mhdt.analyse.Validate;
import com.mhdt.io.FileIO;
import com.mhdt.toolkit.Assert;
import com.mhdt.toolkit.StringUtility;

/**
 * Analysis the config file by one-To-one, The current version can only support
 * equal division . e.g A = B , add and remove 'method was thread safety. If
 * config file had chinese suggest to load the file using the
 * {@code load(InputStream is,String encode)} to assign encode.<br>
 * if need to one-To-many can try to {@link com.mhdt.parse.Forest}
 * 
 * @author LazyToShow
 *         <p>
 *         Date：2016/3/4<br>
 *         Time: 16:20<br>
 *         Email: 282854237@qq.com<br>
 *         lastUpdate: 2016/06/20 , 2016/07/29 ,2017/01/07
 */
public class Properties {

	private Map<String, Object> map = new LinkedHashMap<String, Object>();

	public final void load(InputStream is) throws IOException {
		populate(FileIO.getContent(is).split("\r\n"));
	}

	public final void load(InputStream is, String encode) throws IOException {
		populate(FileIO.getContent(is, encode).split("\r\n"));
	}

	public final void load(File file) throws IOException {
		load(file, "utf-8");
	}

	public final void load(File file, String encode) throws IOException {

		String[] arrays = FileIO.getContent(file, encode).split("\r\n");
		populate(arrays);
	}

	public final void load(String path) throws IOException {
		populate(FileIO.getContent(path).split("\r\n"));
	}

	public final void load(String path, String encode) throws IOException {
		populate(FileIO.getContent(path, encode).split("\r\n"));
	}

	/**
	 * put String to properties , that the string must In line with the format.
	 * 
	 * @param arrays
	 */
	private void populate(String[] arrays) {
		map.clear();
		for (String content : arrays) {
			content = StringUtility.removeBlankChar(content);
			if ( content.startsWith("#"))
				continue;
			if (Validate.isNullOrEmpty(content) || content.indexOf("=") == -1)
				continue;

			String[] array = new String[2];
			int flag = content.indexOf("=");
			array[0] = content.substring(0, flag).toLowerCase();
			array[1] = content.substring(flag + 1);

			if (!Validate.isNullOrEmpty(array[0]))
				map.put(StringUtility.removeBlankChar(array[0]), StringUtility.removeBlankChar(array[1]));
		}

	}

	/**
	 * find properties is contains key , item param was not be null or empty.
	 * 
	 * @param key
	 */
	public boolean containsKey(String key) {
		if (Validate.isNullOrEmpty(key))
			return false;
		return map.containsKey(key.toLowerCase());
	}

	/**
	 * find properties is contains value , item param was not be null or empty.
	 * 
	 * @param value
	 */
	public boolean containsValue(String value) {
		if (Validate.isNullOrEmpty(value))
			return false;
		return map.containsValue(value);
	}

	public void clear() {
		map.clear();
	}

	/**
	 * get Obeject to String by key . return is not think type also use another
	 * method attach.
	 * 
	 * @param key
	 * @return
	 */
	public final String get(String key) {
		return (String) map.get(key.toLowerCase());
	}

	/**
	 * Get Integer by key .
	 * 
	 * @param key
	 * @return
	 * @throws NumberFormatException
	 */
	public final Integer getInteger(String key) throws NumberFormatException {
		return Validate.isNullOrEmpty(map.get(key.toLowerCase())) ? null
				: Integer.parseInt(map.get(key.toLowerCase()).toString());
	}

	/**
	 * get Double by key.get Double by key.
	 * 
	 * @param key
	 * @return
	 */
	public final Double getDouble(String key) {
		return Validate.isNullOrEmpty(map.get(key.toLowerCase())) ? null
				: Double.parseDouble(map.get(key.toLowerCase()).toString());
	}

	public Long getLong(String key) {
		return Validate.isNullOrEmpty(map.get(key.toLowerCase())) ? null
				: Long.parseLong(map.get(key.toLowerCase()).toString());
	}

	/**
	 * get Boolean by key.
	 * 
	 * @param key
	 * @return boolean or null.
	 */
	public final Boolean getBoolean(String key) {
		return Validate.isNullOrEmpty(map.get(key.toLowerCase())) ? null
				: Boolean.parseBoolean(map.get(key.toLowerCase()).toString());
	}

	/**
	 * Return all had parsed keys .
	 * 
	 * @return
	 */
	public final Set<String> getKeys() {
		Iterator<String> it = new HashSet<String>(map.keySet()).iterator();
		while (it.hasNext()) {
			if (it.next().startsWith("aNnOtAtIoN_")) {
				it.remove();
			}
		}
		return map.keySet();
	}

	/**
	 * Remove and return the value by key,But key should be not null then will throw
	 * NullpointException.
	 * 
	 * @param key
	 * @return
	 */
	public synchronized final Object remove(String key) {
		if (key == null)
			throw new NullPointerException("The key should be not null.");
		return map.remove(key.toLowerCase());
	}

	/**
	 * Put key-value into.
	 * 
	 * @param key
	 * @param value
	 */
	public synchronized final void put(String key, Object value) {
		if (key == null)
			throw new NullPointerException("The key should be not null.");
		map.put(key.toLowerCase(), value);
	}

	/**
	 * The element node is entry - {@link Map.Entry}.
	 * 
	 * @param entry
	 */
	public synchronized final void put(Map.Entry<String, Object> entry) {
		if (entry == null)
			throw new NullPointerException("Entry is null.");
		map.put(entry.getKey().toLowerCase(), entry.getValue());
	}

	public synchronized final void putAnnotation(Object content) {
		if (Validate.isNullOrEmpty(content))
			throw new NullPointerException("The content must be not null . ");
		map.put("aNnOtAtIoN_" + System.nanoTime(), content);
	}

	/**
	 * Save this config to assign file,There will cover original data.
	 * 
	 * @param file
	 */
	public final void save(File file) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getKey().startsWith("aNnOtAtIoN_")) {
				stringBuilder.append("# " + entry.getValue() + "\r\n");
			} else {
				stringBuilder.append(entry.getKey() + "=" + entry.getValue() + "\r\n");
			}
		}
		FileIO.write(file, stringBuilder.toString().substring(0, stringBuilder.length() - "\r\n".length()), false);
	}

	public final Map<String, Object> map() {
		return map;
	}

	public final Collection<Object> values() {
		return map.values();
	}

	/**
	 * Sort by keys. Default support base type, If need custom sort to get the map
	 */
	public final List<String> sortByKey() {
		List<String> list1 = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map().entrySet()) {
			list1.add(entry.getKey() + "=" + entry.getValue());
		}
		Collections.sort(list1);

		return list1;
	}

	/**
	 * Sort by values. default support base type, if need custom sort to get the map
	 */
	public final List<String> sortByValue() {
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map().entrySet()) {
			list1.add(entry.getValue() + "=" + entry.getKey());
		}
		Collections.sort(list1);
		for (String temp : list1) {
			list2.add(temp.split("=")[1] + "=" + temp.split("=")[0]);
		}
		return list2;
	}

	/**
	 * Convert to {@link java.util.Properties}
	 * 
	 * @return
	 */
	public java.util.Properties toJavaPropertie() {

		java.util.Properties properties = new java.util.Properties();

		for (Map.Entry<String, Object> entry : map.entrySet())
			properties.put(entry.getKey(), entry.getValue());

		return properties;
	}

	/**
	 * <p>
	 * Filters the {@link Properties} for the specified prefix to generate a new
	 * properties object.
	 * 
	 * <p>
	 * Columns such as
	 * <p>
	 * Obj. Name = zhangsen
	 * <p>
	 * Obj. Age = 18
	 * <p>
	 * After screening, we get
	 * <p>
	 * Name = zhangsan
	 * <p>
	 * Age = 18
	 * 
	 * @param prefix
	 * 
	 * @return
	 */
	public Properties screening(String prefix) {

		Assert.notNull(prefix, "prefix is null");
		Assert.state(prefix.indexOf(".") == -1,
				"The prefix: '" + prefix + "' format is incorrect. Because contain '.' ");

		Properties properties = new Properties();

		prefix += ".";

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getKey().startsWith(prefix))
				properties.put(entry.getKey().substring(entry.getKey().indexOf(prefix) + prefix.length()),
						entry.getValue());
		}

		return properties;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("+--------+---------------+---------------------+-----+\r\n");
		sb.append("|----------------     properties     ----------------+\r\n");
		for (Map.Entry<String, Object> en : map.entrySet()) {
			sb.append("|      [ '" + en.getKey() + "'=" + en.getValue() + "] \r\n");
		}
		sb.append("|--------------    /properties     ------------------ \r\n");
		sb.append("+--------+---------------+---------------------+-----+ \r\n");
		return sb.toString();
	}

}
